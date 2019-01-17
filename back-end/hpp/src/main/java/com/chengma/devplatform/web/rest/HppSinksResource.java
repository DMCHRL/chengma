package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppSinksService;
import com.chengma.devplatform.service.dto.HppSinksDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang.StringUtils;
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
 * REST controller for managingHppSinks.
 */
@RestController
@RequestMapping("/api")
public class HppSinksResource {

    private final Logger log = LoggerFactory.getLogger(HppSinksResource.class);

    private static final String ENTITY_NAME = "hppSinks";

    private final HppSinksService hppSinksService;

    public HppSinksResource(HppSinksService hppSinksService) {
        this.hppSinksService =hppSinksService;
    }


    /**
     * 保存汇商信息
     * @param hppSinksDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_sinks/saveHppSinks")
    @Timed
    public ResponseEntity<ResponseResult> saveHppSinks(@RequestBody HppSinksDTO hppSinksDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        if(StringUtils.isBlank(hppSinksDTO.getId())){
            HashMap<String, Object> checkMap = hppSinksService.checkSave(hppSinksDTO);
            if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
                json.setMsgCode(checkMap.get("msg").toString());
                return new ResponseEntity<>(json, null, HttpStatus.OK);
            }
        }
        log.debug("REST request to save HppSinks : {}", hppSinksDTO);
        HppSinksDTO hppSinksDTO1 = hppSinksService.save(hppSinksDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppSinksDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_sinks/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppSinks");
        Page<HppSinksDTO> page = hppSinksService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_sinks");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    @GetMapping("/hpp_sinks/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppSinks : {}", id);
        ResponseResult json = new ResponseResult();
        hppSinksService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_sinks/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get a HppSinks : {}", id);
        ResponseResult json = new ResponseResult();
        HppSinksDTO hppSinksDTO= hppSinksService.findOne(id);
        json.setData(hppSinksDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_sinks/findList")
    @Timed
    public ResponseEntity<ResponseResult> findList() {
        log.debug("REST request to get a list of HppSinks : {}");
        ResponseResult json = new ResponseResult();
        List<HppSinksDTO> list= hppSinksService.findList();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
