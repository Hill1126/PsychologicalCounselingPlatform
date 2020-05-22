package org.gdou.model.po;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FunImage {
    private Integer funImageId;

    private String title;

    private String imageAbstract;

    private String imageUrl;

    private LocalDateTime createAt;

    private Integer status;


}