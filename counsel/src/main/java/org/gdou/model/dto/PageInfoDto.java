package org.gdou.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/3/25
 **/
@Getter
@Setter
public class PageInfoDto {

    @NotNull(message = "分页信息不能为空")
    private Integer pageSize = 5;
    @NotNull(message = "分页信息不能为空")
    private Integer pageNum = 1;

    @Override
    public String toString() {
        return "PageInfoDto{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                '}';
    }
}
