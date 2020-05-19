package org.gdou.service.impl;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.user.OauthsType;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.dao.OauthsMapper;
import org.gdou.dao.UserMapper;
import org.gdou.model.po.Oauths;
import org.gdou.model.po.example.OauthsExample;
import org.gdou.model.po.User;
import org.gdou.model.po.example.UserExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/2
 **/
@Service
@Slf4j
public class UserService {

    private OauthsMapper oauthsMapper;
    private UserMapper userMapper ;

    public UserService(OauthsMapper oauthsMapper, UserMapper userMapper) {
        this.oauthsMapper = oauthsMapper;
        this.userMapper = userMapper;
    }

    /**
     * 通过传入的user，分别在user表和oauths表插入身份信息和验证信息
     * 默认注册为学生
     * @Author: HILL
     * @date: 2020/3/2 17:33
     *
     * @param user
     * @return: void
    **/
    public Result register(User user){

        //校验当前账号是否存在
        int id = oauthsMapper.ifExitsOauthId(user.getAccount());
        if(id>0) {
            return ResultGenerator.genFailResult("账号已存在！");
        }

        //插入user，返回生成的id
        userMapper.insertSelective(user);
        log.info("数据表{}插入数据 生成的id为{} ","user",user.getId());
        //根据user的信息生成验证信息
        var userOauth = Oauths.builder().userId(user.getId()).
                oauthId(user.getAccount()).
                credential(user.getPassword()).
                oauthType(OauthsType.PASS_WORD).build();

        oauthsMapper.insert(userOauth);
        log.info("数据表{}插入数据 生成的id为{} ","oauths",userOauth.getId());
        return ResultGenerator.genSuccessResult("注册成功！");
    }


    public Result login(Oauths oauth) {
        var example = new OauthsExample();
        var criteria = example.createCriteria();
        criteria.andOauthIdEqualTo(oauth.getOauthId()).andCredentialEqualTo(oauth.getCredential());
        List<Oauths> oauthList = oauthsMapper.selectByExample(example);
        //根据查询的凭证获取用户id
        if (oauthList!=null && oauthList.size()>0){
            var selectOauth = oauthList.get(0);
            var user = userMapper.selectByPrimaryKey(selectOauth.getUserId());
            //删除密码信息，返回user
            user.setPassword("");
            log.info("用户 【{}】 通过 【{}】 方式验证登录",user.getName(),selectOauth.getOauthType());
            return ResultGenerator.genSuccessResult(user);
        }
        return ResultGenerator.genFailResult("用户不存在或密码错误");

    }

    /**
     * 更新user的信息并返回新的user对象。
     * @Author: HILL
     * @date: 2020/3/7 23:27
     *
     * @param user 不包含密码的user
     * @return: org.gdou.common.result.Result
    **/
    public User updateUserInfo(User user){
        userMapper.updateByPrimaryKeySelective(user);
        user = userMapper.selectByPrimaryKey(user.getId());
        user.setPassword(null);
        return user;
    }

    /**
     * 根据id获取用户头像
     * @Author: HILL
     * @date: 2020/5/18 9:34
     * 
     * @param userId 用户id
     * @return: org.gdou.common.result.Result
    **/
    public Result getAvatar(Integer userId) {
        return Result.genSuccessResult(userMapper.getAvatar(userId));
    }

    /**
     * 根据用户类型返回用户信息，若无用户类型则默认返回所有类型
     * @Author: HILL
     * @date: 2020/5/19 13:27
     *
     * @param userType
     * @return: org.gdou.common.result.Result
    **/
    public Result listUsers(Integer userType) {
        UserExample example = new UserExample();
        if (userType!=null){
            example.createCriteria().andUserTypeEqualTo(userType);
        }
        PageInfo<User> info = PageInfo.of(userMapper.selectByExample(example));
        return Result.genSuccessResult(info);
    }

    /**
     * 将用户的登录密码重置为123456
     * @Author: HILL
     * @date: 2020/5/19 14:07
     *
     * @param userId
     * @return: org.gdou.common.result.Result
    **/
    @Transactional(rollbackFor = RuntimeException.class)
    public Result reSetPassWord(Integer userId) {
        return updatePassword(userId,"123456");
    }

    private Result updatePassword(Integer userId,String password) {
        //更新用户密码
        User user = new User();
        user.setPassword(password);
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateByPrimaryKeySelective(user);
        //更新用户凭证
        oauthsMapper.reSetPassWord(userId,password);
        log.info("用户id【{}】的密码被修改为【{}】",userId,password);
        return Result.genSuccessResult();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteUser(Integer userId) {
        //删除用户
        userMapper.deleteByPrimaryKey(userId);
        //删除用户凭证
        var example = new OauthsExample();
        example.createCriteria().andUserIdEqualTo(userId);
        oauthsMapper.deleteByExample(example);
        log.info("用户id【{}】被删除",userId);
        return Result.genSuccessResult();

    }

    /**
     * 根据用户旧密码验证，修改为新密码
     * @Author: HILL
     * @date: 2020/5/19 15:06
     *
     * @param user 用户信息
     * @param oldPass 旧密码
     * @param newPass 新密码
     * @return: org.gdou.common.result.Result
    **/
    @Transactional(rollbackFor = RuntimeException.class)
    public Result updatePassWord(User user, String oldPass, String newPass) {
        var example = new OauthsExample();
        example.createCriteria().andUserIdEqualTo(user.getId())
                .andOauthTypeEqualTo(OauthsType.PASS_WORD)
                .andCredentialEqualTo(oldPass);
        List<Oauths> list = oauthsMapper.selectByExample(example);
        //密码验证失败，返回提示
        if (list==null||list.size()==0){
            return Result.genFailResult("密码验证错误，请重试。");
        }
        updatePassword(user.getId(),newPass);
        return Result.genSuccessResult();
    }
}
