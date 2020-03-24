package org.gdou.counsel.workorder;

import org.gdou.common.result.Result;
import org.gdou.common.result.ResultCode;
import org.gdou.model.bo.MakeAppointmentBO;
import org.gdou.service.impl.CounselService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/23
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Autowired
    CounselService counselService;
    MakeAppointmentBO bo;

    @Before
    public void before(){
        bo = new MakeAppointmentBO();
        bo.setAppointmentDate(LocalDate.of(2020,3,23));
        bo.setAppointmentTime(LocalTime.of(9,0));
        bo.setStudentId(14);
        bo.setTeacherId(15);
    }

    @Test
    public void getWorkOrderByIdTest(){
        Result result = counselService.getWorkOrderByID(22);
        Assert.assertNotNull(result.getData());

    }

    @Test
    public void makeAppointmentCheckTest(){

        Result result = counselService.makeAppointment(bo);
        Assert.assertEquals(ResultCode.FAIL,result.getCode());
    }

    @Test
    public void insertAppointmentTest(){
        bo.setAppointmentTime(LocalTime.now());
        bo.setAppointmentDate(LocalDate.now().plusDays(1));
        Result result = counselService.makeAppointment(bo);
        Assert.assertEquals(ResultCode.SUCCESS,result.getCode());
    }


}
