package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.MobileService;
import com.chengma.devplatform.service.dto.MobileDTO;
import com.codahale.metrics.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * REST controller for managing MobileValidate.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class MobileResource {


    private final MobileService mobileService;


    public MobileResource(MobileService mobileService) {
        this.mobileService = mobileService;
    }

    /**
     * post /mobileValidate/sendCode ：发送短信验证码
     *
     * @param params 参数：{mobileNum:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/potential-user/pageList")
    @Timed
    public ResponseEntity<ResponseResult> sendCode(@RequestBody HashMap<String, Object> params) {
        Page<MobileDTO> page =  mobileService.pageList(params);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/potential-user/editUser")
    @Timed
    public ResponseEntity<ResponseResult> editUser(@RequestBody MobileDTO mobileDTO) {
        MobileDTO mobileDTO1 =  mobileService.update(mobileDTO);
        ResponseResult json = new ResponseResult();
        json.setData(mobileDTO1);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
