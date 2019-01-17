package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.service.HppVideoService;
import com.chengma.devplatform.service.dto.HppVideoDTO;
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
 * REST controller for managingHppVideo.
 */
@RestController
@RequestMapping("/api")
public class HppVideoResource {

    private final Logger log = LoggerFactory.getLogger(HppVideoResource.class);

    private static final String ENTITY_NAME = "hppVideo";

    private final HppVideoService hppVideoService;

    public HppVideoResource(HppVideoService hppVideoService) {
        this.hppVideoService =hppVideoService;
    }


    /**
     * 保存视频信息
     * @param hppVideoDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_video/saveHppVideo")
    @Timed
    public ResponseEntity<ResponseResult> saveHppVideo(@RequestBody HppVideoDTO hppVideoDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        if(StringUtils.isBlank(hppVideoDTO.getId())){
            HashMap<String, Object> checkMap = hppVideoService.checkHppVideo(hppVideoDTO);
            if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
                json.setMsgCode(checkMap.get("msg").toString());
                return new ResponseEntity<>(json, null, HttpStatus.OK);
            }
        }
        log.debug("REST request to save HppVideo : {}", hppVideoDTO);
        HppVideoDTO hppVideoDTO1 = hppVideoService.createHppVideoDTO(hppVideoDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppVideoDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_video/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppVideo");
        Page<HppVideoDTO> page = hppVideoService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_video");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    @GetMapping("/hpp_video/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppVideo : {}", id);
        ResponseResult json = new ResponseResult();
        hppVideoService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_video/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get a HppVideo : {}", id);
        ResponseResult json = new ResponseResult();
        HppVideoDTO hppVideoDTO= hppVideoService.findOne(id);
        json.setData(hppVideoDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_video/showOnApp/{id}")
    @Timed
    public ResponseEntity<ResponseResult> showOnApp(@PathVariable String id) {
        log.debug("REST request to get a HppVideo : {}", id);
        ResponseResult json = new ResponseResult();
        HppVideoDTO hppVideoDTO= hppVideoService.showOnApp(id);
        json.setData(hppVideoDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *加载积分列表(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_video/loadIntegral")
    @Timed
    public ResponseEntity<ResponseResult> loadIntegral() {
        List<HppVideoDTO> list = hppVideoService.loadIntegral();
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
     *加载兑换明细列表(app)
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_video/loadExchange/{mobileNum}")
    @Timed
    public ResponseEntity<ResponseResult> loadExchange(@PathVariable String mobileNum) {
        List<HppVideoDTO> list = hppVideoService.loadExchange(mobileNum);
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
    @GetMapping("/hpp_video/findByTypeId/{typeId}")
    @Timed
    public ResponseEntity<ResponseResult> findByTypeId(@PathVariable String typeId) {
        List<HppVideoDTO> list = hppVideoService.findByTypeId(typeId);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
