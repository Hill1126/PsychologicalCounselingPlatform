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
        defaultResultMapper.insert(defaultResult);
        log.info("成功为试卷id:【{}】添加默认结果，分数范围是:{}-{}"
                ,defaultResult.getPaperId(),defaultResult.getScoreStart(),defaultResult.getScoreEnd());
        return Result.genSuccessResult(defaultResult);
    }

    public Result updateResult(DefaultResult defaultResult) {
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
