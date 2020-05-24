package org.gdou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.dao.AnswerMapper;
import org.gdou.dao.DefaultResultMapper;
import org.gdou.dao.PaperMapper;
import org.gdou.dao.TestRecordMapper;
import org.gdou.model.dto.PageInfoDto;
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
    private AnswerMapper answerMapper;

    public PaperService(PaperMapper paperMapper, QuestionService questionService, DefaultResultMapper defaultResultMapper, TestRecordMapper testRecordMapper, AnswerMapper answerMapper) {
        this.paperMapper = paperMapper;
        this.questionService = questionService;
        this.defaultResultMapper = defaultResultMapper;
        this.testRecordMapper = testRecordMapper;
        this.answerMapper = answerMapper;
    }

    public Result creatPaper(Paper paper) {
        paperMapper.insert(paper);
        log.info("用户id为【{}】新建试卷，标题为【{}】",paper.getCreatUserId(),paper.getPaperTitle());
        return Result.genSuccessResult(paper);
    }

    public Result updatePaper( Paper paper) {
        paperMapper.updateByPrimaryKeySelective(paper);
        log.info("试卷状态更新，信息为：{}",paper.toString());
        return Result.genSuccessResult();

    }

    /**
     * 根据传入的用户id，返回该用户创建的试卷集合
     * @Author: HILL
     * @date: 2020/4/22 22:57
     *
     * @param userId 用户id
     * @return: org.gdou.common.result.Result
    **/
    public Result listPapers(PageInfoDto pageInfoDto,Integer userId) {
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<Paper> paperList =  paperMapper.listPapers(userId);
        return Result.genSuccessResult(PageInfo.of(paperList));
    }

    /**
     * 根据分页信息返回，试题的简略信息
     * @Author: HILL
     * @date: 2020/4/24 15:30
     *
     * @param pageInfoDto
     * @return: org.gdou.common.result.Result 带有分页信息的list
    **/
    public  Result listPreviews(PageInfoDto pageInfoDto) {
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<PaperAbstractVo> paperAbstractVos = paperMapper.listPreviews();
        return Result.genSuccessResult(PageInfo.of(paperAbstractVos));
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
        if (paper==null){
            return Result.genNotFound("试卷信息未找到");
        }
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
     * @param answerIds 考试用户选择的答案id
     * @param userId 当前考试用户的id
     * @return: org.gdou.common.result.Result 返回包含成绩和描述的结果
    **/
    public Result commit(Integer paperId,List<Integer> answerIds, Integer userId) {

        //根据提交答案id获得分数
        Double totalScore ;
        if (answerIds.size()==0){
            totalScore = 0.0;
        }else {
            totalScore = answerMapper.getTotalScoreByIds(answerIds);
        }
        //根据总分获取结果
        String description = defaultResultMapper.getResultByScore(paperId, totalScore);
        log.info("用户：id【{}】提交了一次考试，成绩为：【{}】，试卷id为【{}】"
                ,userId,totalScore,paperId);
        if (description==null){
            description = "此分数范围没有结果可以查看，请联系管理员";
        }
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

    public Result listAll(PageInfoDto pageInfoDto) {
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<Paper> paperList =  paperMapper.listAll();
        return Result.genSuccessResult(PageInfo.of(paperList));
    }
}
