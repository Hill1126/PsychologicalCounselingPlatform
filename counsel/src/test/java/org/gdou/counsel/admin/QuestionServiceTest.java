package org.gdou.counsel.admin;

import org.gdou.common.result.Result;
import org.gdou.service.impl.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/23
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void fun(){
        Result result = questionService.listQuestions(300101);

    }


}
