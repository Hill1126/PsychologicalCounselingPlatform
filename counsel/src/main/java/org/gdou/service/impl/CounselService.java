package org.gdou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.dao.UserMapper;
import org.gdou.model.qo.TeacherChatQo;
import org.springframework.stereotype.Service;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/21
 **/
@Service
@Slf4j
public class CounselService {

    private UserMapper userMapper;

    public CounselService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PageInfo getTeacherList(int pageNum, int pageSize, boolean isOrderByDesc){
        PageHelper.startPage(pageNum,pageSize);
        //查找标识为老师且当前预约时间比较多的的用户。
        TeacherChatQo teacherChatQo = TeacherChatQo.quicklyBuild();
        teacherChatQo.setOrderByDesc(isOrderByDesc);
        PageInfo pageInfo = PageInfo.of(userMapper.selectAppointmentTeacher(teacherChatQo));
        return pageInfo;
    }
}
