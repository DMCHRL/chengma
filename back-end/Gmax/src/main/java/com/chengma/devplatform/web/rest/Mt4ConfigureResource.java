package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.UserRepository;
import com.chengma.devplatform.security.SecurityUtils;
import com.chengma.devplatform.service.MailService;
import com.chengma.devplatform.service.Mt4ConfigureService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.Mt4ConfigureDTO;
import com.chengma.devplatform.service.dto.UserDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.chengma.devplatform.web.rest.vm.KeyAndPasswordVM;
import com.chengma.devplatform.web.rest.vm.ManagedUserVM;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing the current user's account.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class Mt4ConfigureResource {

    private final Logger log = LoggerFactory.getLogger(Mt4ConfigureResource.class);

    private static final String ENTITY_NAME = "mt4Configure";

    private final Mt4ConfigureService mt4ConfigureService;
    

    public Mt4ConfigureResource(Mt4ConfigureService mt4ConfigureService) {
        this.mt4ConfigureService = mt4ConfigureService;
    }


    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/mt4_configure/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of Mt4Configure",params);
        Page<Mt4ConfigureDTO> page = mt4ConfigureService.pageList(params);
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
     * @param mt4ConfigureDTO
     * @return
     */
    @PostMapping("/mt4_configure/createMt4Configure")
    @Timed
    public ResponseEntity<ResponseResult> updateMt4Configure(@RequestBody Mt4ConfigureDTO mt4ConfigureDTO){
        ResponseResult json = new ResponseResult();
        log.debug("REST request to create a Mt4Configure : {}", mt4ConfigureDTO);
        HashMap<String, Object> checkMap = mt4ConfigureService.checkCreateMt4ConfigureDTO(mt4ConfigureDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        Mt4ConfigureDTO mt4ConfigureDTO1 = mt4ConfigureService.createMt4ConfigureDTO(mt4ConfigureDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(mt4ConfigureDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/mt4_configure/get")
    @Timed
    public ResponseEntity<ResponseResult> get() {
        log.debug("REST request to get one  Mt4Configure : {}");
        Mt4ConfigureDTO Mt4ConfigureDTO = mt4ConfigureService.findOne();
        ResponseResult json = new ResponseResult();
        json.setData(Mt4ConfigureDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @param id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/mt4_configure/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete one Mt4Configure : {}", id);
        mt4ConfigureService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/mt4_configure/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to findAll  Mt4Configure ");
        List<Mt4ConfigureDTO> list = mt4ConfigureService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
