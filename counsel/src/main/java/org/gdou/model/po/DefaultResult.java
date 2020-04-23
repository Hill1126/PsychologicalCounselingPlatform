package org.gdou.model.po;

import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author HILL
 */
@ToString
public class DefaultResult {
    private Integer id;

    @NotNull(message = "试卷id不能为空")
    private Integer paperId;
    @NotNull(message = "分数起始范围不能为空")
    private Double scoreStart;
    @NotNull(message = "分数起始范围不能为空")
    private Double scoreEnd;
    @NotBlank(message = "结果描述不能为空")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Double getScoreStart() {
        return scoreStart;
    }

    public void setScoreStart(Double scoreStart) {
        this.scoreStart = scoreStart;
    }

    public Double getScoreEnd() {
        return scoreEnd;
    }

    public void setScoreEnd(Double scoreEnd) {
        this.scoreEnd = scoreEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}