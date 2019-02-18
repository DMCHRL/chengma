package com.chengma.devplatform.service;

import com.chengma.devplatform.domain.HppNoticeSign;
import com.chengma.devplatform.service.dto.HppNoticeDTO;
import com.chengma.devplatform.service.dto.HppNoticeSignDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */
public interface HppNoticeSignService {

    Page<HppNoticeSignDTO> pageList(HashMap<String, Object> params);

    HppNoticeSignDTO save(HppNoticeSignDTO hppVideoDTO);

    HppNoticeSignDTO createHppNoticeSignDTO(HppNoticeSignDTO hppVideoDTO);

    HashMap<String, Object> checkCreateHppNoticeSignDTO(HppNoticeSignDTO hppVideoDTO);

    HppNoticeSignDTO findOne(String id);

    List<HppNoticeSignDTO> findAll();

    //手机用户删除消息记录
    void delete(String id);

    /**
     * 未读消息个数
     * @param mail
     * @return
     */
    HppNoticeSignDTO unreadNum(String mail);

    /**
     * app端加载消息列表
     * @param mail
     * @return
     */
    List<HppNoticeDTO> readList(String mail);

    /**
     * 标为已读状态
     * @param id
     */
    void readOne(String id);

    void deleteByNoticeId(String noticeId);

}

