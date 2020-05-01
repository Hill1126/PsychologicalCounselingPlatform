package org.gdou.model.vo.paper;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/24
 **/
@Getter
@Setter
public class PaperAbstractVo {

    private Integer id;

    private String paperTitle;

    private String paperAbstract;

    private LocalDateTime creatAt;

    private String questionSetter;

    private String coverUrl;

}
