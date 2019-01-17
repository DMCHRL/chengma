package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppCommentService;
import com.chengma.devplatform.service.dto.HppCommentDTO;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
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

/**
 * REST controller for managing HppComment.
 */
@RestController
@RequestMapping("/api")
public class HppCommentResource {

    private final Logger log = LoggerFactory.getLogger(HppCommentResource.class);

    private static final String ENTITY_NAME = "hppComment";

    private final HppCommentService hppCommentService;

    public HppCommentResource(HppCommentService hppCommentService) {
        this.hppCommentService = hppCommentService;
    }


    /**
     * 保存评论信息
     * @param hppCommentDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_comment/saveHppComment")
    @Timed
    public ResponseEntity<ResponseResult> saveHppComment(@RequestBody HppCommentDTO hppCommentDTO) {
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> checkMap = hppCommentService.checkSave(hppCommentDTO);
        if(!checkMap.get("statusCode").equals(ResponseResult.SUCCESS_CODE)){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to save HppComment : {}", hppCommentDTO);
        HppCommentDTO hppCommentDTO1 = hppCommentService.save(hppCommentDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppCommentDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 保存评论信息
     * @param videoId
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/hpp_comment/findForApp/{videoId}")
    @Timed
    public ResponseEntity<ResponseResult> saveHppComment(@PathVariable String videoId) {
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> response = hppCommentService.findForApp(videoId);
        log.debug("REST request to find HppComment for App : {}", videoId);
        HashMap<String,Object> retMap = new HashMap<>();
        retMap.put("list",response.get("list"));
        retMap.put("total",response.get("total"));
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(retMap);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *视频集下的评论
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_comment/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppComment");
        Page<HppCommentDTO> page = hppCommentService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_comment");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    @GetMapping("/hpp_comment/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppComment : {}", id);
        ResponseResult json = new ResponseResult();
        hppCommentService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
