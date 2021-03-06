package org.gdou.counsel.user;

import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.dao.UserMapper;
import org.gdou.model.qo.TeacherChatQo;
import org.gdou.model.vo.TeacherVo;
import org.gdou.service.impl.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/11
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserTest {

    @Value("${user.interceptor}")
    private boolean startInterceptor;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    private TeacherChatQo teacherChatQo = TeacherChatQo.quicklyBuild();


    @Test
    public void fun1(){
        List<TeacherVo> teacherVos = userMapper.selectAppointmentTeacher(teacherChatQo);
        teacherVos.forEach((teacherVo)->System.out.println(teacherVo.getName()));

    }
    @Test
    public void after(){
        teacherChatQo.setHot(true);
        List<TeacherVo> teacherVos = userMapper.selectAppointmentTeacher(teacherChatQo);
        teacherVos.forEach((teacherVo)->System.out.println(teacherVo.getName()));
    }

    @Test
    public void listUsersTest(){
        Integer userType = UserType.TEACHER;
        Result result = userService.listUsers(null);


    }
}
