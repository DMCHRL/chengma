package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.AllotRuleService;
import com.chengma.devplatform.service.AssetDetailService;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.AssetDetailDTO;
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
public class AssetDetailResource {

    private final Logger log = LoggerFactory.getLogger(AssetDetailResource.class);

    private static final String ENTITY_NAME = "assetDetail";

    private final AssetDetailService assetDetailService;


    public AssetDetailResource(AssetDetailService assetDetailService) {
        this.assetDetailService = assetDetailService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/asset_detail/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of AssetDetail",params);
        Page<AssetDetailDTO> page = assetDetailService.pageList(params);
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
     * 申请入金或出金
     * @param assetDetailDTO
     * @return
     */
    @PostMapping("/asset_detail/createAssetDetail")
    @Timed
    public ResponseEntity<ResponseResult> updateAssetDetail(@RequestBody AssetDetailDTO assetDetailDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a AssetDetail : {}", assetDetailDTO);
        HashMap<String, Object> checkMap = assetDetailService.checkCreateAssetDetailDTO(assetDetailDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        AssetDetailDTO assetDetailDTO1 = assetDetailService.createAssetDetailDTO(assetDetailDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(assetDetailDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 处理申请
     * @param params
     * @return
     */
    @PostMapping("/asset_detail/approveAssetDetail")
    @Timed
    public ResponseEntity<ResponseResult> approveAssetDetail(@RequestBody  HashMap<String, Object> params){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to approveAssetDetail : {}", params);
        HashMap<String, Object> checkMap = assetDetailService.approveAssetDetail(params);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/asset_detail/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.debug("REST request to get one  AssetDetail : {}", id);
        AssetDetailDTO AssetDetailDTO = assetDetailService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(AssetDetailDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/asset_detail/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one AssetDetail : {}", id);
        assetDetailService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/asset_detail/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  AssetDetail ");
        List<AssetDetailDTO> list = assetDetailService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/asset_detail/loadMyAssetDetail")
    @Timed
    public ResponseEntity<ResponseResult> loadMyAssetDetail() {
        log.debug("REST request to loadMyAssetDetail {}");
        List<AssetDetailDTO> list = assetDetailService.loadMyAssetDetail();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}


