package org.gdou.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.user.OauthsType;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.dao.OauthsMapper;
import org.gdou.dao.UserMapper;
import org.gdou.model.po.Oauths;
import org.gdou.model.po.OauthsExample;
import org.gdou.model.po.User;
import org.springframework.stereotype.Service;

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
        return ResultGenerator.genSuccessResult();
    }


    public Result login(Oauths oauth) {
        var example = new OauthsExample();
        var criteria = example.createCriteria();
        criteria.andOauthIdEqualTo(oauth.getOauthId()).andCredentialEqualTo(oauth.getCredential());
        List<Oauths> oauthList = oauthsMapper.selectByExample(example);
        //根据查询的凭证获取用户id
        if (oauthList!=null && oauthList.size()>0){
            var userId = oauthList.get(0).getUserId();
            var user = userMapper.selectByPrimaryKey(userId);
            //删除密码信息，返回user
            user.setPassword("");
            log.info("用户{}通过{}方式验证登录",user.getName(),oauth.getOauthType());
            return ResultGenerator.genSuccessResult(user);
        }
        return ResultGenerator.genFailResult("用户不存在或密码错误");

    }
}
