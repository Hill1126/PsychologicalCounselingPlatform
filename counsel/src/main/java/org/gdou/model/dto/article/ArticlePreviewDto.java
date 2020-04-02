package org.gdou.model.dto.article;

import lombok.Getter;
import lombok.Setter;
import org.gdou.model.dto.PageInfoDto;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/2
 **/
@Getter
@Setter
public class ArticlePreviewDto {

    private PageInfoDto pageInfoDto ;
    private String category;
}
