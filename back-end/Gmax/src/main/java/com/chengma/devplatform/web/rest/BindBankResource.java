package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.BindBankService;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing BindBank.
 */
@RestController
@RequestMapping("/api")
public class BindBankResource {

    private final Logger log = LoggerFactory.getLogger(BindBankResource.class);

    private static final String ENTITY_NAME = "bindBank";

    private final BindBankService bindBankService;

    public BindBankResource(BindBankService bindBankService) {
        this.bindBankService = bindBankService;
    }



    @GetMapping("/bind-bank/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> approvedBindBank(@PathVariable String id) {
        log.debug("REST request to delete BindBank : {}", id);
        BindBankDTO bindBankDTO = bindBankService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(bindBankDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }




}
