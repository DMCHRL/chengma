package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppAdvertisementService;
import com.chengma.devplatform.service.dto.HppAdvertisementDTO;
import com.chengma.devplatform.service.dto.HppVideoDTO;
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
 * REST controller for managingHppAdvertisement.
 */
@RestController
@RequestMapping("/api")
public class HppAdvertisementResource {

    private final Logger log = LoggerFactory.getLogger(HppAdvertisementResource.class);

    private static final String ENTITY_NAME = "hppAdvertisement";

    private final HppAdvertisementService hppAdvertisementService;

    public HppAdvertisementResource(HppAdvertisementService hppAdvertisementService) {
        this.hppAdvertisementService =hppAdvertisementService;
    }


    /**
     * 保存培训信息
     * @param hppAdvertisementDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_advertisement/saveHppAdvertisement")
    @Timed
    public ResponseEntity<ResponseResult> saveHppAdvertisement(@RequestBody HppAdvertisementDTO hppAdvertisementDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppAdvertisementService.checkHppAdvertisementDTO(hppAdvertisementDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save HppAdvertisement : {}", hppAdvertisementDTO);
        HppAdvertisementDTO hppAdvertisementDTO1 = hppAdvertisementService.save(hppAdvertisementDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppAdvertisementDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_advertisement/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppAdvertisement");
        Page<HppAdvertisementDTO> page = hppAdvertisementService.pageList(params);
       // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_advertisement");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    @GetMapping("/hpp_advertisement/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id){
        log.debug("REST request to get one of HppAdvertisement");
        HppAdvertisementDTO hppAdvertisementDTO = hppAdvertisementService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppAdvertisementDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_advertisement/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppAdvertisement : {}", id);
        ResponseResult json = new ResponseResult();
        hppAdvertisementService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * Post  .
     *加载首页广告(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_advertisement/loadByHome")
    @Timed
    public ResponseEntity<ResponseResult> loadExchange() {
        List<HppAdvertisementDTO> list = hppAdvertisementService.loadByHome();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *加载视频页广告(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_advertisement/loadByVideo")
    @Timed
    public ResponseEntity<ResponseResult> loadByVideo() {
        List<HppAdvertisementDTO> list = hppAdvertisementService.loadByVideo();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *加载匯商页广告(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_advertisement/loadBySinks")
    @Timed
    public ResponseEntity<ResponseResult> loadBySinks() {
        List<HppAdvertisementDTO> list = hppAdvertisementService.loadBySinks();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *加载策略入驻页广告(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_advertisement/loadByStrategy")
    @Timed
    public ResponseEntity<ResponseResult> loadByStrategy() {
        List<HppAdvertisementDTO> list = hppAdvertisementService.loadByStrategy();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *加载直播页广告(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_advertisement/loadByLive")
    @Timed
    public ResponseEntity<ResponseResult> loadByLive() {
        List<HppAdvertisementDTO> list = hppAdvertisementService.loadByLive();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
