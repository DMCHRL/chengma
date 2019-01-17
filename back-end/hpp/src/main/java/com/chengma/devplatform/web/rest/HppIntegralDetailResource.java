package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppIntegralDetail;
import com.chengma.devplatform.service.HppIntegralDetailService;
import com.chengma.devplatform.service.dto.HppIntegralDetailDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingHppIntegralDetail.
 */
@RestController
@RequestMapping("/api")
public class HppIntegralDetailResource {

    private final Logger log = LoggerFactory.getLogger(HppIntegralDetailResource.class);

    private static final String ENTITY_NAME = "hppIntegralDetail";

    private final HppIntegralDetailService hppIntegralDetailService;

    public HppIntegralDetailResource(HppIntegralDetailService hppIntegralDetailService) {
        this.hppIntegralDetailService =hppIntegralDetailService;
    }



    /**
     * 加载积分明细(app)
     * @return
     */
    @PostMapping("/hpp_integral_detail/pageList")
    @Timed
    public ResponseEntity<ResponseResult> setIntegral(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get pageList of HppIntegralDetailDTO",params);
        ResponseResult json = new ResponseResult();
        Page<HppIntegralDetailDTO> page = hppIntegralDetailService.pageList(params);
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    

}
