package org.gdou.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/25
 **/
@Data
public class PageInfoDto {

    @NotNull(message = "分页信息不能为空")
    private Integer pageSize;
    @NotNull(message = "分页信息不能为空")
    private Integer pageNum;

}
