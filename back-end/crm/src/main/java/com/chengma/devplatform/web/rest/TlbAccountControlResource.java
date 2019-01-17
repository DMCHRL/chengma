package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbAccountControlService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.dto.TlbAccountControlDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @Autowired
    private TlbAccountService tlbAccountService;

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
        HashMap<String, Object> checkMap=tlbAccountControlService.checkTlbAccountControlDTO(tlbAccountControlDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        TlbAccountDTO tlbAccountDTO = tlbAccountControlService.save(tlbAccountControlDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbAccountDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据用户Id获取所有关联tlb账户
     * @param userId
     * @return
     */
    @GetMapping("/tlb-account-control/loadRelationAccount/{userId}")
    @Timed
    public ResponseEntity<ResponseResult> loadRelationAccount(@PathVariable String userId) {
        log.debug("REST request to get TlbAccountControlList : {}", userId);
        ResponseResult json = new ResponseResult();

        List<TlbAccountDTO> tlbAccountDTOList = tlbAccountControlService.loadRelationAccount(userId);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbAccountDTOList);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * \根据id删除关联tlb账号
     * @param id
     * @return
     */
    @GetMapping("/tlb-account-control/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteUser(@PathVariable String id) {
        tlbAccountControlService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}

