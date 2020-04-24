package org.gdou.model.vo.paper;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/24
 **/
public class PaperDetailVo extends PaperAbstractVo {
    private List<QuestionsVo> questionList;

    public List<QuestionsVo> getQuestionList(){
        return questionList;
    }

    public void setQuestionList(List<QuestionsVo> questionList) {
        this.questionList = questionList;
    }
}
