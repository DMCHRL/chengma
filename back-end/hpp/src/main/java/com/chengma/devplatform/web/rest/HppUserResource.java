package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppUserService;
import com.chengma.devplatform.service.dto.HppUserDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
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
 * REST controller for managing HppUser.
 */
@RestController
@RequestMapping("/api")
public class HppUserResource {

    private final Logger log = LoggerFactory.getLogger(HppUserResource.class);

    private static final String ENTITY_NAME = "hppUser";

    private final HppUserService hppUserService;

    public HppUserResource(HppUserService hppUserService) {
        this.hppUserService = hppUserService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param hppUserDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hpp_user/saveHppUser")
    @Timed
    public ResponseEntity<ResponseResult> createHppUser(@RequestBody HppUserDTO hppUserDTO) throws URISyntaxException {
        log.debug("REST request to save HppUser : {}", hppUserDTO);
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> resultMap=hppUserService.checkSaveUser(hppUserDTO);
        if(!resultMap.get("statusCode").equals(ResponseResult.SUCCESS_CODE)){
            json.setMsgCode(resultMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        HppUserDTO  hppUserDTO1 = hppUserService.save(hppUserDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppUserDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @PostMapping("/hpp_user/updateHppUser")
    @Timed
    public ResponseEntity<ResponseResult> updateHppUser(@RequestBody HppUserDTO hppUserDTO) throws URISyntaxException {
        log.debug("REST request to update HppUser : {}", hppUserDTO);
        if (hppUserDTO.getId() == null) {
            return createHppUser(hppUserDTO);
        }
        HppUserDTO hppUserDTO1 = hppUserService.save(hppUserDTO);;
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppUserDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_user/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppUsers");
        Page<HppUserDTO> page = hppUserService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbl-user");
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
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_user/getAll")
    @Timed
    public ResponseEntity<ResponseResult> getAll(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppUsers");
        List<HppUserDTO> hppUserDTOList = hppUserService.findAll(params);
        ResponseResult json = new ResponseResult();
        json.setData(hppUserDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * GET  /sys-components/:id : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hpp_user/getHppUser/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getHppUser(@PathVariable String id) {
        log.debug("REST request to get HppUser : {}", id);
        ResponseResult json = new ResponseResult();

        try {
            HppUserDTO sysComponentDTO = hppUserService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



    @GetMapping("/hpp_user/deleteHppUser/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteHppUser(@PathVariable String id) {
        log.debug("REST request to delete HppUser : {}", id);
        HashMap<String, Object> retMap = hppUserService.checkDeleteUser(id);
        ResponseResult json = new ResponseResult();
        if(null != retMap.get("msg") ){
            json.setMsgCode(retMap.get("msg").toString());
        }else {
            hppUserService.delete(id);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/hpp_user/approved/{id}")
    @Timed
    public ResponseEntity<ResponseResult> approvedHppUser(@PathVariable String id, @RequestBody HashMap<String, Object> params) {
        log.debug("REST request to delete HppUser : {}", id);
        HashMap retMap = hppUserService.approved(id, params);
        ResponseResult json = new ResponseResult();
        if(ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))) {
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }else{
            json.setMsgCode(retMap.get("msg").toString());
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/hpp_user/createAccount")
    @Timed
    public ResponseEntity<ResponseResult> createAccount(@RequestBody HashMap<String, Object> params) {

        String password = params.get("password") != null ? params.get("password").toString() : "";

        ResponseResult json = new ResponseResult();
        if("".equals(password)) {
            json.setMsgCode("请输入套利宝帐号密码");
        }else{
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
            json.setData(hppUserService.createAccount(password));
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据手机号查询开户
     * @param phone
     * @return
     */
    @GetMapping("/hpp_user/findByMobile/{phone}")
    @Timed
    public ResponseEntity<ResponseResult> findByMobile(@PathVariable String phone) {
        log.debug("REST request to delete HppUser : {}", phone);
        ResponseResult json = new ResponseResult();
        List<HppUserDTO> list = hppUserService.findByMobile(phone);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(list);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
