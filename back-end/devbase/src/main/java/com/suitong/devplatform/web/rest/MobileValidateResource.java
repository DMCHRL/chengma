package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.MobileValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * REST controller for managing MobileValidate.
 */
@RestController
@RequestMapping("/api")
public class MobileValidateResource {

    private final Logger log = LoggerFactory.getLogger(MobileValidateResource.class);

    private static final String ENTITY_NAME = "mobileValidate";

    private final MobileValidateService mobileValidateService;

    public MobileValidateResource(MobileValidateService mobileValidateService) {
        this.mobileValidateService = mobileValidateService;
    }

    /**
     * sendCode
     */
    @PostMapping("/mobileValidate/sendCode")
    @Timed
    public ResponseEntity<ResponseResult> sendCode(@RequestBody HashMap<String,Object> params) {
        ResponseResult response = mobileValidateService.sendCode(params);
        return new ResponseEntity<>(response,null,HttpStatus.OK);
    }

    /**
     * verification
     */
    @PostMapping("/mobileValidate/verification")
    @Timed
    public ResponseEntity<ResponseResult> verification(@RequestBody HashMap<String,Object> params) {
        ResponseResult response = mobileValidateService.verification(params);
        return new ResponseEntity<>(response,null,HttpStatus.OK);
    }

    /**
     * verification
     */
    @PostMapping("/mobileValidate/notice")
    @Timed
    public ResponseEntity<HashMap<String,Object>> notice(@RequestBody HashMap<String,Object> params) {
        HashMap<String, Object> response = mobileValidateService.notice(params);
        return new ResponseEntity<>(response,null,HttpStatus.OK);
    }
}
