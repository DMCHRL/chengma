package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.AllotRuleService;
import com.chengma.devplatform.service.FundSignalService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
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
public class AllotRuleResource {

    private final Logger log = LoggerFactory.getLogger(AllotRuleResource.class);

    private static final String ENTITY_NAME = "allotRule";

    private final AllotRuleService allotRuleService;


    public AllotRuleResource(AllotRuleService allotRuleService) {
        this.allotRuleService = allotRuleService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/allot_rule/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of AllotRule",params);
        Page<AllotRuleDTO> page = allotRuleService.pageList(params);
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
     * @param allotRuleDTO
     * @return
     */
    @PostMapping("/allot_rule/createAllotRule")
    @Timed
    public ResponseEntity<ResponseResult> updateAllotRule(@RequestBody AllotRuleDTO allotRuleDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a AllotRule : {}", allotRuleDTO);
        HashMap<String, Object> checkMap = allotRuleService.checkCreateAllotRuleDTO(allotRuleDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        AllotRuleDTO allotRuleDTO1 = allotRuleService.createAllotRuleDTO(allotRuleDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(allotRuleDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/allot_rule/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get one  AllotRule : {}", id);
        AllotRuleDTO AllotRuleDTO = allotRuleService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(AllotRuleDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/allot_rule/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one AllotRule : {}", id);
        allotRuleService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/allot_rule/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  AllotRule ");
        List<AllotRuleDTO> list = allotRuleService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}

