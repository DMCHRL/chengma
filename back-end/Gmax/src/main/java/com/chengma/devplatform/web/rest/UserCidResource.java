package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.UserCidService;
import com.chengma.devplatform.service.dto.NoticeDTO;
import com.chengma.devplatform.service.dto.SendNoticeDTO;
import com.chengma.devplatform.service.dto.UserCidDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
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
 * REST controller for managingNotice.
 */
@RestController
@RequestMapping("/api")
public class UserCidResource {

    private final Logger log = LoggerFactory.getLogger(UserCidResource.class);

    private static final String ENTITY_NAME = "userCid";

    private final UserCidService userCidService;

    public UserCidResource(UserCidService userCidService) {
        this.userCidService =userCidService;
    }


    /**
     * 保存cid
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/userCid/setCid")
    @Timed
    public ResponseEntity<ResponseResult> saveNotice(@RequestBody UserCidDTO userCidDTO) {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = userCidService.checkSave(userCidDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save Notice : {}", userCidDTO);
        UserCidDTO userCidDTO1 = userCidService.save(userCidDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(userCidDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


}
