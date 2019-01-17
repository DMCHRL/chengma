package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.SysUserService;
import com.suitong.devplatform.service.dto.SysUserDTO;
import com.suitong.devplatform.web.rest.util.HeaderUtil;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing SysUser.
 */
@RestController
@RequestMapping("/api")
public class SysUserResource {

    private final Logger log = LoggerFactory.getLogger(SysUserResource.class);

    private static final String ENTITY_NAME = "sysUser";

    private final SysUserService sysUserService;

    public SysUserResource(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * POST  新增
     *
     * @param sysUserDTO the sysUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysUserDTO, or with status 400 (Bad Request) if the sysUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys-user/createSysUser")
    @Timed
    public ResponseEntity<ResponseResult> createSysUser(@RequestBody SysUserDTO sysUserDTO) throws URISyntaxException {
        log.debug("REST request to save SysUser : {}", sysUserDTO);
        if (sysUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysUser cannot already have an ID")).body(null);
        }
        SysUserDTO result = sysUserService.save(sysUserDTO);
        ResponseResult json = new ResponseResult();
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * PUT  /修改
     *
     * @param sysUserDTO the sysUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysUserDTO,
     * or with status 400 (Bad Request) if the sysUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the sysUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys-user/updateSysUser")
    @Timed
    public ResponseEntity<ResponseResult> updateSysUser(@RequestBody SysUserDTO sysUserDTO) throws URISyntaxException {
        log.debug("REST request to update SysUser : {}", sysUserDTO);
        if (sysUserDTO.getId() == null) {
            return createSysUser(sysUserDTO);
        }
        SysUserDTO result = sysUserService.save(sysUserDTO);
        ResponseResult json = new ResponseResult();
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取所有的账户
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysUsers in body
     */
    @PostMapping("/sys-user/getAllSysUsers")
    @Timed
    public ResponseEntity<ResponseResult> getAllSysUsers(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get a page of SysUsers");
        Page<SysUserDTO> page = sysUserService.findAll(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys-user/getAllSysUsers");
        ResponseResult json = new ResponseResult();
        json.setData(page.getContent());
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * GET  获取某个账户
     *
     * @return the ResponseEntity with status 200 (OK) and with body the sysUserDTO, or with status 404 (Not Found)
     */
    @PostMapping("/sys-user/getSysUser")
    @Timed
    public ResponseEntity<ResponseResult> getSysUser(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get SysUser : {}", params);
        Long id = params.get("id") == null ? null : Long.valueOf(params.get("id").toString());
        SysUserDTO sysUserDTO = sysUserService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(sysUserDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * DELETE  /删除
     *
     * @param id the id of the sysUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sys-user/deleteSysUser/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysUser(@PathVariable Long id) {
        log.debug("REST request to delete SysUser : {}", id);
        sysUserService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     *  获取用户角色
     *
     * @return the ResponseEntity with status 200 (OK) and with body the sysUserDTO, or with status 404 (Not Found)
     */
    @PostMapping("/sys-user/getSysUserRoles")
    @Timed
    public ResponseEntity<ResponseResult> getSysUserRoles(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get SysUser : {}", params);
        Long id = params.get("id") == null ? null : Long.valueOf(params.get("id").toString());
        Map roles = sysUserService.getSysUserRoles(params);
        ResponseResult json = new ResponseResult();
        json.setData((HashMap<String,Object>)roles);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
