package org.gdou.model.dto.article;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/7
 **/
@Getter
@Setter
public class ArticleDto {

    private String title;

    private String articleSource;

    private String articleUrl;

    private String author;

    private String articleCategory;

    private String articleLabel;

    private String headImgUrl;

    private String articleContent;

    private String contentHtml;

}
