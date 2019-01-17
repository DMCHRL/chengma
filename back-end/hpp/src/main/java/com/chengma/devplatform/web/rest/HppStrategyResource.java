package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppStrategyService;
import com.chengma.devplatform.service.dto.HppStrategyDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingHppStrategy.
 */
@RestController
@RequestMapping("/api")
public class HppStrategyResource {

    private final Logger log = LoggerFactory.getLogger(HppStrategyResource.class);

    private static final String ENTITY_NAME = "hppStrategy";

    private final HppStrategyService hppStrategyService;

    public HppStrategyResource(HppStrategyService hppStrategyService) {
        this.hppStrategyService =hppStrategyService;
    }


    /**
     * 获取个人跟单策略(app)
     * @param mobileNum
     * @return
     */
    @GetMapping("/hpp_strategy/findByMobile/{mobileNum}")
    @Timed
    public ResponseEntity<ResponseResult> findByMobile(@PathVariable String mobileNum) {
        log.debug("REST request to get a hppStrategyDTO : {}", mobileNum);
        ResponseResult json = new ResponseResult();
        HppStrategyDTO hppStrategyDTO= hppStrategyService.findByMobileNum(mobileNum);
        json.setData(hppStrategyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取个人跟单策略(app)
     * @param id
     * @return
     */
    @GetMapping("/hpp_strategy/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get a hppStrategyDTO : {}", id);
        ResponseResult json = new ResponseResult();
        HppStrategyDTO hppStrategyDTO= hppStrategyService.findOne(id);
        json.setData(hppStrategyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取策略列表(app)  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_strategy/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to get a list of HppStrategyDTO");
        List<HppStrategyDTO> list = hppStrategyService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取策略列表(后台)  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_strategy/findPage")
    @Timed
    public ResponseEntity<ResponseResult> findPage(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to findPage of HppStrategyDTO");
        Page<HppStrategyDTO> page = hppStrategyService.findPage(params);
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
     * 策略申请
     * @param hppStrategyDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_strategy/apply")
    @Timed
    public ResponseEntity<ResponseResult> saveHppStrategy(@RequestBody HppStrategyDTO hppStrategyDTO) {
        ResponseResult json = new ResponseResult();
        if(StringUtils.isBlank(hppStrategyDTO.getId())){
            HashMap<String, Object> checkMap=hppStrategyService.checkHppStrategyDTO(hppStrategyDTO);
            if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
                json.setMsgCode(checkMap.get("msg").toString());
                return new ResponseEntity<>(json, null, HttpStatus.OK);
            }
        }
        log.debug("REST request to save HppStrategy : {}", hppStrategyDTO);
        HppStrategyDTO hppStrategyDTO1 = hppStrategyService.save(hppStrategyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppStrategyDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 修改策略
     * @param hppStrategyDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_strategy/edit")
    @Timed
    public ResponseEntity<ResponseResult> editHppStrategy(@RequestBody HppStrategyDTO hppStrategyDTO) {
        ResponseResult json = new ResponseResult();
        log.debug("REST request to edit HppStrategy : {}", hppStrategyDTO);
        HppStrategyDTO hppStrategyDTO1 = hppStrategyService.edit(hppStrategyDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppStrategyDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 处理申请
     * @param params
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_strategy/approve")
    @Timed
    public ResponseEntity<ResponseResult> approve(@RequestBody HashMap<String,Object> params) {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppStrategyService.checkApprove(params);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to approve HppStrategyOrder : {}");
        HppStrategyDTO hppStrategyDTO1 = hppStrategyService.approve(params);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppStrategyDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * 删除至审核
     * @param id
     * @return
     */
    @GetMapping("/hpp_strategy/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppStrategy : {}", id);
        ResponseResult json = new ResponseResult();
        Long count=hppStrategyService.findOrderCountById(id);
        if(count>0){
            json.setStatusCode(ResponseResult.FAIL_CODE);
            json.setMsgCode("该策略下有"+count+"条记录,不能删除。");
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }else{
            hppStrategyService.delete(id);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
    }

    /**
     * 彻底删除
     * @param id
     * @return
     */
    @GetMapping("/hpp_strategy/deleteAll/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteAll(@PathVariable String id) {
        log.debug("REST request to delete HppStrategy : {}", id);
        ResponseResult json = new ResponseResult();
        hppStrategyService.deleteAll(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_strategy/downOrUpLine/{id}")
    @Timed
    public ResponseEntity<ResponseResult> downOrUpLine(@PathVariable String id) {
        log.debug("REST request to delete HppStrategy : {}", id);
        ResponseResult json = new ResponseResult();
        Long count=hppStrategyService.findOrderCountById(id);
        if(count>0){
            json.setStatusCode(ResponseResult.FAIL_CODE);
            json.setMsgCode("该策略下有"+count+"条记录,不能下线。");
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }else{
            hppStrategyService.downOrUpLine(id);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_strategy/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppStrategyDTO");
        Page<HppStrategyDTO> page = hppStrategyService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_strategy");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }


    /**
     * 信号中断
     * @param params
     * @return
     */
    @PostMapping("/hpp_strategy/outStrategy")
    @Timed
    public ResponseEntity<ResponseResult> outStrategy(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to outStrategy HppStrategy : {}", params);
        ResponseResult json = new ResponseResult();
        hppStrategyService.outStrategy(params);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
