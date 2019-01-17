package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "t_tlb_history_k")
public class TlbHistoryK extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_cycle")
    private String cycle;

    @Column(name = "i_open")
    private Double open;

    @Column(name = "i_close")
    private Double close;

    @Column(name = "i_high")
    private Double high;

    @Column(name = "i_low")
    private Double low;

    @Column(name = "c_macd")
    private String macd;

    @Column(name = "c_dif")
    private String dif;

    @Column(name = "d_time")
    private Date time;

    @Column(name = "c_symbol")
    private String symbol;

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public String getMacd() {
        return macd;
    }

    public void setMacd(String macd) {
        this.macd = macd;
    }

    public String getDif() {
        return dif;
    }

    public void setDif(String dif) {
        this.dif = dif;
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
}
