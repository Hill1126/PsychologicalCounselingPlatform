package org.gdou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.dao.UserMapper;
import org.gdou.dao.WorkOrderMapper;
import org.gdou.model.po.User;
import org.gdou.model.po.example.UserExample;
import org.gdou.model.po.example.WorkOrderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/19
 **/
@RestController
@RequestMapping("/counsel")
@Slf4j
public class CounselController {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkOrderMapper workOrderMapper;

    @RequestMapping("/teacherList")
    public Result getTeacherList(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "5") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        //查找标识为老师的用户
        var userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserTypeEqualTo(UserType.TEACHER);
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo pageInfo = PageInfo.of(userList);
        //根据id查找各个老师的空闲时间
        var orderExample = new WorkOrderExample();
        var orderExampleCriteria = orderExample.createCriteria();
        //获取老师的id,设定条件：工单日期在今天或明天的。
        List<Integer> idList = userList.stream().map(User::getId).collect(Collectors.toList());
        //根据id返回对象

        return ResultGenerator.genSuccessResult(pageInfo);

    }





}
