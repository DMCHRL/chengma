package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppMobileUserService;
import com.chengma.devplatform.service.dto.HppMobileUserDTO;
import com.chengma.devplatform.service.dto.HppMobileUserDTO;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing HppMobileUserValidate.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class HppMobileUserResource {


    private final HppMobileUserService hppMobileUserService;


    public HppMobileUserResource(HppMobileUserService hppMobileUserService) {
        this.hppMobileUserService = hppMobileUserService;
    }

    /**
     * post /hppMobileUserValidate/sendCode ：发送短信验证码
     *
     * @param params 参数：{hppMobileUserNum:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/hpp_mobile_user/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        Page<HppMobileUserDTO> page =  hppMobileUserService.pageList(params);
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
     * get  .
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_mobile_user/get/{mobileNum}")
    @Timed
    public ResponseEntity<ResponseResult> findByTypeId(@PathVariable String mobileNum) {
        HppMobileUserDTO hppMobileUserDTO = hppMobileUserService.findByMobile(mobileNum);
        ResponseResult json = new ResponseResult();
        json.setData(hppMobileUserDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post  .
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_mobile_user/edit")
    @Timed
    public ResponseEntity<ResponseResult> edit(@RequestBody HppMobileUserDTO hppMobileUserDTO) {
        ResponseResult json = new ResponseResult();
        if(StringUtils.isBlank(hppMobileUserDTO.getId())){
            HashMap<String, Object> checkMap=hppMobileUserService.checkHppMobileUserDTO(hppMobileUserDTO);
            if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
                json.setMsgCode(checkMap.get("msg").toString());
                return new ResponseEntity<>(json, null, HttpStatus.OK);
            }
        }
        HppMobileUserDTO hppMobileUserDTO1 = hppMobileUserService.edit(hppMobileUserDTO);
        json.setData(hppMobileUserDTO1);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post  .
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_mobile_user/setCid")
    @Timed
    public ResponseEntity<ResponseResult> setCid(@RequestBody HashMap<String,Object> params) {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap=hppMobileUserService.setCid(params);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * get  .
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_mobile_user/findByRecommendation")
    @Timed
    public ResponseEntity<ResponseResult> findByRecommendation(@RequestBody HashMap<String,Object> params) {
        Page<HppMobileUserDTO> page = hppMobileUserService.findByRecommendationEd(params);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
