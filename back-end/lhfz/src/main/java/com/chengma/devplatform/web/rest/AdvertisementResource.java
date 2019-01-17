package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.AdvertisementService;
import com.chengma.devplatform.service.dto.AdvertisementDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingAdvertisement.
 */
@RestController
@RequestMapping("/api")
public class AdvertisementResource {

    private final Logger log = LoggerFactory.getLogger(AdvertisementResource.class);

    private static final String ENTITY_NAME = "advertisement";

    private final AdvertisementService advertisementService;

    public AdvertisementResource(AdvertisementService advertisementService) {
        this.advertisementService =advertisementService;
    }


    /**
     * 保存培训信息
     * @param advertisementDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/advertisement/saveAdvertisement")
    @Timed
    public ResponseEntity<ResponseResult> saveAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = advertisementService.checkAdvertisementDTO(advertisementDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save Advertisement : {}", advertisementDTO);
        AdvertisementDTO advertisementDTO1 = advertisementService.save(advertisementDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(advertisementDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/advertisement/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of Advertisement");
        Page<AdvertisementDTO> page = advertisementService.pageList(params);
       // HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/advertisement");
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
    @GetMapping("/advertisement/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id){
        log.debug("REST request to get one of Advertisement");
        AdvertisementDTO advertisementDTO = advertisementService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(advertisementDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/advertisement/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete Advertisement : {}", id);
        ResponseResult json = new ResponseResult();
        advertisementService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * Post  .
     *加载首页广告(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/advertisement/loadByType/{type}")
    @Timed
    public ResponseEntity<ResponseResult> loadExchange(@PathVariable String type) {
        List<AdvertisementDTO> list = advertisementService.loadByType(type);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
