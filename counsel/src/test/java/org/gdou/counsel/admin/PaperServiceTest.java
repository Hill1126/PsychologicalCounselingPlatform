package org.gdou.counsel.admin;

import org.gdou.common.constant.CommonDataStatus;
import org.gdou.common.result.Result;
import org.gdou.dao.PaperMapper;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.po.DefaultResult;
import org.gdou.model.po.Paper;
import org.gdou.service.impl.DefaultResultService;
import org.gdou.service.impl.PaperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PaperServiceTest {

    @Autowired
    PaperService paperService;
    @Autowired
    PaperMapper paperMapper;
    @Autowired
    DefaultResultService defaultResultService;

    @Test
    public void createTest(){
        Paper paper = new Paper();
        paper.setPaperStatus(CommonDataStatus.DELETE);
        paper.setCreatAt(LocalDateTime.now());
        paper.setPaperAbstract("这是测试内容");
        paper.setPaperTitle("测试试题");
        paper.setCreatUserId(1111);
        paperService.creatPaper(paper);
    }

    @Test
    public void updateTest(){
        var paper = new Paper();
        paper.setPaperAbstract(LocalDateTime.now().toString());
        paper.setId(1111);
        paperService.updatePaper(paper);
    }

    @Test
    public void listPapersTest(){
        Result result = paperService.listPapers(new PageInfoDto(),1111);
    }

    @Test
    public void fun(){
        Result result = paperService.getPaper(300101);
        System.out.println(result.getData().toString());
    }

    @Test
    public void resultScopeTest(){
        var result = new DefaultResult();
        result.setPaperId(300101);
        result.setScoreStart(2.5);
        result.setScoreEnd(4.0);
        result.setDescription("test");
        Result r = defaultResultService.addDefaultResult(result);

    }

}
