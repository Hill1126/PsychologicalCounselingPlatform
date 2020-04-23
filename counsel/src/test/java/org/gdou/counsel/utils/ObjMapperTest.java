package org.gdou.counsel.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gdou.common.aop.RequestInfo;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.po.User;
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
public class ObjMapperTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void fun() throws JsonProcessingException {
        RequestInfo info = new RequestInfo();
        info.setRequestParams(new User());
        var dto = new PageInfoDto();
        dto.setPageNum(1);
        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);
    }
}
