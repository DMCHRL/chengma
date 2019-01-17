package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by phwen on 2017/9/18.
 */
@Entity
@Table(name = "t_serial_no")
public class SerialNo extends BaseEntity implements Serializable {

    @Column(name = "c_type")
    private String type; //类型

    @Column(name = "c_formatter")
    private String formatter; //格式

    @Column(name = "d_date")
    private Date date; //日期

    @Column(name = "i_bit")
    private Integer bit; //流水位数

    @Column(name = "i_serial")
    private Long serial; //流水

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getBit() {
        return bit;
    }

    public void setBit(Integer bit) {
        this.bit = bit;
    }

    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "SerialNo{" +
                "type='" + type + '\'' +
                ", formatter='" + formatter + '\'' +
                ", date=" + date +
                ", bit=" + bit +
                ", serial=" + serial +
                ", id='" + id + '\'' +
                '}';
    }
}
