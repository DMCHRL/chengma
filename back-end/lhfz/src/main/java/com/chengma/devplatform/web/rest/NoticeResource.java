package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.NoticeService;
import com.chengma.devplatform.service.dto.NoticeDTO;
import com.chengma.devplatform.service.dto.SendNoticeDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingNotice.
 */
@RestController
@RequestMapping("/api")
public class NoticeResource {

    private final Logger log = LoggerFactory.getLogger(NoticeResource.class);

    private static final String ENTITY_NAME = "notice";

    private final NoticeService noticeService;

    public NoticeResource(NoticeService noticeService) {
        this.noticeService =noticeService;
    }


    /**
     * 保存消息通知
     * @param noticeDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/notice/saveNotice")
    @Timed
    public ResponseEntity<ResponseResult> saveNotice(@RequestBody NoticeDTO noticeDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = noticeService.checkCreateNoticeDTO(noticeDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save Notice : {}", noticeDTO);
        NoticeDTO noticeDTO1 = noticeService.createNoticeDTO(noticeDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(noticeDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/notice/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of Notice");
        Page<NoticeDTO> page = noticeService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notice");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    @GetMapping("/notice/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id){
        log.debug("REST request to get one of Notice");
        NoticeDTO noticeDTO = noticeService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(noticeDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 发送消息通知
     * @param sendNoticeDTO
     * @return
     */
    @PostMapping("/notice/sendNotice")
    @Timed
    public ResponseEntity<ResponseResult> sendNotice(@RequestBody SendNoticeDTO sendNoticeDTO) {
        log.debug("REST request to sendNotice");
        HashMap<String, Object> result = noticeService.sendNotice(sendNoticeDTO);
        ResponseResult json = new ResponseResult();
        //json.setData(result.get("msg"));
        json.setStatusCode((String)result.get("statusCode"));
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 撤销消息通知
     * @param id
     * @return
     *//*
    @GetMapping("/notice/revokeNotice/{id}")
    @Timed
    public ResponseEntity<ResponseResult> revokeNotice(@PathVariable String id) {
        log.debug("REST request to sendNotice");
        noticeService.revokeNotice(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/



    /**
     * Post  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/notice/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to get a list of Notice");
        List<NoticeDTO> list = noticeService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * 标记删除消息通知
     * @param id
     * @return
     */
    @GetMapping("/notice/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete notice");
        //noticeService.deleteFlag(id);
        noticeService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
