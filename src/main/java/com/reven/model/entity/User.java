package com.reven.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "test_user")
public class User extends BaseEntity implements Serializable {
    @Id
    private Integer id;

    /**
     * @Fields userName 用户名
     */
    @Column(name = "`user_name`")
    private String userName;

    private String enName;

    /**
     * @Fields password 密码
     */
    private String password;

    /**
     * @Fields salt 盐
     */
    private String salt;

    @Column(name = "`create_time`")
    private Date createTime;

    @OneToOne
    @JoinColumn(name = "detail_id", referencedColumnName = "id", insertable = false)
    private UserDetail detail;

    @OneToMany(fetch=FetchType.EAGER)//EAGER，那么表示取出这条数据时，它关联的数据也同时取出放入内存中 
    @JoinColumn(name = "userId", insertable = false)
    private List<UserRole> roles;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserDetail getDetail() {
        return detail;
    }

    public void setDetail(UserDetail detail) {
        this.detail = detail;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

}