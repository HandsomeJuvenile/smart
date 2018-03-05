package com.ace.smart.entity;

import com.ace.smart.annotation.RowName;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2017-12-07
 */

/**
 *  @Data注解在类上；包含了@ToString，@EqualsAndHashCode，@Getter / @Setter和@RequiredArgsConstructor的功能，
 * 提供类所有属性的 getter 和 setter 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 */
@Data
public class PUser {
    private Long id;
    /**
     * 用户登录名
     */
    @NotNull(message = "登录名必填")
    @RowName(name="用户名")
    private String userLoginName;

    /**
     * 用户昵称
     */
    @RowName(name="真实姓名")
    private String nickname;
    private String rName;
    private Long rId;
    /**
     * 邮箱|登录帐号
     */
    @Email(message = "邮箱格式有误！")
    @RowName(name="邮箱")
    private String email;
    @RowName(name="年龄")
    private Integer uAge;
    @RowName(name="住址")
    private String uAddress;
    @RowName(name="手机号")
    private String phone;

    /**
     * 密码
     */
    @NotNull(message = "密码不可以不填！")
    private String pswd;

    /**
     * 创建时间
     */
    @RowName(name="注册时间")
    private String createTime;

    /**
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 1:有效，0:禁止登录
     */
    private String status;

    /**
     * 自我介绍
     */
    @RowName(name="自我介绍")
    private String selfIntroduction;
    private List<PRole> pRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getuAge() {
        return uAge;
    }

    public void setuAge(Integer uAge) {
        this.uAge = uAge;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public List<PRole> getpRoles() {
        return pRoles;
    }

    public void setpRoles(List<PRole> pRoles) {
        this.pRoles = pRoles;
    }
}