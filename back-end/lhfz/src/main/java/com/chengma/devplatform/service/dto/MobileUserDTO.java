package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * A SysRole.
 */
public class MobileUserDTO {

    private String id;

    private String name;

    private String headImg;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    private String mobile;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    private String status;

    private String idPositive;        //身份证正面

    private String idNegative;        //身份证反面

    private String cid;        //个推识别Id

    private String email;        //邮箱

    private String password;        //支付密码

    private String oldPassword;        //支付密码

    private String userId;

    private String department; //身份

    private Double balance;   //余额

    private Double total;  //账户总资产

    private Double used;  //当前投资金额

    private Double netProfit;  //累计净收益

    private Double fundProfit;  //当前入伙盈亏

    private Double avgYearRate;  //发起产品平均年化收益率

    private Double totalProfit;  //历史总收益

    private Double successRate;  //发起成功率

    private Integer finish;  //清盘数量

    private Integer profitNum;  //清盘盈利数量

    private Double totalPreFund;  //历史募集金额

    private Double avgPreFund;  //历史平均募集金额

    private Integer level; //等级

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdPositive() {
        return idPositive;
    }

    public void setIdPositive(String idPositive) {
        this.idPositive = idPositive;
    }

    public String getIdNegative() {
        return idNegative;
    }

    public void setIdNegative(String idNegative) {
        this.idNegative = idNegative;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getUsed() {
        return used;
    }

    public void setUsed(Double used) {
        this.used = used;
    }

    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }

    public Double getFundProfit() {
        return fundProfit;
    }

    public void setFundProfit(Double fundProfit) {
        this.fundProfit = fundProfit;
    }

    public Double getAvgYearRate() {
        return avgYearRate;
    }

    public void setAvgYearRate(Double avgYearRate) {
        this.avgYearRate = avgYearRate;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public Integer getProfitNum() {
        return profitNum;
    }

    public void setProfitNum(Integer profitNum) {
        this.profitNum = profitNum;
    }

    public Double getTotalPreFund() {
        return totalPreFund;
    }

    public void setTotalPreFund(Double totalPreFund) {
        this.totalPreFund = totalPreFund;
    }

    public Double getAvgPreFund() {
        return avgPreFund;
    }

    public void setAvgPreFund(Double avgPreFund) {
        this.avgPreFund = avgPreFund;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
