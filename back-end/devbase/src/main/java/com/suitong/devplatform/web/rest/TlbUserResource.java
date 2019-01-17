package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.FileManageService;
import com.suitong.devplatform.service.FileStreamManageService;
import com.suitong.devplatform.service.SysComponentService;
import com.suitong.devplatform.service.TlbUserService;
import com.suitong.devplatform.service.dto.SysComponentDTO;
import com.suitong.devplatform.service.dto.TlbUserDTO;
import com.suitong.devplatform.web.rest.util.HeaderUtil;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing SysComponent.
 */
@RestController
@RequestMapping("/api")
public class TlbUserResource {

    private final Logger log = LoggerFactory.getLogger(TlbUserResource.class);

    private static final String ENTITY_NAME = "tlbUser";

    private final TlbUserService tlbUserService;

    public TlbUserResource(TlbUserService tlbUserService) {
        this.tlbUserService = tlbUserService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbUserDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-user/createTlbUser")
    @Timed
    public ResponseEntity<ResponseResult> createSysComponent(@RequestBody TlbUserDTO tlbUserDTO) throws URISyntaxException {
        log.debug("REST request to save SysComponent : {}", tlbUserDTO);
        if (tlbUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysComponent cannot already have an ID")).body(null);
        }
        HashMap<String,Object> response = tlbUserService.save(tlbUserDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/tlb-user/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SysComponents");
        Page<TlbUserDTO> page = tlbUserService.pageList(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbl-user");
        ResponseResult json = new ResponseResult();
        json.setData(page.getContent());
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-user/getAll")
    @Timed
    public ResponseEntity<ResponseResult> getAll(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysComponents");
        List<TlbUserDTO> tlbUserDTOList = tlbUserService.findAll(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbUserDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * GET  /sys-components/:id : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tlb-user/getTlbUser/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getSysComponent(@PathVariable Long id) {
        log.debug("REST request to get SysComponent : {}", id);
        ResponseResult json = new ResponseResult();
        try {
            TlbUserDTO sysComponentDTO = tlbUserService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @DeleteMapping("/tlb-user/deleteTlbUser/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysUser(@PathVariable Long id) {
        log.debug("REST request to delete SysUser : {}", id);
        tlbUserService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
