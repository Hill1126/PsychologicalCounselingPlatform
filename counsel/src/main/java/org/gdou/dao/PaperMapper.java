package org.gdou.dao;

import org.gdou.model.po.Paper;
import org.gdou.model.vo.paper.PaperAbstractVo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaperMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Paper record);

    int insertSelective(Paper record);

    Paper selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKey(Paper record);

    /**
     * 获取用户设置的试卷
     * @Author: HILL
     * @date: 2020/5/24 12:47
     *
     * @param userId
     * @return: java.util.List<org.gdou.model.po.Paper>
    **/
    List<Paper> listPapers(Integer userId);

    List<PaperAbstractVo> listPreviews();

    List<Paper> listAll();
}