package org.gdou.dao;

import org.apache.ibatis.annotations.Param;
import org.gdou.model.po.User;
import org.gdou.model.po.example.UserExample;
import org.gdou.model.qo.TeacherChatQo;
import org.gdou.model.vo.TeacherVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * TODO
     * @Author: HILL
     * @date: 2020/3/20 18:08
     * 
     * @param teacherChatQo
     * @return: java.util.List<org.gdou.model.vo.TeacherVo>
    **/
    List<TeacherVo> selectAppointmentTeacher(TeacherChatQo teacherChatQo);

    String getAvatar(Integer userId);
}