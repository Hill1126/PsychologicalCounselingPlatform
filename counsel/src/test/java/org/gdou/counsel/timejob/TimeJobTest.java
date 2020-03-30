package org.gdou.counsel.timejob;

import org.gdou.timejob.counsel.CounselTimeJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/30
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TimeJobTest {

    @Autowired
    private CounselTimeJob counselTimeJob;

    @Test
    public void updateOrderStatusTest(){
        counselTimeJob.updateOrderStatus();
    }
}
