package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.NoticeDTO;
import com.chengma.devplatform.service.dto.SendNoticeDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */
public interface NoticeService {

    Page<NoticeDTO> pageList(HashMap<String, Object> params);

    NoticeDTO save(NoticeDTO noticeDTO);

    NoticeDTO createNoticeDTO(NoticeDTO noticeDTO);

    HashMap<String, Object> checkCreateNoticeDTO(NoticeDTO noticeDTO);

    HashMap<String,Object> sendNotice(SendNoticeDTO SendNoticeDTO);

    void revokeNotice(String id);

    NoticeDTO findOne(String id);

    List<NoticeDTO> findAll();

    void delete(String id);

    void deleteFlag(String id);
}
