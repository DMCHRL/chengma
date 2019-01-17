package com.chengma.devplatform.domain;

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/7.
 */
@Entity
@Table(name = "t_tlb_commission_trace")
public class TlbCommissionTrace extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="t_commission_id")
    private String commissionId;

    @Column(name = "i_order_no")
    private Long orderNo;

    @Column(name = "c_money")
    private Double money;

    @Column(name = "i_lot_amount")
    private Double lotAmount;

    @Column(name = "d_create_at")
    private Date createAt;

    public String getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(String commissionId) {
        this.commissionId = commissionId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Double getLotAmount() {
        return lotAmount;
    }

    public void setLotAmount(Double lotAmount) {
        this.lotAmount = lotAmount;
    }
}
