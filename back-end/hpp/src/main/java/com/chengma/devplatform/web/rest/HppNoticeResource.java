package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppNoticeService;
import com.chengma.devplatform.service.dto.HppNoticeDTO;
import com.chengma.devplatform.service.dto.HppSendNoticeDTO;
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
 * REST controller for managingHppNotice.
 */
@RestController
@RequestMapping("/api")
public class HppNoticeResource {

    private final Logger log = LoggerFactory.getLogger(HppNoticeResource.class);

    private static final String ENTITY_NAME = "hppNotice";

    private final HppNoticeService hppNoticeService;

    public HppNoticeResource(HppNoticeService hppNoticeService) {
        this.hppNoticeService =hppNoticeService;
    }


    /**
     * 保存消息通知
     * @param hppNoticeDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_notice/saveHppNotice")
    @Timed
    public ResponseEntity<ResponseResult> saveHppNotice(@RequestBody HppNoticeDTO hppNoticeDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppNoticeService.checkCreateHppNoticeDTO(hppNoticeDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save HppNotice : {}", hppNoticeDTO);
        HppNoticeDTO hppNoticeDTO1 = hppNoticeService.createHppNoticeDTO(hppNoticeDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppNoticeDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_notice/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppNotice");
        Page<HppNoticeDTO> page = hppNoticeService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_notice");
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
    @GetMapping("/hpp_notice/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id){
        log.debug("REST request to get one of HppNotice");
        HppNoticeDTO hppNoticeDTO = hppNoticeService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppNoticeDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 发送消息通知
     * @param hppSendNoticeDTO
     * @return
     */
    @PostMapping("/hpp_notice/sendNotice")
    @Timed
    public ResponseEntity<ResponseResult> sendNotice(@RequestBody HppSendNoticeDTO hppSendNoticeDTO) {
        log.debug("REST request to sendNotice");
        HashMap<String, Object> result = hppNoticeService.sendNotice(hppSendNoticeDTO);
        ResponseResult json = new ResponseResult();
        json.setData(result.get("msg"));
        json.setStatusCode((String)result.get("statusCode"));
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 撤销消息通知
     * @param id
     * @return
     *//*
    @GetMapping("/hpp_notice/revokeNotice/{id}")
    @Timed
    public ResponseEntity<ResponseResult> revokeNotice(@PathVariable String id) {
        log.debug("REST request to sendNotice");
        hppNoticeService.revokeNotice(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/



    /**
     * Post  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_notice/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to get a list of HppNotice");
        List<HppNoticeDTO> list = hppNoticeService.findAll();
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
    @GetMapping("/hpp_notice/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to sendNotice");
        //hppNoticeService.deleteFlag(id);
        hppNoticeService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
