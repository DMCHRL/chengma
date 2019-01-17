package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * A DTO for the SysUserRole entity.
 */
public class TlbMtPriceHistoryDTO {

  /*  private String id;

    private Double sell;

    private Double buy;*/

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;

    private String symbol;

   /* private String high1440;

    private String high30;

    private String high60;

    private String low1440;

    private String low30;

    private String low60;

    private String open1440;

    private String open30;

    private String open60;*/

    private Double spread;

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

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }
}
