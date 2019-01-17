package com.suitong.devplatform.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by phwen on 2017/9/18.
 */
@Entity
@Table(name = "t_serial_no")
public class SerialNo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}
