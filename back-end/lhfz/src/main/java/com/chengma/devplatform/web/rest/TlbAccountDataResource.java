package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbAccountDataService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbAccountDataDTO;
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
public class TlbAccountDataResource {

    private final Logger log = LoggerFactory.getLogger(TlbAccountDataResource.class);

    private static final String ENTITY_NAME = "tlbAccountData";

    private final TlbAccountDataService tlbAccountDataService;


    public TlbAccountDataResource(TlbAccountDataService tlbAccountDataService) {
        this.tlbAccountDataService = tlbAccountDataService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb_account_data/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbAccountData",params);
        Page<TlbAccountDataDTO> page = tlbAccountDataService.pageList(params);
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
     * @param tlbAccountDataDTO
     * @return
     */
    @PostMapping("/tlb_account_data/createTlbAccountData")
    @Timed
    public ResponseEntity<ResponseResult> updateTlbAccountData(@RequestBody TlbAccountDataDTO tlbAccountDataDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a TlbAccountData : {}", tlbAccountDataDTO);
        HashMap<String, Object> checkMap = tlbAccountDataService.checkCreateTlbAccountDataDTO(tlbAccountDataDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        TlbAccountDataDTO tlbAccountDataDTO1 = tlbAccountDataService.createTlbAccountDataDTO(tlbAccountDataDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbAccountDataDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/tlb_account_data/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get one  TlbAccountData : {}", id);
        TlbAccountDataDTO TlbAccountDataDTO = tlbAccountDataService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(TlbAccountDataDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/tlb_account_data/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one TlbAccountData : {}", id);
        tlbAccountDataService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/tlb_account_data/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  TlbAccountData ");
        List<TlbAccountDataDTO> list = tlbAccountDataService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
