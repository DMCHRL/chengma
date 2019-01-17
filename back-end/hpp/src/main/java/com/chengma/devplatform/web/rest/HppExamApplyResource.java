package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppExamApplyService;
import com.chengma.devplatform.service.dto.HppExamApplyDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * REST controller for managingHppExamApply.
 */
@RestController
@RequestMapping("/api")
public class HppExamApplyResource {

    private final Logger log = LoggerFactory.getLogger(HppExamApplyResource.class);

    private static final String ENTITY_NAME = "hppExamApply";

    private final HppExamApplyService hppExamApplyService;

    public HppExamApplyResource(HppExamApplyService hppExamApplyService) {
        this.hppExamApplyService =hppExamApplyService;
    }


    /**
     * 报名考证 + 获取预付单
     * @param hppExamApplyDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_exam_apply/applyHppExam")
    @Timed
    public ResponseEntity<ResponseResult> applyHppExam(HttpServletRequest request ,@RequestBody HppExamApplyDTO hppExamApplyDTO) throws URISyntaxException {

        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppExamApplyService.checkHppExamApply(hppExamApplyDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to save HppExamApply : {}", hppExamApplyDTO);
        HashMap<String,Object> result = hppExamApplyService.createHppExamApplyDTO(request,hppExamApplyDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(result.get("statusCode"))){
            json.setMsgCode(result.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result.get("data"));
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_exam_apply/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppExamApply");
        Page<HppExamApplyDTO> page = hppExamApplyService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_exam");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }





}
