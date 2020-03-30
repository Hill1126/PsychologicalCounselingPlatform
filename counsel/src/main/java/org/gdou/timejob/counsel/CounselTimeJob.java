package org.gdou.timejob.counsel;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.utils.RedisUtil;
import org.gdou.dao.WorkOrderMapper;
import org.gdou.model.qo.TimeQo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 有关咨询模块的相关定时任务
 * @author HILL
 * @version V1.0
 * @date 2020/3/27
 **/
@Component
@Slf4j
public class CounselTimeJob {


    private WorkOrderMapper workOrderMapper;
    private RedisUtil redisUtil;

    public CounselTimeJob(WorkOrderMapper workOrderMapper, RedisUtil redisUtil) {
        this.workOrderMapper = workOrderMapper;
        this.redisUtil = redisUtil;
    }

    /**
     * 每隔一小时进行工单的更新。
     * @Author: HILL
     * @date: 2020/3/27 22:22
     *
     * @return: void
    **/
    @Scheduled(cron = "0 2 */1 * * ?")
    public void updateOrderStatus(){
        log.info("定时任务：updateOrderStatus已启动");
        //获取需要更新状态的id
        TimeQo qo = new TimeQo();
        qo.setEndDate(LocalDate.now());
        qo.setTime(LocalTime.now());
        List<Integer> ids = workOrderMapper.getTimeUpOrderId(qo);
        List<Integer> changeList = new ArrayList<>(4);
        //拿id到redis中查询是否存在
        ids.forEach((orderId)->{
            if (!redisUtil.hHasKey(ProjectConstant.ORDER_KEY,orderId+"")){
                //不存在则记录，然后后续调用mapper修改状态
                changeList.add(orderId);
                log.info("订单id【{}】准备更新状态为已完成",orderId);
            }
        });
        if (!changeList.isEmpty()){
            workOrderMapper.updateStatusByIds(changeList);
            log.info("订单状态更新完成");
        }

    }

}
