package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.FundSignalService;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.dto.view.FindListFundSignalDTO;
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
public class FundSignalResource {

    private final Logger log = LoggerFactory.getLogger(FundSignalResource.class);

    private static final String ENTITY_NAME = "fundSignal";

    private final FundSignalService fundSignalService;


    public FundSignalResource(FundSignalService fundSignalService) {
        this.fundSignalService = fundSignalService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/fund_signal/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of FundSignal",params);
        Page<FundSignalDTO> page = fundSignalService.pageList(params);
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
     * @param fundSignalDTO
     * @return
     */
    @PostMapping("/fund_signal/createFundSignal")
    @Timed
    public ResponseEntity<ResponseResult> updateFundSignal(@RequestBody FundSignalDTO fundSignalDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a FundSignal : {}", fundSignalDTO);
        HashMap<String, Object> checkMap = fundSignalService.checkCreateFundSignalDTO(fundSignalDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        FundSignalDTO fundSignalDTO1 = fundSignalService.createFundSignalDTO(fundSignalDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(fundSignalDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**for app
     * @param params
     * @return
     */
    @PostMapping("/fund_signal/findList")
    @Timed
    public ResponseEntity<ResponseResult> findList(@RequestBody HashMap<String, Object> params){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to findList of FundSignal : {}", params);
        List<FindListFundSignalDTO> list = fundSignalService.findList(params);
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**for app 我的合伙配置
     * @param params
     * @return
     */
    @PostMapping("/fund_signal/loadMyFund")
    @Timed
    public ResponseEntity<ResponseResult> loadMyFund(@RequestBody HashMap<String, Object> params){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to loadMyFund of FundSignal {}", params);
        List<FindListFundSignalDTO> list = fundSignalService.loadMyFund(params);
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(result);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**for app
     * @param id
     * @return
     */
    @GetMapping("/fund_signal/findOneDetail/{id}")
    @Timed
    public ResponseEntity<ResponseResult> findList(@PathVariable String id){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to findOneDetail of FundSignal : {}", id);
        FundSignalDTO fundSignalDTO = fundSignalService.findOneDetail(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(fundSignalDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/fund_signal/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get one  FundSignal : {}", id);
        FundSignalDTO FundSignalDTO = fundSignalService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(FundSignalDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**设为操作中
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/fund_signal/operation/{id}")
    @Timed
    public ResponseEntity<ResponseResult> operation(@PathVariable String id) {
        log.debug("REST request to get one  FundSignal : {}", id);
        fundSignalService.operation(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/fund_signal/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one FundSignal : {}", id);
        fundSignalService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/fund_signal/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  FundSignal ");
        List<FundSignalDTO> list = fundSignalService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
