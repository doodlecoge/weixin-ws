package wang.huaichao.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/15.
 */
@Entity
@Table(name = "users")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class User {
    @Id
    @Column(name = "wxid")
    private String wxId;

    @Column(name = "wbx_username")
    private String wbxUsername;

    @Column(name = "wbx_password")
    private String wbxPassword;

    @Column(name = "wbx_site_url")
    private String wbxSiteUrl;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getWbxUsername() {
        return wbxUsername;
    }

    public void setWbxUsername(String wbxUsername) {
        this.wbxUsername = wbxUsername;
    }

    public String getWbxPassword() {
        return wbxPassword;
    }

    public void setWbxPassword(String wbxPassword) {
        this.wbxPassword = wbxPassword;
    }

    public String getWbxSiteUrl() {
        return wbxSiteUrl;
    }

    public void setWbxSiteUrl(String wbxSiteUrl) {
        this.wbxSiteUrl = wbxSiteUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
