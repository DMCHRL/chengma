package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppIntegral;
import com.chengma.devplatform.domain.HppIntegralDetail;
import com.chengma.devplatform.service.HppIntegralService;
import com.chengma.devplatform.service.dto.HppIntegralDTO;
import com.chengma.devplatform.service.dto.HppIntegralDetailDTO;
import com.chengma.devplatform.service.dto.HppIntegralViewDTO;
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
 * REST controller for managingHppIntegral.
 */
@RestController
@RequestMapping("/api")
public class HppIntegralResource {

    private final Logger log = LoggerFactory.getLogger(HppIntegralResource.class);

    private static final String ENTITY_NAME = "hppIntegral";

    private final HppIntegralService hppIntegralService;

    public HppIntegralResource(HppIntegralService hppIntegralService) {
        this.hppIntegralService =hppIntegralService;
    }


    /**
     * 获取积分实体(app)
     * @param mobileNum
     * @return
     */
    @GetMapping("/hpp_integral/get/{mobileNum}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String mobileNum) {
        log.debug("REST request to get a HppIntegral : {}", mobileNum);
        ResponseResult json = new ResponseResult();
        HppIntegralDTO hppIntegralDTO=hppIntegralService.findByMobileNum(mobileNum);
        json.setData(hppIntegralDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 分享到朋友圈(app)
     * @param mobileNum
     * @return
     */
    @GetMapping("/hpp_integral/shareToCommunity/{mobileNum}")
    @Timed
    public ResponseEntity<ResponseResult> shareToCommunity(@PathVariable String mobileNum) {
        log.debug("REST request to shareToCommunity  : {}", mobileNum);
        ResponseResult json = new ResponseResult();
       hppIntegralService.shareToCommunity(mobileNum);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 分享到好友(app)
     * @param mobileNum
     * @return
     */
    @GetMapping("/hpp_integral/shareToFriend/{mobileNum}")
    @Timed
    public ResponseEntity<ResponseResult> shareToFriend(@PathVariable String mobileNum) {
        log.debug("REST request to shareToFriend : {}", mobileNum);
        ResponseResult json = new ResponseResult();
        hppIntegralService.shareToFriend(mobileNum);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 登录奖励(app)
     * @param mobileNum
     * @return
     */
    @GetMapping("/hpp_integral/login/{mobileNum}")
    @Timed
    public ResponseEntity<ResponseResult> login(@PathVariable String mobileNum) {
        log.debug("REST request to shareToFriend : {}", mobileNum);
        ResponseResult json = new ResponseResult();
        hppIntegralService.login(mobileNum);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 加载积分兑换列表(app)
     * @return
     */
    @GetMapping("/hpp_integral/findList")
    @Timed
    public ResponseEntity<ResponseResult> findList() {
        log.debug("REST request to findList");
        ResponseResult json = new ResponseResult();
        List<HppIntegralViewDTO> list = hppIntegralService.findList();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 设置积分兑换列表(app)
     * @return
     */
    @PostMapping("/hpp_integral/setIntegral")
    @Timed
    public ResponseEntity<ResponseResult> setIntegral(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to setIntegral ");
        ResponseResult json = new ResponseResult();
        hppIntegralService.setIntegral(params);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 加载积分兑换明细(app)
     * @return
     */
    @GetMapping("/hpp_integral/findDetailByMobile/{mobile}")
    @Timed
    public ResponseEntity<ResponseResult> findDetailByMobile(@PathVariable String mobile) {
        log.debug("REST request to findDetailByMobile");
        ResponseResult json = new ResponseResult();
        List<HppIntegralDetailDTO> list = hppIntegralService.findDetailByMobile(mobile);
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
