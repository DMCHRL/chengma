package com.chengma.devplatform.service.dto;


import java.util.List;

/**
 * A DTO for the SysUserRole entity.
 */
public class SendNoticeDTO {

    private String noticeId; //消息Id

    private String type;  //发送类型 multiple(单个或多个) whole(全体)

    private List<MobileUserDTO> userCidDTOList;

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

    public List<MobileUserDTO> getMobileUserDTOList() {
        return userCidDTOList;
    }

    public void setMobileUserDTOList(List<MobileUserDTO> userCidDTOList) {
        this.userCidDTOList = userCidDTOList;
    }
}
