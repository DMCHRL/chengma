package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.AllotRuleService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TlbAccountResource {

    private final Logger log = LoggerFactory.getLogger(TlbAccountResource.class);

    private static final String ENTITY_NAME = "tlbAccount";

    private final TlbAccountService tlbAccountService;
    

    public TlbAccountResource(TlbAccountService tlbAccountService) {
        this.tlbAccountService = tlbAccountService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb_account/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbAccount",params);
        Page<TlbAccountDTO> page = tlbAccountService.pageList(params);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param tlbAccountDTO
     * @return
     */
    @PostMapping("/tlb_account/createTlbAccount")
    @Timed
    public ResponseEntity<ResponseResult> updateTlbAccount(@RequestBody TlbAccountDTO tlbAccountDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a TlbAccount : {}", tlbAccountDTO);
       /* HashMap<String, Object> checkMap = tlbAccountService.checkCreateTlbAccountDTO(tlbAccountDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }*/
        TlbAccountDTO tlbAccountDTO1 = tlbAccountService.createTlbAccountDTO(tlbAccountDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbAccountDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/tlb_account/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get one  TlbAccount : {}", id);
        TlbAccountDTO TlbAccountDTO = tlbAccountService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(TlbAccountDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/tlb_account/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one TlbAccount : {}", id);
        tlbAccountService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/tlb_account/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  TlbAccount ");
        List<TlbAccountDTO> list = tlbAccountService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}

