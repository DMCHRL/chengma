package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.NoticeDTO;
import com.chengma.devplatform.service.dto.NoticeSignDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */
public interface NoticeSignService {

    Page<NoticeSignDTO> pageList(HashMap<String, Object> params);

    NoticeSignDTO save(NoticeSignDTO noticeSignDTO);

    NoticeSignDTO createNoticeSignDTO(NoticeSignDTO noticeSignDTO);

    HashMap<String, Object> checkCreateNoticeSignDTO(NoticeSignDTO noticeSignDTO);

    NoticeSignDTO findOne(String id);

    List<NoticeSignDTO> findAll();

    //手机用户删除消息记录
    void delete(String id);

    /**
     * 未读消息个数
     * @param mobile
     * @return
     */
    NoticeSignDTO unreadNum(String mobile);

    List<NoticeDTO> read(String mobile);

    void deleteByNoticeId(String noticeId);


}

