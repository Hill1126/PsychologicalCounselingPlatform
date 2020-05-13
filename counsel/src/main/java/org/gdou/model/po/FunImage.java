package org.gdou.model.po;

import java.time.LocalDateTime;

public class FunImage {
    private Integer funImageId;

    private String title;

    private String imageAbstract;

    private String imageUrl;

    private LocalDateTime createAt;

    private Integer status;

    public Integer getFunImageId() {
        return funImageId;
    }

    public void setFunImageId(Integer funImageId) {
        this.funImageId = funImageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImageAbstract() {
        return imageAbstract;
    }

    public void setImageAbstract(String imageAbstract) {
        this.imageAbstract = imageAbstract == null ? null : imageAbstract.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}