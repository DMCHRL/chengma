package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppTrainService;
import com.chengma.devplatform.service.dto.HppTrainApplyDTO;
import com.chengma.devplatform.service.dto.HppTrainDTO;
import com.chengma.devplatform.service.dto.HppTrainDTO;
import com.chengma.devplatform.service.dto.HppTrainDTO;
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
 * REST controller for managingHppTrain.
 */
@RestController
@RequestMapping("/api")
public class HppTrainResource {

    private final Logger log = LoggerFactory.getLogger(HppTrainResource.class);

    private static final String ENTITY_NAME = "hppTrain";

    private final HppTrainService hppTrainService;

    public HppTrainResource(HppTrainService hppTrainService) {
        this.hppTrainService =hppTrainService;
    }


    /**
     * 保存培训信息
     * @param hppTrainDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_train/saveHppTrain")
    @Timed
    public ResponseEntity<ResponseResult> saveHppTrain(@RequestBody HppTrainDTO hppTrainDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppTrainService.checkHppTrain(hppTrainDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save HppTrain : {}", hppTrainDTO);
        HppTrainDTO hppTrainDTO1 = hppTrainService.save(hppTrainDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppTrainDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_train/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppTrain");
        Page<HppTrainDTO> page = hppTrainService.pageList(params);
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

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    @GetMapping("/hpp_train/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id){
        log.debug("REST request to get one of HppTrain");
        HppTrainDTO hppTrainDTO = hppTrainService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppTrainDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_train/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppTrain : {}", id);
        ResponseResult json = new ResponseResult();
        Long applyNum=hppTrainService.applyCount(id);
        if(applyNum>0){
            json.setStatusCode(ResponseResult.FAIL_CODE);
            json.setMsgCode("该培训已有"+applyNum+"人报名参加,不能删除。");
        }else{
            hppTrainService.delete(id);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * Post  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_train/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to get a list of HppTrain");
        List<HppTrainDTO> list = hppTrainService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
