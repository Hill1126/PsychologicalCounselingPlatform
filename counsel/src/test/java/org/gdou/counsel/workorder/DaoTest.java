package org.gdou.counsel.workorder;

import org.gdou.dao.WorkOrderMapper;
import org.gdou.model.bo.AppointmentTimeBo;
import org.gdou.model.qo.AvailableTimeQo;
import org.gdou.model.qo.TimeQo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/22
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class DaoTest {

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Test
    public void fun(){
        LocalDate now = LocalDate.now();
        List<AppointmentTimeBo> appointmentById = workOrderMapper.getAppointmentById(new AvailableTimeQo(15,now,now.plusDays(1) ));
        Assert.assertNotEquals(0,appointmentById.size());
    }

    @Test
    public void getTimeUPOrderIdTest(){
        TimeQo qo = new TimeQo();
        qo.setEndDate(LocalDate.now());
        qo.setTime(LocalTime.now());
        List<Integer> id = workOrderMapper.getTimeUpOrderId(qo);
        Assert.assertNotEquals(0,id.size());
    }

    @Test
    public void updateStatusByIdsTest(){
        var list = new ArrayList<Integer>();
        list.add(10010);
        list.add(20020);
        workOrderMapper.updateStatusByIds(list);
    }

}
