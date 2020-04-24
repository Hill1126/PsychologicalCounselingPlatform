package org.gdou.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.dao.PaperMapper;
import org.gdou.model.dto.paper.PaperDto;
import org.gdou.model.po.Paper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 集合测评量表的相关功能
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@Service
@Slf4j
public class PaperService {

    @Autowired
    private PaperMapper paperMapper;

    public Result creatPaper(Paper paper) {
        paperMapper.insert(paper);
        log.info("用户id为【{}】新建试卷，标题为【{}】",paper.getCreatUserId(),paper.getPaperTitle());
        return Result.genSuccessResult();
    }

    public Result updatePaper( PaperDto paperDto) {
        var paper = new Paper();
        BeanUtils.copyProperties(paperDto,paper);
        paperMapper.updateByPrimaryKeySelective(paper);
        log.info("试卷状态更新，信息为：{}",paper.toString());
        return Result.genSuccessResult();

    }

    /**
     * 根据传入的用户id，返回改用户创建的试卷
     * @Author: HILL
     * @date: 2020/4/22 22:57
     *
     * @param userId 用户id
     * @return: org.gdou.common.result.Result
    **/
    public Result listPapers(Integer userId) {
        List<Paper> paperList =  paperMapper.listPapers(userId);
        return Result.genSuccessResult(paperList);
    }
}
