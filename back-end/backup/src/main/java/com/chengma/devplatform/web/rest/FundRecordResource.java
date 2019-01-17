package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.FundRecordService;
import com.chengma.devplatform.service.FundSignalService;
import com.chengma.devplatform.service.dto.FundRecordDTO;
import com.chengma.devplatform.service.dto.FundSignalDTO;
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
public class FundRecordResource {

    private final Logger log = LoggerFactory.getLogger(FundRecordResource.class);

    private static final String ENTITY_NAME = "fundRecord";

    private final FundRecordService fundRecordService;


    public FundRecordResource(FundRecordService fundRecordService) {
        this.fundRecordService = fundRecordService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/fund_record/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of FundRecord",params);
        Page<FundRecordDTO> page = fundRecordService.pageList(params);
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
     * @param fundRecordDTO
     * @return
     */
    @PostMapping("/fund_record/createFundRecord")
    @Timed
    public ResponseEntity<ResponseResult> updateFundRecord(@RequestBody FundRecordDTO fundRecordDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a FundRecord : {}", fundRecordDTO);
        HashMap<String, Object> checkMap = fundRecordService.checkCreateFundRecordDTO(fundRecordDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        FundRecordDTO fundRecordDTO1 = fundRecordService.createFundRecordDTO(fundRecordDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(fundRecordDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/fund_record/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get one  FundRecord : {}", id);
        FundRecordDTO FundRecordDTO = fundRecordService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(FundRecordDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/fund_record/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one FundRecord : {}", id);
        fundRecordService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/fund_record/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  FundRecord ");
        List<FundRecordDTO> list = fundRecordService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * for app 读取信号入伙记录
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/fund_record/findByFundSignalId/{fundSignalId}")
    @Timed
    public ResponseEntity<ResponseResult> findAll(@PathVariable String fundSignalId) {
        log.debug("REST request to findByFundSignalId  FundRecord {}",fundSignalId);
        HashMap response = fundRecordService.findListByFundSignalId(fundSignalId);
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",response.get("list"));
        result.put("total",response.get("total"));
        result.put("num",response.get("num"));
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}

