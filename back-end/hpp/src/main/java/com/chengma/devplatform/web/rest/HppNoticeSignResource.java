package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppNoticeSignService;
import com.chengma.devplatform.service.dto.HppNoticeDTO;
import com.chengma.devplatform.service.dto.HppNoticeSignDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingHppNoticeSign.
 */
@RestController
@RequestMapping("/api")
public class HppNoticeSignResource {

    private final Logger log = LoggerFactory.getLogger(HppNoticeSignResource.class);

    private static final String ENTITY_NAME = "hppNoticeSign";

    private final HppNoticeSignService hppNoticeSignService;

    public HppNoticeSignResource(HppNoticeSignService hppNoticeSignService) {
        this.hppNoticeSignService =hppNoticeSignService;
    }


    /**
     * 加载未读消息个数(app)
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/hpp_notice_sign/unreadNum")
    @Timed
    public ResponseEntity<ResponseResult> saveHppNoticeSign() throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HppNoticeSignDTO hppNoticeSignDTO = hppNoticeSignService.unreadNum();
        log.debug("REST request to get a HppNoticeSign unread num: {}");
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppNoticeSignDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *读取消息通知list(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_notice_sign/read")
    @Timed
    public ResponseEntity<ResponseResult> readList() {
        log.debug("REST request to get a list of HppNoticeSign");
        List<HppNoticeDTO> list = hppNoticeSignService.readList();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(retMap);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *读取消息通知list(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_notice_sign/readOne/{signId}")
    @Timed
    public ResponseEntity<ResponseResult> readOne(@PathVariable String signId) {
        log.debug("REST request to get a list of HppNoticeSign");
        hppNoticeSignService.readOne(signId);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *删除消息通知(app)
     * @param id the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_notice_sign/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to get a list of HppNoticeSign");
        hppNoticeSignService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
