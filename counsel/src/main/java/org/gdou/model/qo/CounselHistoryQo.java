package org.gdou.model.qo;

import lombok.Data;
import org.gdou.common.constant.chat.WorkOrderStatus;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/25
 **/
@Data
public class CounselHistoryQo {
    private Integer teacherId;
    private Integer studentId;
    private Integer status = WorkOrderStatus.READY;
}
