package org.gdou.model.po;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public class Oauths {

    public Oauths() {
    }

    public Oauths(Integer id, Integer userId, String oauthType, String oauthId, String unionid, String credential) {
        this.id = id;
        this.userId = userId;
        this.oauthType = oauthType;
        this.oauthId = oauthId;
        this.unionid = unionid;
        this.credential = credential;
    }

    private Integer id;

    private Integer userId;

    private String oauthType;

    @NotBlank(message = "账号不能为空")
    private String oauthId;

    private String unionid;

    @NotBlank(message = "密码不能为空")
    private String credential;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOauthType() {
        return oauthType;
    }

    public void setOauthType(String oauthType) {
        this.oauthType = oauthType == null ? null : oauthType.trim();
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId == null ? null : oauthId.trim();
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential == null ? null : credential.trim();
    }
}