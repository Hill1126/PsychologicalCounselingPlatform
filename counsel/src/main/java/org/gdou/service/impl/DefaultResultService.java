package org.gdou.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.dao.DefaultResultMapper;
import org.gdou.model.po.DefaultResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@Service
@Slf4j
public class DefaultResultService {

    @Autowired
    DefaultResultMapper defaultResultMapper;

    public Result addDefaultResult(DefaultResult defaultResult) {
        boolean pass = scoreCheck(defaultResult);
        //如果不通过过
        if (!pass){
            return unPassResult(defaultResult);
        }
        defaultResultMapper.insert(defaultResult);
        log.info("成功为试卷id:【{}】添加默认结果，分数范围是:{}-{}"
                ,defaultResult.getPaperId(),defaultResult.getScoreStart(),defaultResult.getScoreEnd());
        return Result.genSuccessResult(defaultResult);
    }

    /**
     * 检查分数范围是否冲突
     * @Author: HILL
     * @date: 2020/4/29 14:55
     *
     * @param defaultResult 要变化的结果
     * @return: boolean 通过为true，不通过为false
    **/
    private boolean scoreCheck(DefaultResult defaultResult) {
        Integer check = defaultResultMapper.scoreCheck(defaultResult);
        //已有结果造成冲突则返回false
        return check <= 0;
    }

    private Result unPassResult(DefaultResult defaultResult) {
        var msg = String.format("分数范围%.2f-%.2f造成了冲突",
                defaultResult.getScoreStart(), defaultResult.getScoreEnd());
        return Result.genFailResult(msg);
    }


    public Result updateResult(DefaultResult defaultResult) {
        boolean pass = scoreCheck(defaultResult);
        //如果不通过过
        if (!pass){
            return unPassResult(defaultResult);
        }
        defaultResultMapper.updateByPrimaryKeySelective(defaultResult);
        log.info("更新试卷id为【{}】的默认结果",defaultResult.getPaperId());
        log.info("更新的内容为【{}】",defaultResult.toString());
        return Result.genSuccessResult();
    }



    public Result listResults(Integer paperId) {
        List<DefaultResult>  resultList = defaultResultMapper.listResultsByPaperId(paperId);
        return Result.genSuccessResult(resultList);
    }

    public Result deleteResult(Integer resultId) {
        defaultResultMapper.deleteByPrimaryKey(resultId);
        log.info("删除默认结果，id为【{}】",resultId);
        return Result.genSuccessResult();
    }
}
