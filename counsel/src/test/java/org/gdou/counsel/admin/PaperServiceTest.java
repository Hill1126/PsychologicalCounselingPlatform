package org.gdou.counsel.admin;

import org.gdou.common.constant.CommonDataStatus;
import org.gdou.common.result.Result;
import org.gdou.dao.PaperMapper;
import org.gdou.model.dto.paper.PaperDto;
import org.gdou.model.po.Paper;
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
        var paperDto = new PaperDto();
        paperDto.setPaperAbstract(LocalDateTime.now().toString());
        paperService.updatePaper(1111,paperDto);
    }

    @Test
    public void listPapersTest(){
        Result result = paperService.listPapers(1111);
    }

}
