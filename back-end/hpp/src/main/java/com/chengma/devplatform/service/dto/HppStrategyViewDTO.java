package com.chengma.devplatform.service.dto;

import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by Administrator on 2018/11/26.
 */
public class HppStrategyViewDTO {

    private Double equery;    //净值

    private String  days;         //增长率的时间节点

    private Double gainRate;     //收益率

    private Double inAmount;   //入金

    private Double outAmount;  //出金

    public Double getEquery() {
        return equery;
    }

    public void setEquery(Double equery) {
        this.equery = equery;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public Double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }

    public Double getGainRate() {
        return gainRate;
    }

    public void setGainRate(Double gainRate) {
        this.gainRate = gainRate;
    }
}
