package org.gdou.model.bo;

import lombok.Getter;
import lombok.Setter;
import org.gdou.model.vo.article.ArticlePreviewVo;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/9
 **/
@Getter
@Setter
public class SearchArticleBo {
    private String scrollId;
    private List<ArticlePreviewVo> voList;

    public SearchArticleBo(String scrollId, List<ArticlePreviewVo> voList) {
        this.scrollId = scrollId;
        this.voList = voList;
    }
}
