package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppServer;
import com.chengma.devplatform.service.HppServerService;
import com.chengma.devplatform.service.dto.HppServerDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing HppServer.
 */
@RestController
@RequestMapping("/api")
public class HppServerResource {

    private final Logger log = LoggerFactory.getLogger(HppServerResource.class);

    private static final String ENTITY_NAME = "hppServer";

    private final HppServerService hppServerService;

    public HppServerResource(HppServerService hppServerService) {
        this.hppServerService = hppServerService;
    }


    /**
     * 保存客服信息
     * @param hppServerDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_server/saveHppServer")
    @Timed
    public ResponseEntity<ResponseResult> updateHppServer(@RequestBody HppServerDTO hppServerDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> checkMap = hppServerService.checkCreateHppServerDTO(hppServerDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        log.debug("REST request to update HppServer : {}", hppServerDTO);
        HppServerDTO hppServerDTO1 = hppServerService.createHppServerDTO(hppServerDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppServerDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /user/get/{id} ：获取用户信息
     *
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/hpp_server/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        HppServerDTO HppServerDTO = hppServerService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(HppServerDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_server/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        hppServerService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/hpp_server/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        List<HppServerDTO> list =  hppServerService.findAll();
        HashMap<String,Object> retMap = new HashMap<>();
        retMap.put("list",list);
        ResponseResult json = new ResponseResult();
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
