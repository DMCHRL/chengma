package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppTrainApplyService;
import com.chengma.devplatform.service.dto.HppTrainApplyDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * REST controller for managingHppTrainApply.
 */
@RestController
@RequestMapping("/api")
public class HppTrainApplyResource {

    private final Logger log = LoggerFactory.getLogger(HppTrainApplyResource.class);

    private static final String ENTITY_NAME = "hppTrainApply";

    private final HppTrainApplyService hppTrainApplyService;

    public HppTrainApplyResource(HppTrainApplyService hppTrainApplyService) {
        this.hppTrainApplyService =hppTrainApplyService;
    }


    /**
     * 报名培训 + 获取预付单
     * @param hppTrainApplyDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_train_apply/applyHppTrain")
    @Timed
    public ResponseEntity<ResponseResult> applyHppTrain(HttpServletRequest request , @RequestBody HppTrainApplyDTO hppTrainApplyDTO) throws URISyntaxException {

        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppTrainApplyService.checkHppTrainApply(hppTrainApplyDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to save HppTrainApply : {}", hppTrainApplyDTO);
        HashMap<String,Object> result = hppTrainApplyService.createHppTrainApplyDTO(request,hppTrainApplyDTO);
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
    @PostMapping("/hpp_train_apply/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppTrainApply");
        Page<HppTrainApplyDTO> page = hppTrainApplyService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_train");
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
