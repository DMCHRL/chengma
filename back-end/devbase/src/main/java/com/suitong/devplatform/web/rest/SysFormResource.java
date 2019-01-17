package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.constant.DevplatformConstants;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.SysFormService;
import com.suitong.devplatform.service.dto.SysFormDTO;
import com.suitong.devplatform.web.rest.util.HeaderUtil;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SysForm.
 */
@RestController
@RequestMapping("/api")
public class SysFormResource {

    private final Logger log = LoggerFactory.getLogger(SysFormResource.class);

    private static final String ENTITY_NAME = "sysForm";

    private final SysFormService sysFormService;

    public SysFormResource(SysFormService sysFormService) {
        this.sysFormService = sysFormService;
    }

    /**
     * POST  /sys-forms : Create a new sysForm.
     *
     * @param sysFormDTO the sysFormDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysFormDTO, or with status 400 (Bad Request) if the sysForm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys-form/createSysForm")
    @Timed
    public ResponseEntity<ResponseResult> createSysForm(@RequestBody SysFormDTO sysFormDTO) throws URISyntaxException {
        log.debug("REST request to save SysForm : {}", sysFormDTO);
        if (sysFormDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysForm cannot already have an ID")).body(null);
        }
        sysFormDTO.setAccessEdit("1");
        HashMap<String,Object> response = sysFormService.save(sysFormDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * PUT  /sys-forms : Updates an existing sysForm.
     *
     * @param sysFormDTO the sysFormDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysFormDTO,
     * or with status 400 (Bad Request) if the sysFormDTO is not valid,
     * or with status 500 (Internal Server Error) if the sysFormDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys-form/updateSysForm")
    @Timed
    public ResponseEntity<ResponseResult> updateSysForm(@RequestBody SysFormDTO sysFormDTO) throws URISyntaxException {
        log.debug("REST request to update SysForm : {}", sysFormDTO);
        if (sysFormDTO.getId() == null) {
            return createSysForm(sysFormDTO);
        }
        HashMap<String,Object> response = sysFormService.save(sysFormDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据选中的菜单，获取他下面的表单
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysMenus in body
     */
    @PostMapping("/sys-form/getSysForms")
    @Timed
    public ResponseEntity<ResponseResult> getSysForms(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get a page of SysMenus");
        ResponseResult json = new ResponseResult();
        try {
            Long menuId = params.get("menuId") == null ? null : Long.valueOf(params.get("menuId").toString());
            List<SysFormDTO> formDTOList= sysFormService.queryFormPage(menuId.toString(),"");
            json.setData(formDTOList);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * GET  /sys-forms : get all the sysForms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysForms in body
     */
    @GetMapping("/sys-form/getAllSysForms")
    @Timed
    public ResponseEntity<ResponseResult> getAllSysForms(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SysForms");
        Page<SysFormDTO> page = sysFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys-forms");
        ResponseResult json = new ResponseResult();
        json.setData(page.getContent());
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * GET  /sys-forms/:id : get the "id" sysForm.
     *
     * @param id the id of the sysFormDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysFormDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sys-form/getSysForm/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getSysForm(@PathVariable Long id) {
        log.debug("REST request to get SysForm : {}", id);
        ResponseResult json = new ResponseResult();
        try {
            SysFormDTO sysFormDTO = sysFormService.findOne(id);
            json.setData(sysFormDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * DELETE  /sys-forms/:id : delete the "id" sysForm.
     *
     * @param ids the id of the sysFormDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/sys-form/deleteSysForm")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysForm(@RequestBody Long[] ids) {
        log.debug("REST request to delete SysForm : {}");
        ResponseResult json = new ResponseResult();
        try {
            for(Long id : ids){
                sysFormService.deleteSysRoleForm(id);
                sysFormService.delete(id);
            }
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/sys-form/checkFormEnglishName")
    @Timed
    public ResponseEntity<ResponseResult> checkFormEnglishName(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to checkFormEnglishName");
        ResponseResult json = new ResponseResult();
        try {
            int count = sysFormService.checkFormEnglishName(params);
            json.setData(count);
            if(count > 0){
                json.setMsgCode(DevplatformConstants.REPEAT_FORMENGLISHNAME);
            }
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
