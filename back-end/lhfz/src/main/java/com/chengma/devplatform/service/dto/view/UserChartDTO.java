package com.chengma.devplatform.service.dto.view;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * A SysRole.
 */
public class UserChartDTO {

    private Double currentFund;

    private Double originatorFund;

    private Double managerFund;

    public Double getCurrentFund() {
        return currentFund;
    }

    public void setCurrentFund(Double currentFund) {
        this.currentFund = currentFund;
    }

    public Double getOriginatorFund() {
        return originatorFund;
    }

    public void setOriginatorFund(Double originatorFund) {
        this.originatorFund = originatorFund;
    }

    public Double getManagerFund() {
        return managerFund;
    }

    public void setManagerFund(Double managerFund) {
        this.managerFund = managerFund;
    }
}
