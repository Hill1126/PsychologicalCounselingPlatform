package org.gdou.dao;

import org.gdou.model.po.Question;
import org.gdou.model.vo.QuestionsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    int checkUserAuthority(Integer questionId, Integer userId);

    List<QuestionsVo> listQuestions(Integer paperId);
}