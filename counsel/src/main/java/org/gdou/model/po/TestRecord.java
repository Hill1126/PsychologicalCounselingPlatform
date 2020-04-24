package org.gdou.model.po;

import java.time.LocalDateTime;

public class TestRecord {
    private Integer id;

    private Integer paperId;

    private Integer userId;

    private LocalDateTime createAt;

    private Double totalScore;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        return "TestRecord{" +
                "id=" + id +
                ", paperId=" + paperId +
                ", userId=" + userId +
                ", createAt=" + createAt +
                ", totalScore=" + totalScore +
                ", description='" + description + '\'' +
                '}';
    }
}