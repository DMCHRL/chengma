package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.NoticeSignService;
import com.chengma.devplatform.service.dto.NoticeDTO;
import com.chengma.devplatform.service.dto.NoticeSignDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingNoticeSign.
 */
@RestController
@RequestMapping("/api")
public class NoticeSignResource {

    private final Logger log = LoggerFactory.getLogger(NoticeSignResource.class);

    private static final String ENTITY_NAME = "noticeSign";

    private final NoticeSignService noticeSignService;

    public NoticeSignResource(NoticeSignService noticeSignService) {
        this.noticeSignService =noticeSignService;
    }


    /**
     * 加载未读消息个数(app)
     * @param mobile
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/notice_sign/unreadNum/{mobile}")
    @Timed
    public ResponseEntity<ResponseResult> saveNoticeSign(@PathVariable String mobile) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        NoticeSignDTO hoticeSignDTO = noticeSignService.unreadNum(mobile);
        log.debug("REST request to get a NoticeSign unread num: {}", mobile);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hoticeSignDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *读取消息通知(app)
     * @param mobile the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/notice_sign/read/{mobile}")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@PathVariable String mobile) {
        log.debug("REST request to get a list of NoticeSign");
        List<NoticeDTO> list = noticeSignService.read(mobile);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *删除消息通知(app)
     * @param id the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/notice_sign/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to get a list of NoticeSign");
        noticeSignService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
