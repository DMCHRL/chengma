package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppNewsService;
import com.chengma.devplatform.service.dto.HppNewsDTO;
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
 * REST controller for managingHppNews.
 */
@RestController
@RequestMapping("/api")
public class HppNewsResource {

    private final Logger log = LoggerFactory.getLogger(HppNewsResource.class);

    private static final String ENTITY_NAME = "hppNews";

    private final HppNewsService hppNewsService;

    public HppNewsResource(HppNewsService hppNewsService) {
        this.hppNewsService =hppNewsService;
    }


    /**
     * 保存新闻信息
     * @param hppNewsDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_news/saveHppNews")
    @Timed
    public ResponseEntity<ResponseResult> saveHppNews(@RequestBody HppNewsDTO hppNewsDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        if(StringUtils.isBlank(hppNewsDTO.getId())){
            HashMap<String, Object> checkMap = hppNewsService.checkSave(hppNewsDTO);
            if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
                json.setMsgCode(checkMap.get("msg").toString());
                return new ResponseEntity<>(json, null, HttpStatus.OK);
            }
        }
        log.debug("REST request to save HppNews : {}", hppNewsDTO);
        HppNewsDTO hppNewsDTO1 = hppNewsService.save(hppNewsDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppNewsDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_news/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppNews");
        Page<HppNewsDTO> page = hppNewsService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_news");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    @GetMapping("/hpp_news/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppNews : {}", id);
        ResponseResult json = new ResponseResult();
        hppNewsService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_news/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get a HppNews : {}", id);
        ResponseResult json = new ResponseResult();
        HppNewsDTO hppNewsDTO= hppNewsService.findOne(id);
        json.setData(hppNewsDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_news/showOnApp/{id}")
    @Timed
    public ResponseEntity<ResponseResult> showOnApp(@PathVariable String id) {
        log.debug("REST request to get a HppNews : {}", id);
        ResponseResult json = new ResponseResult();
        HppNewsDTO hppNewsDTO= hppNewsService.showOne(id);
        json.setData(hppNewsDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_news/read/{id}")
    @Timed
    public ResponseEntity<ResponseResult> read(@PathVariable String id) {
        log.debug("REST request to get a HppNews : {}", id);
        ResponseResult json = new ResponseResult();
        HppNewsDTO hppNewsDTO= hppNewsService.readOne(id);
        json.setData(hppNewsDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_news/findList")
    @Timed
    public ResponseEntity<ResponseResult> findList() {
        log.debug("REST request to get a list of HppNews : {}");
        ResponseResult json = new ResponseResult();
        List<HppNewsDTO> list= hppNewsService.findList();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_news/sendNews/{id}")
    @Timed
    public ResponseEntity<ResponseResult> sendNews(@PathVariable String id) {
        log.debug("REST request to sendNews a HppNews : {}", id);
        ResponseResult json = new ResponseResult();
        hppNewsService.sendNews(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
