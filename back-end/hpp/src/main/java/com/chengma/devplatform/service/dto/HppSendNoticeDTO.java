package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * A DTO for the SysUserRole entity.
 */
public class HppSendNoticeDTO {

    private String noticeId; //消息Id

    private String type;  //发送类型 multiple(单个或多个) whole(全体)

    private List<HppMobileUserDTO> hppMobileUserDTOList;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HppMobileUserDTO> getHppMobileUserDTOList() {
        return hppMobileUserDTOList;
    }

    public void setHppMobileUserDTOList(List<HppMobileUserDTO> hppMobileUserDTOList) {
        this.hppMobileUserDTOList = hppMobileUserDTOList;
    }
}
