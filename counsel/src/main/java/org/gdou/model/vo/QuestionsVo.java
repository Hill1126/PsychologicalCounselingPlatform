package org.gdou.model.vo;

import lombok.Getter;
import lombok.Setter;
import org.gdou.model.po.Answer;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/23
 **/
@Getter
@Setter
public class QuestionsVo {

   private Integer id;

    private String questionTitle;

    private Integer paperId;

    private List<Answer> answerList;

}
