package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppLive;
import com.chengma.devplatform.service.HppLiveService;
import com.chengma.devplatform.service.dto.HppAdvertisementDTO;
import com.chengma.devplatform.service.dto.HppLiveDTO;
import com.chengma.devplatform.service.mapper.HppLiveMapper;
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
import java.util.List;

/**
 * REST controller for managing HppLive.
 */
@RestController
@RequestMapping("/api")
public class HppLiveResource {

    private final Logger log = LoggerFactory.getLogger(HppLiveResource.class);

    private static final String ENTITY_NAME = "hppLive";

    private final HppLiveService hppLiveService;
    

    public HppLiveResource(HppLiveService hppLiveService) {
        this.hppLiveService = hppLiveService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_live/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppAdvertisement");
        Page<HppLiveDTO> page = hppLiveService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_live");
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
     * 保存直播信息
     * @param hppLiveDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_live/createHppLive")
    @Timed
    public ResponseEntity<ResponseResult> updateHppLive(@RequestBody HppLiveDTO hppLiveDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a HppLive : {}", hppLiveDTO);
        HashMap<String, Object> checkMap = hppLiveService.checkCreateHppLiveDTO(hppLiveDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        HppLiveDTO hppLiveDTO1 = hppLiveService.createHppLiveDTO(hppLiveDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppLiveDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /user/get/{id} ：获取直播信息
     *
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/hpp_live/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        HppLiveDTO HppLiveDTO = hppLiveService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(HppLiveDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /user/get/{id} ：获取直播信息
     *
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/hpp_live/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        hppLiveService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /user/get/{id} ：获取直播信息
     *
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/hpp_live/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        List<HppLiveDTO> list = hppLiveService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
