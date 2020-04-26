package org.gdou.model.vo.article;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/2
 **/
@Data
public class ArticlePreviewVo {

    private Integer id;
    private String title;
    private LocalDateTime updateAt;
    private String articleSource;
    private String author;
    private String headImgUrl;
}
