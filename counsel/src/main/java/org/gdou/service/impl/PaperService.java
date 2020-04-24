package org.gdou.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.dao.DefaultResultMapper;
import org.gdou.dao.PaperMapper;
import org.gdou.dao.TestRecordMapper;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.dto.paper.PaperDto;
import org.gdou.model.po.Paper;
import org.gdou.model.po.TestRecord;
import org.gdou.model.vo.paper.PaperAbstractVo;
import org.gdou.model.vo.paper.PaperDetailVo;
import org.gdou.model.vo.paper.QuestionsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集合测评量表的相关功能
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@Service
@Slf4j
public class PaperService {

    private PaperMapper paperMapper;
    private QuestionService questionService;
    private DefaultResultMapper defaultResultMapper;
    private TestRecordMapper testRecordMapper;

    public PaperService(PaperMapper paperMapper, QuestionService questionService, DefaultResultMapper defaultResultMapper, TestRecordMapper testRecordMapper) {
        this.paperMapper = paperMapper;
        this.questionService = questionService;
        this.defaultResultMapper = defaultResultMapper;
        this.testRecordMapper = testRecordMapper;
    }

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

    /**
     * 根据分页信息返回，试题的简略信息
     * @Author: HILL
     * @date: 2020/4/24 15:30
     *
     * @param pageInfoDto
     * @return: org.gdou.common.result.Result
    **/
    public  Result listPreviews(PageInfoDto pageInfoDto) {
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<PaperAbstractVo> paperAbstractVos = paperMapper.listPreviews();
        return Result.genSuccessResult(paperAbstractVos);
    }

    /**
     * 根据paperId，获取试卷的详细内容
     * @Author: HILL
     * @date: 2020/4/24 15:50
     *
     * @param paperId
     * @return: org.gdou.common.result.Result
    **/
    public Result getPaper(Integer paperId) {
        PaperDetailVo detailVo = new PaperDetailVo();
        Paper paper = paperMapper.selectByPrimaryKey(paperId);
        BeanUtils.copyProperties(paper,detailVo);
        log.debug("获取试卷【id:{}】的信息",paperId);
        //将试卷所属的问题列表放入vo中
        Result result = questionService.listQuestions(paperId);
        var questionsVoList = (List<QuestionsVo>)result.getData();
        log.debug("获取试卷的问题列表的，大小为：{}",questionsVoList.size());
        detailVo.setQuestionList(questionsVoList);
        return Result.genSuccessResult(detailVo);

    }

    /**
     * 根据给出的试卷id和分数返回结果。
     * 并在数据库记录此次数据。
     * @Author: HILL
     * @date: 2020/4/24 16:43
     *
     * @param paperId 试卷id
     * @param totalScore 总分
     * @param userId 当前考试用户的id
     * @return: org.gdou.common.result.Result 返回包含成绩和描述的结果
    **/
    public Result commit(Integer paperId, Double totalScore,Integer userId) {
        //根据总分获取结果
        String description = defaultResultMapper.getResultByScore(paperId, totalScore);
        log.info("用户：id【{}】提交了一次考试，成绩为：【{}】，试卷id为【{}】"
                ,userId,totalScore,paperId);
        //记录这一次的考试成绩
        var testRecord = new TestRecord();
        testRecord.setCreateAt(LocalDateTime.now());
        testRecord.setPaperId(paperId);
        testRecord.setTotalScore(totalScore);
        testRecord.setUserId(userId);
        testRecord.setDescription(description);
        testRecordMapper.insert(testRecord);
        log.debug("插入一条考试成绩记录，详情：{}",testRecord.toString());
        Map<String,Object> map = new HashMap<>(2);
        map.put("totalScore",totalScore);
        map.put("description",description);
        return Result.genSuccessResult(map);

    }
}
