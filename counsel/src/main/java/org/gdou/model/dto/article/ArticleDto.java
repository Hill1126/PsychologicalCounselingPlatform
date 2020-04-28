package org.gdou.model.dto.article;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/7
 **/
@Getter
@Setter
public class ArticleDto {

    @NotBlank
    private String title;
    @NotBlank
    private String articleSource;
    @NotBlank
    private String articleUrl;
    @NotBlank
    private String author;
    @NotBlank
    private String articleCategory;
    @NotBlank
    private String articleLabel;

    private String headImgUrl;
    @NotBlank
    private String articleContent;
    @NotBlank
    private String contentHtml;




}
