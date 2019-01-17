package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.BankInfoService;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing BankInfo.
 */
@RestController
@RequestMapping("/api")
public class BankInfoResource {

    private final Logger log = LoggerFactory.getLogger(BankInfoResource.class);

    private static final String ENTITY_NAME = "bankInfo";

    private final BankInfoService bankInfoService;


    public BankInfoResource(BankInfoService bankInfoService) {
        this.bankInfoService = bankInfoService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    /*@PostMapping("/bank_info/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of BankInfo",params);
        Page<BankInfoDTO> page = bankInfoService.pageList(params);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/

    /**
     * @param bankInfoDTO
     * @return
     */
    @PostMapping("/bank_info/createBankInfo")
    @Timed
    public ResponseEntity<ResponseResult> updateBankInfo(@RequestBody BankInfoDTO bankInfoDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a BankInfo : {}", bankInfoDTO);
        HashMap<String, Object> checkMap = bankInfoService.checkCreateBankInfoDTO(bankInfoDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        BankInfoDTO bankInfoDTO1 = bankInfoService.createBankInfoDTO(bankInfoDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(bankInfoDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
   /* @GetMapping("/bank_info/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get one  BankInfo : {}", id);
        BankInfoDTO BankInfoDTO = bankInfoService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(BankInfoDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/

   /* *
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/bank_info/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one BankInfo : {}", id);
        bankInfoService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
   /* @GetMapping("/bank_info/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  BankInfo ");
        List<BankInfoDTO> list = bankInfoService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/bank_info/loadMyBank")
    @Timed
    public ResponseEntity<ResponseResult> findByUserId() {
        log.debug("REST request to loadMyBank of BankInfo");
        List<BankInfoDTO> list = bankInfoService.loadMyBank();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
