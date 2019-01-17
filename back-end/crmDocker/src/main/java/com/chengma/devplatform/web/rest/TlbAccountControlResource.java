package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbAccountControlService;
import com.chengma.devplatform.service.dto.TlbAccountControlDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing SysComponent.
 */
@RestController
@RequestMapping("/api")
public class TlbAccountControlResource {

    private final Logger log = LoggerFactory.getLogger(TlbAccountControlResource.class);

    private static final String ENTITY_NAME = "tlbAccountControl";

    private final TlbAccountControlService tlbAccountControlService;

    public TlbAccountControlResource(TlbAccountControlService tlbAccountControlService) {
        this.tlbAccountControlService = tlbAccountControlService;
    }

    /**
     *
     * @param tlbAccountControlDTO
     * @return
     */
    @PostMapping("/tlb-account-control/setTlbAccountControl")
    @Timed
    public ResponseEntity<ResponseResult> setTlbAccountControl(@RequestBody TlbAccountControlDTO tlbAccountControlDTO) {
        log.debug("REST request to save TlbAccountControl : {}", tlbAccountControlDTO);
        if (tlbAccountControlDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysComponent cannot already have an ID")).body(null);
        }
        ResponseResult json = new ResponseResult();
        TlbAccountControlDTO tlbAccountControlDTO1 = tlbAccountControlService.save(tlbAccountControlDTO);
        if(tlbAccountControlDTO1==null){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }else{
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }
        json.setData(tlbAccountControlDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据用户Id获取所有关联tlb账户
     * @param userId
     * @return
     */
    @GetMapping("/tlb-account-control/getList/{userId}")
    @Timed
    public ResponseEntity<ResponseResult> setTlbAccountControl(@PathVariable String userId) {
        log.debug("REST request to get TlbAccountControlList : {}", userId);
        ResponseResult json = new ResponseResult();

        List<TlbAccountControlDTO> tlbAccountControlDTOList = tlbAccountControlService.findByUserId(userId);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbAccountControlDTOList);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * \根据id删除关联tlb账号
     * @param id
     * @return
     */
    @DeleteMapping("/tlb-account-control/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteUser(@PathVariable String id) {
        tlbAccountControlService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}

