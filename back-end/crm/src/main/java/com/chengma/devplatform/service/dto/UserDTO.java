package com.chengma.devplatform.service.dto;

import com.chengma.devplatform.CustomDateSerializer;
import com.chengma.devplatform.common.constant.EnumRoleName;
import com.chengma.devplatform.config.Constants;
import com.chengma.devplatform.domain.Authority;
import com.chengma.devplatform.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import liquibase.util.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private String id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private String createdBy;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    private String lastModifiedBy;
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date lastModifiedDate;

    private Set<String> authorities;

    private String activationKey;

    private String resetKey;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date resetDate = null;

    private String charNo;

    private String sex;

    private String idNo;

    private String deptId;

    private String tel;

    private String mobile;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date onboardDate;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date expireDate;

    private String status;

    private String delFlag;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date passwordExpireDate;

    private String department;

    private String sysUsersId;

    private String activatedShow;

    private Long errorCount;

    private String openId;

    private String isExpire;

    private String passwordRemark;

    private String parentId;

    private String isCompany;
    private String isProxy;
    private String eara;
    private Long accountCount;
    private Long proxyCount;

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(Long accountCount) {
        this.accountCount = accountCount;
    }

    public Long getProxyCount() {
        return proxyCount;
    }

    public void setProxyCount(Long proxyCount) {
        this.proxyCount = proxyCount;
    }

    public String getSysUsersId() {
        return sysUsersId;
    }

    public void setSysUsersId(String sysUsersId) {
        this.sysUsersId = sysUsersId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public String getCharNo() {
        return charNo;
    }

    public void setCharNo(String charNo) {
        this.charNo = charNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getOnboardDate() {
        return onboardDate;
    }

    public void setOnboardDate(Date onboardDate) {
        this.onboardDate = onboardDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getPasswordExpireDate() {
        return passwordExpireDate;
    }

    public void setPasswordExpireDate(Date passwordExpireDate) {
        this.passwordExpireDate = passwordExpireDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getActivatedShow() {
        return activatedShow;
    }

    public void setActivatedShow(String activatedShow) {
        this.activatedShow = activatedShow;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    public String getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(String isExpire) {
        this.isExpire = isExpire;
    }

    public String getPasswordRemark() {
        return passwordRemark;
    }

    public void setPasswordRemark(String passwordRemark) {
        this.passwordRemark = passwordRemark;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany;
    }

    public String getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(String isProxy) {
        this.isProxy = isProxy;
    }

    public String getEara() {
        return eara;
    }

    public void setEara(String eara) {
        this.eara = eara;
    }

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(String id, String login, String firstName, String lastName,
                   String email, String imageUrl, boolean activated, String langKey,
                   String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate,
                   Set<Authority> authorities, String activationKey, String resetKey, Date resetDate, String charNo,
                   String sex, String idNo, String deptId, String tel, String mobile, Date onboardDate,
                   Date expireDate, String status, String delFlag, Date passwordExpireDate, String department, String sysUsersId, Long errorCount, String isCompany, String isProxy,String parentId) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.langKey = langKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.authorities = stringsFromAuthorities(authorities);
        this.activationKey = activationKey;
        this.resetKey = resetKey;
        this.resetDate = resetDate;
        this.charNo = charNo;
        this.sex = sex;
        this.idNo = idNo;
        this.deptId = deptId;
        this.tel = tel;
        this.mobile = mobile;
        this.onboardDate = onboardDate;
        this.expireDate = expireDate;
        this.status = status;
        this.delFlag = delFlag;
        this.passwordExpireDate = passwordExpireDate;
        this.sysUsersId = sysUsersId;
        this.department = department;
        this.errorCount = errorCount;
        this.isCompany = isCompany;
        this.isProxy = isProxy;
        this.parentId=parentId;

        this.setLastName(StringUtils.isEmpty(this.getDepartment()) ? EnumRoleName.user.value() : EnumRoleName.valueOf(this.getDepartment()).value());
    }

    public UserDTO(User user) {
        this(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getImageUrl(), user.getActivated(), user.getLangKey(),
                user.getCreatedBy(), user.getCreatedDate(), user.getLastModifiedBy(), user.getLastModifiedDate(),
                user.getAuthorities(),
                user.getActivationKey(), user.getResetKey(), user.getResetDate(), user.getCharNo(),
                user.getSex(), user.getIdNo(), user.getDeptId(), user.getTel(), user.getMobile(), user.getOnboardDate(),
                user.getExpireDate(), user.getStatus(), user.getDelFlag(), user.getPasswordExpireDate(), user.getDepartment(), null, user.getErrorCount(),user.getIsCompany(), user.getIsProxy(),user.getParentId())
        ;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Set<String> stringsFromAuthorities(Set<Authority> authorities) {

        Set<String> strings = new HashSet<>();
        Iterator<Authority> it = authorities.iterator();
        while (it.hasNext()) {
            Authority auth = it.next();
            strings.add(auth.getName());
        }
        return strings;
    }
}
