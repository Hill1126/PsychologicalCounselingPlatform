package org.gdou.model.po;

/**
 * @author HILL
 */
public class Answer {
    private Integer id;

    private Integer questionId;

    private String answerValue;

    private Double answerScore;

    private Integer answerStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue == null ? null : answerValue.trim();
    }

    public Double getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(Double answerScore) {
        this.answerScore = answerScore;
    }

    public Integer getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Integer answerStatus) {
        this.answerStatus = answerStatus;
    }
}