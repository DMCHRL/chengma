package com.chengma.devplatform.domain;


import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A SysForm.
 */
@Entity
@Table(name = "t_tlb_ea_group")
public class TlbEaGroup extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_ea_group")
    private String eaGroup;

    @Column(name = "c_ea_group_name")
    private String eaGroupName;

    public String getEaGroup() {
        return eaGroup;
    }

    public void setEaGroup(String eaGroup) {
        this.eaGroup = eaGroup;
    }

    public String getEaGroupName() {
        return eaGroupName;
    }

    public void setEaGroupName(String eaGroupName) {
        this.eaGroupName = eaGroupName;
    }
}
