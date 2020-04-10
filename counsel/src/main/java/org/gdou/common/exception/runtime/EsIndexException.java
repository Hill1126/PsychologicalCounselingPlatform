package org.gdou.common.exception.runtime;

import lombok.Getter;
import org.gdou.model.po.Article;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/8
 **/
public class EsIndexException extends RuntimeException {

    @Getter
   public Article article;

    public EsIndexException(String message,Article article) {
        super(message);
        this.article = article;
    }

}
