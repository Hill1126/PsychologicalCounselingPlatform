package org.gdou.counsel.workorder;

import org.gdou.common.result.Result;
import org.gdou.common.result.ResultCode;
import org.gdou.model.bo.MakeAppointmentBO;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.qo.CounselHistoryQo;
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
import java.util.List;

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
        bo.setStudentName("testStudent");
        bo.setTeacherId(15);
        bo.setTeacherName("testTeacher");
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

    @Test
    public void historyCounselTest(){

        CounselHistoryQo qo = new CounselHistoryQo();
        qo.setTeacherId(15);
        Result history = counselService.getMyCounselHistory(qo);
        System.out.println(history.getData().toString());
        Assert.assertNotNull(history.getData());
        qo.setTeacherId(null);
        qo.setStudentId(14);
        Result myCounselHistory = counselService.getMyCounselHistory(qo);
        System.out.println(myCounselHistory.getData().toString());
    }

    @Test
    public void getMsgRecordTest(){
        PageInfoDto dto = new PageInfoDto();
        dto.setPageSize(10);
        dto.setPageNum(1);
        Result msgRecord = counselService.getMsgRecord(10010,dto);
        var data = (List)msgRecord.getData();
        Assert.assertNotNull(data);
    }

    @Test
    public void getTodoCounselTest(){
        Result counsel = counselService.getTodoCounsel(14);
        System.out.println(counsel.getData());
    }

}
