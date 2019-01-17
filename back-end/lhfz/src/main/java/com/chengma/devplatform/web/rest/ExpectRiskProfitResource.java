package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.ExpectRiskProfitService;
import com.chengma.devplatform.service.dto.ExpectRiskProfitDTO;
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
public class ExpectRiskProfitResource {

    private final Logger log = LoggerFactory.getLogger(ExpectRiskProfitResource.class);

    private static final String ENTITY_NAME = "expectRiskProfit";

    private final ExpectRiskProfitService expectRiskProfitService;


    public ExpectRiskProfitResource(ExpectRiskProfitService expectRiskProfitService) {
        this.expectRiskProfitService = expectRiskProfitService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/expect_risk_profit/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of ExpectRiskProfit",params);
        Page<ExpectRiskProfitDTO> page = expectRiskProfitService.pageList(params);
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
     * @param expectRiskProfitDTO
     * @return
     */
    @PostMapping("/expect_risk_profit/createExpectRiskProfit")
    @Timed
    public ResponseEntity<ResponseResult> updateExpectRiskProfit(@RequestBody ExpectRiskProfitDTO expectRiskProfitDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a ExpectRiskProfit : {}", expectRiskProfitDTO);
        HashMap<String, Object> checkMap = expectRiskProfitService.checkCreateExpectRiskProfitDTO(expectRiskProfitDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        ExpectRiskProfitDTO expectRiskProfitDTO1 = expectRiskProfitService.createExpectRiskProfitDTO(expectRiskProfitDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(expectRiskProfitDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/expect_risk_profit/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get one  ExpectRiskProfit : {}", id);
        ExpectRiskProfitDTO ExpectRiskProfitDTO = expectRiskProfitService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(ExpectRiskProfitDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/expect_risk_profit/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one ExpectRiskProfit : {}", id);
        expectRiskProfitService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/expect_risk_profit/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  ExpectRiskProfit ");
        List<ExpectRiskProfitDTO> list = expectRiskProfitService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}

