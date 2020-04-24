package org.gdou.dao;

import org.gdou.model.po.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);

    List<Answer> listAnswers(Iterable<Integer> ids);
}