package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.HppIntegralDTO;
import com.chengma.devplatform.service.dto.HppMsgNoticeDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface HppMsgNoticeService {


    HppMsgNoticeDTO save(HppMsgNoticeDTO hppMsgNoticeDTO);

    HppMsgNoticeDTO findOne(String id);

    List<HppMsgNoticeDTO> readMsg(String mobileNum);

    void delete(String id);
}
