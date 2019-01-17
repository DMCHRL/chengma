package com.chengma.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.SysComponentService;
import com.chengma.devplatform.service.dto.SysComponentDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing SysComponent.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class SysComponentResource {

    private final Logger log = LoggerFactory.getLogger(SysComponentResource.class);

    private static final String ENTITY_NAME = "sysComponent";

    private final SysComponentService sysComponentService;

    public SysComponentResource(SysComponentService sysComponentService) {
        this.sysComponentService = sysComponentService;
    }

    /**
     * POST  /sys-component/createSysComponent : Create a new sysComponent.
     *
     * @param sysComponentDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys-component/createSysComponent")
    @Timed
    public ResponseEntity<ResponseResult> createSysComponent(@RequestBody SysComponentDTO sysComponentDTO) throws URISyntaxException {
        log.debug("REST request to save SysComponent : {}", sysComponentDTO);
        if (sysComponentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysComponent cannot already have an ID")).body(null);
        }
        sysComponentDTO.setAccessEdit("1");
        HashMap<String, Object> response = sysComponentService.save(sysComponentDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * PUT  /sys-component/updateSysComponent : Updates an existing sysComponent.
     *
     * @param sysComponentDTO the sysComponentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysComponentDTO,
     * or with status 400 (Bad Request) if the sysComponentDTO is not valid,
     * or with status 500 (Internal Server Error) if the sysComponentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys-component/updateSysComponent")
    @Timed
    public ResponseEntity<ResponseResult> updateSysComponent(@RequestBody SysComponentDTO sysComponentDTO) throws URISyntaxException {
        log.debug("REST request to update SysComponent : {}", sysComponentDTO);
        if (sysComponentDTO.getId() == null) {
            return createSysComponent(sysComponentDTO);
        }
        HashMap<String, Object> response = sysComponentService.save(sysComponentDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * GET  /sys-component/getSysComponent/{id} : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sys-component/getSysComponent/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getSysComponent(@PathVariable String id) {
        log.debug("REST request to get SysComponent : {}", id);
        ResponseResult json = new ResponseResult();
        try {
            SysComponentDTO sysComponentDTO = sysComponentService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        } catch (Exception e) {
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-component/getSysComponents ：根据选中的表单，获取他下面的控件按钮。
     *
     * @param params 参数：{formId:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-component/getSysComponents")
    @Timed
    public ResponseEntity<ResponseResult> getSysComponents(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysMenus");
        ResponseResult json = new ResponseResult();
        try {
            String formId = params.get("formId") == null ? "" : params.get("formId").toString();
            List<SysComponentDTO> componentDTOList = sysComponentService.queryComponentPage(formId, "");
            json.setData(componentDTOList);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        } catch (Exception e) {
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post  /sys-component/deleteSysComponent : delete the "id" sysComponent.
     *
     * @param ids the id of the sysComponentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/sys-component/deleteSysComponent")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysComponent(@RequestBody String[] ids) {
        log.debug("REST request to delete SysComponent : {}");
        ResponseResult json = new ResponseResult();
        try {
            for (String id : ids) {
                sysComponentService.deleteSysRoleComponent(id);
                sysComponentService.delete(id);
            }
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        } catch (Exception e) {
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
