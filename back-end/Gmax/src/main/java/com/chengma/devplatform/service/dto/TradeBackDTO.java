package com.chengma.devplatform.service.dto;

/**
 * Created by Administrator on 2018/7/19.
 */
public class TradeBackDTO {

    private String result;
    private String lots;
    private String symbol;
    private String remark;
    private String state;
    private String rstoplost;
    private String closetime;
    private String rorderid;
    private String type;

    public String getRprice() {
        return rprice;
    }

    public void setRprice(String rprice) {
        this.rprice = rprice;
    }

    private String rprice;
    private String closeprice;
    private String id;
    private String time;
    private String rtime;
    private String isReal;
    private String rstopwin;
    private String account;
    private String openprice;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLots() {
        return lots;
    }

    public void setLots(String lots) {
        this.lots = lots;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRstoplost() {
        return rstoplost;
    }

    public void setRstoplost(String rstoplost) {
        this.rstoplost = rstoplost;
    }

    public String getClosetime() {
        return closetime;
    }

    public void setClosetime(String closetime) {
        this.closetime = closetime;
    }

    public String getRorderid() {
        return rorderid;
    }

    public void setRorderid(String rorderid) {
        this.rorderid = rorderid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCloseprice() {
        return closeprice;
    }

    public void setCloseprice(String closeprice) {
        this.closeprice = closeprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public String getIsReal() {
        return isReal;
    }

    public void setIsReal(String isReal) {
        this.isReal = isReal;
    }

    public String getRstopwin() {
        return rstopwin;
    }

    public void setRstopwin(String rstopwin) {
        this.rstopwin = rstopwin;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOpenprice() {
        return openprice;
    }

    public void setOpenprice(String openprice) {
        this.openprice = openprice;
    }
}
