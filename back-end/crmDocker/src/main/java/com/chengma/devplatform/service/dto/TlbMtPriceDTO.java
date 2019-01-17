package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * A DTO for the SysUserRole entity.
 */
public class TlbMtPriceDTO  {

    private String id;

    private Double sell;

    private Double buy;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;

    private String symbol;

    private String high1440;

    private String high30;

    private String high60;

    private String low1440;

    private String low30;

    private String low60;

    private String open1440;

    private String open30;

    private String open60;

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getHigh1440() {
        return high1440;
    }

    public void setHigh1440(String high1440) {
        this.high1440 = high1440;
    }

    public String getHigh30() {
        return high30;
    }

    public void setHigh30(String high30) {
        this.high30 = high30;
    }

    public String getHigh60() {
        return high60;
    }

    public void setHigh60(String high60) {
        this.high60 = high60;
    }

    public String getLow1440() {
        return low1440;
    }

    public void setLow1440(String low1440) {
        this.low1440 = low1440;
    }

    public String getLow30() {
        return low30;
    }

    public void setLow30(String low30) {
        this.low30 = low30;
    }

    public String getLow60() {
        return low60;
    }

    public void setLow60(String low60) {
        this.low60 = low60;
    }

    public String getOpen1440() {
        return open1440;
    }

    public void setOpen1440(String open1440) {
        this.open1440 = open1440;
    }

    public String getOpen30() {
        return open30;
    }

    public void setOpen30(String open30) {
        this.open30 = open30;
    }

    public String getOpen60() {
        return open60;
    }

    public void setOpen60(String open60) {
        this.open60 = open60;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
