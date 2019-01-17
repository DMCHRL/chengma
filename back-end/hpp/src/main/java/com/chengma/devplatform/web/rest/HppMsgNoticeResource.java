package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppMsgNotice;
import com.chengma.devplatform.service.HppMsgNoticeService;
import com.chengma.devplatform.service.dto.HppMsgNoticeDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingHppMsgNotice.
 */
@RestController
@RequestMapping("/api")
public class HppMsgNoticeResource {

    private final Logger log = LoggerFactory.getLogger(HppMsgNoticeResource.class);

    private static final String ENTITY_NAME = "hppMsgNotice";

    private final HppMsgNoticeService hppMsgNoticeService;

    public HppMsgNoticeResource(HppMsgNoticeService hppMsgNoticeService) {
        this.hppMsgNoticeService =hppMsgNoticeService;
    }


    /**
     * 获取新消息(app)
     * @param mobileNum
     * @return
     */
    @GetMapping("/hpp_msg_notice/readMsg/{mobileNum}")
    @Timed
    public ResponseEntity<ResponseResult> readMsg(@PathVariable String mobileNum) {
        log.debug("REST request to get list of HppMsgNotice : {}", mobileNum);
        ResponseResult json = new ResponseResult();
        List<HppMsgNoticeDTO> list =hppMsgNoticeService.readMsg(mobileNum);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
