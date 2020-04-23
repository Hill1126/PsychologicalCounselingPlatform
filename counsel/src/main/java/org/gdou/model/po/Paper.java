package org.gdou.model.po;

import java.time.LocalDateTime;

public class Paper {
    private Integer id;

    private String paperTitle;

    private LocalDateTime creatAt;

    private Integer creatUserId;

    private Integer paperStatus;

    private String paperType;

    private String paperAbstract;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle == null ? null : paperTitle.trim();
    }

    public LocalDateTime getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(LocalDateTime creatAt) {
        this.creatAt = creatAt;
    }

    public Integer getCreatUserId() {
        return creatUserId;
    }

    public void setCreatUserId(Integer creatUserId) {
        this.creatUserId = creatUserId;
    }

    public Integer getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(Integer paperStatus) {
        this.paperStatus = paperStatus;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType == null ? null : paperType.trim();
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract == null ? null : paperAbstract.trim();
    }

    @Override
    public String toString() {
        return "Paper{" +
                "id=" + id +
                ", paperTitle='" + paperTitle + '\'' +
                ", creatAt=" + creatAt +
                ", creatUserId=" + creatUserId +
                ", paperStatus=" + paperStatus +
                ", paperType='" + paperType + '\'' +
                ", paperAbstract='" + paperAbstract + '\'' +
                '}';
    }
}