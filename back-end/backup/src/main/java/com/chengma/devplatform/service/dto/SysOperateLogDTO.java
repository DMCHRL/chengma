package com.chengma.devplatform.service.dto;

import com.chengma.devplatform.domain.SysOperateLog;

import java.util.Objects;

/**
 * Created by Administrator on 2017/8/24.
 */
public class SysOperateLogDTO extends SysOperateLog {

    private String typeName;

    public String getTypeName() {
        if("1".equals(this.getType())){
            return "登陆记录";
        }else if("2".equals(this.getType())){
            return "操作记录";
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysOperateLogDTO sysOperateLogDTO = (SysOperateLogDTO) o;
        if (sysOperateLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysOperateLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysOperateLogDTO{" +
                "id=" + getId() +
                ", account='" + getAccount() + "'" +
                ", createTime='" + getCreateTime() + "'" +
                ", address='" + getAddress() + "'" +
                ", logTime='" + getLogTime() + "'" +
                ", operateContent='" + getOperateContent() + "'" +
                "}";
    }
}
