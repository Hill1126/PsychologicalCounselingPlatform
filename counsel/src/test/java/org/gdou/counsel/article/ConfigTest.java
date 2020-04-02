package org.gdou.counsel.article;

import org.gdou.dao.ConfigMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/1
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ConfigTest {

    @Autowired
    ConfigMapper configMapper;

    @Test
    public void getValueByNameTest(){
        List<String> configValue = configMapper.getValueByName("article", "category");
        Assert.assertNotEquals(0,configValue.size());
    }
}
