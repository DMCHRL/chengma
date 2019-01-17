package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppVideoType;
import com.chengma.devplatform.service.HppVideoTypeService;
import com.chengma.devplatform.service.dto.HppVideoTypeDTO;
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

import javax.ws.rs.GET;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingHppVideoType.
 */
@RestController
@RequestMapping("/api")
public class HppVideoTypeResource {

    private final Logger log = LoggerFactory.getLogger(HppVideoTypeResource.class);

    private static final String ENTITY_NAME = "hppVideoType";

    private final HppVideoTypeService hppVideoTypeService;

    public HppVideoTypeResource(HppVideoTypeService hppVideoTypeService) {
        this.hppVideoTypeService =hppVideoTypeService;
    }


    /**
     * 保存视频类别信息
     * @param hppVideoTypeDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_video_type/saveHppVideoType")
    @Timed
    public ResponseEntity<ResponseResult> saveHppVideoType(@RequestBody HppVideoTypeDTO hppVideoTypeDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        if(StringUtils.isBlank(hppVideoTypeDTO.getId())){
            HashMap<String, Object> checkMap = hppVideoTypeService.checkHppVideoType(hppVideoTypeDTO);
            if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
                json.setMsgCode(checkMap.get("msg").toString());
                return new ResponseEntity<>(json, null, HttpStatus.OK);
            }
        }
        log.debug("REST request to save HppVideoType : {}", hppVideoTypeDTO);
        HppVideoTypeDTO hppVideoTypeDTO1 = hppVideoTypeService.save(hppVideoTypeDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppVideoTypeDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_video_type/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppVideoType");
        Page<HppVideoTypeDTO> page = hppVideoTypeService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_video_type");
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
     * Post  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_video_type/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to get a list of HppVideoType");
        List<HppVideoTypeDTO> list = hppVideoTypeService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_video_type/comboList")
    @Timed
    public ResponseEntity<ResponseResult> comboList() {
        log.debug("REST request to get a comboList of HppVideoType");
        List<HppVideoTypeDTO> list = hppVideoTypeService.comboList();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_video_type/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppVideoType : {}", id);
        ResponseResult json = new ResponseResult();
        Long count=hppVideoTypeService.countByTypeId(id);
        if(count>0){
            json.setStatusCode(ResponseResult.FAIL_CODE);
            json.setMsgCode("该类别下有"+count+"条记录,不能删除。");
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }else{
            hppVideoTypeService.delete(id);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
    }

    @GetMapping("/hpp_video_type/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get HppVideoType : {}", id);
        ResponseResult json = new ResponseResult();
        HppVideoTypeDTO hppVideoTypeDTO = hppVideoTypeService.findOne(id);
        json.setData(hppVideoTypeDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * get  .设置为每日推荐
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_video_type/editDayPush/{id}")
    @Timed
    public ResponseEntity<ResponseResult> editDayPush(@PathVariable String id) {
        log.debug("REST request to editDayPush one of HppVideoType");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppVideoTypeService.checkDayPush(id);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        HppVideoTypeDTO hppVideoTypeDTO = hppVideoTypeService.editDayPush(id);
        json.setData(hppVideoTypeDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * get  .取消每日推荐
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_video_type/cancelDayPush/{id}")
    @Timed
    public ResponseEntity<ResponseResult> cancelDayPush(@PathVariable String id) {
        log.debug("REST request to cancelDayPush one of HppVideoType");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppVideoTypeService.cancelDayPush(id);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * get  .获取每日推荐列表
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_video_type/findDayPush")
    @Timed
    public ResponseEntity<ResponseResult> cancelDayPush() {
        log.debug("REST request to findDayPush of HppVideoType");
        ResponseResult json = new ResponseResult();
        List<HppVideoTypeDTO> hppVideoTypesList = hppVideoTypeService.findDayPush();
        json.setData(hppVideoTypesList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
