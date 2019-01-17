package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppNoticeDTO;
import com.chengma.devplatform.service.dto.HppSendNoticeDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */
public interface HppNoticeService {

    Page<HppNoticeDTO> pageList(HashMap<String, Object> params);

    HppNoticeDTO save(HppNoticeDTO hppVideoDTO);

    HppNoticeDTO createHppNoticeDTO(HppNoticeDTO hppVideoDTO);

    HashMap<String, Object> checkCreateHppNoticeDTO(HppNoticeDTO hppVideoDTO);

    HashMap<String,Object> sendNotice(HppSendNoticeDTO hppSendNoticeDTO);

    void revokeNotice(String id);

    HppNoticeDTO findOne(String id);

    List<HppNoticeDTO> findAll();

    void delete(String id);

    void deleteFlag(String id);
}
