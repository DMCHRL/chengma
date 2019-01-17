package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppNavigationService;
import com.chengma.devplatform.service.dto.HppNavigationDTO;
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
 * REST controller for managingHppNavigation.
 */
@RestController
@RequestMapping("/api")
public class HppNavigationResource {

    private final Logger log = LoggerFactory.getLogger(HppNavigationResource.class);

    private static final String ENTITY_NAME = "hppNavigation";

    private final HppNavigationService hppNavigationService;

    public HppNavigationResource(HppNavigationService hppNavigationService) {
        this.hppNavigationService =hppNavigationService;
    }




    /**
     *统计点击量
     * @param flag
     * @return
     */
    @GetMapping("/hpp_navigation/countFlag/{flag}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String flag){
        log.debug("REST request to get one of HppNavigation");
        hppNavigationService.countFlag(flag);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



    /**
     * Post  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_navigation/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to get a list of HppNavigation");
        List<HppNavigationDTO> list = hppNavigationService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
