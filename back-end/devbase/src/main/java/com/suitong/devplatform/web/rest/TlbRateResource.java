package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.TlbRateService;
import com.suitong.devplatform.service.dto.TlbRateDTO;
import com.suitong.devplatform.web.rest.util.HeaderUtil;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
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
 * REST controller for managing SysComponent.
 */
//@Api(value = "UserController", description = "汇率相关api")
@RestController
@RequestMapping("/api")
public class TlbRateResource {

    private final Logger log = LoggerFactory.getLogger(TlbRateResource.class);

    private static final String ENTITY_NAME = "tlbRate";

    private final TlbRateService tlbRateService;

    public TlbRateResource(TlbRateService tlbRateService) {
        this.tlbRateService = tlbRateService;
    }

    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-rate/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SysComponents");
        Page<TlbRateDTO> page = tlbRateService.pageList(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbl-rate");
        ResponseResult json = new ResponseResult();
        json.setData(page.getContent());
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }


    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbRateDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    //@ApiOperation(value = "保存汇率", notes = "保存汇率", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "rate", value = "汇率", required = true, dataType = "BigDecimal", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0100, message = "参数没填好")
    })*/
    @PostMapping("/tlb-rate/createTlbRate")
    @Timed
    public ResponseEntity<ResponseResult> createSysComponent(@RequestBody TlbRateDTO tlbRateDTO) throws URISyntaxException {
        log.debug("REST request to save SysComponent : {}", tlbRateDTO);
        if (tlbRateDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysComponent cannot already have an ID")).body(null);
        }
        HashMap<String,Object> response = tlbRateService.save(tlbRateDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * Post  /修改
     *
     * @param tlbRateDTO the sysUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysUserDTO,
     * or with status 400 (Bad Request) if the sysUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the sysUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-rate/updateTlbRate")
    @Timed
    public ResponseEntity<ResponseResult> updateSysUser(@RequestBody TlbRateDTO tlbRateDTO) throws URISyntaxException {
        log.debug("REST request to update SysUser : {}", tlbRateDTO);
        if (tlbRateDTO.getId() == null) {
            return createSysComponent(tlbRateDTO);
        }
        TlbRateDTO result = tlbRateService.update(tlbRateDTO);
        ResponseResult json = new ResponseResult();
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }




    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-rate/getAll")
    @Timed
    public ResponseEntity<ResponseResult> getAll(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysComponents");
        List<TlbRateDTO> tlbRateDTOList = tlbRateService.findAll(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbRateDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * Post  /sys-components/:id : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @PostMapping("/tlb-rate/getTlbRate/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getSysComponent(@PathVariable Long id) {
        log.debug("REST request to get SysComponent : {}", id);
        ResponseResult json = new ResponseResult();
        try {
            TlbRateDTO sysComponentDTO = tlbRateService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @PostMapping("/tlb-rate/deleteTlbRate/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysUser(@PathVariable Long id) {
        log.debug("REST request to delete SysUser : {}", id);
        tlbRateService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
