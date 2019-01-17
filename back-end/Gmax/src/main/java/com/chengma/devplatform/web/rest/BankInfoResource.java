package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.BankInfoService;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * REST controller for managing BankInfo.
 */
@RestController
@RequestMapping("/api")
public class BankInfoResource {

    private final Logger log = LoggerFactory.getLogger(BankInfoResource.class);

    private static final String ENTITY_NAME = "bankInfo";

    private final BankInfoService bankInfoService;

    public BankInfoResource(BankInfoService bankInfoService) {
        this.bankInfoService = bankInfoService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param bankInfoDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
  /*  @PostMapping("/bank_info/createBankInfo")
    @Timed*/
    public ResponseEntity<ResponseResult> createBankInfo(@RequestBody BankInfoDTO bankInfoDTO) throws URISyntaxException {
        log.debug("REST request to save BankInfo : {}", bankInfoDTO);
        if (bankInfoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new BankInfo cannot create already have an ID")).body(null);
        }
        BankInfoDTO  bankInfoDTO1 = bankInfoService.save(bankInfoDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(bankInfoDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 保存银行卡信息
     * @param bankInfoDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/bank_info/saveBankInfo")
    @Timed
    public ResponseEntity<ResponseResult> updateBankInfo(@RequestBody BankInfoDTO bankInfoDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        String userId=bankInfoDTO.getUserId();
        if(userId==null||"".equals(userId)){
            json.setStatusCode(ResponseResult.FAIL_CODE);
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        BankInfoDTO currentBankInfo = bankInfoService.findByUserId(bankInfoDTO.getUserId());
        if (currentBankInfo == null) {
            return createBankInfo(bankInfoDTO);
        }
        log.debug("REST request to update BankInfo : {}", bankInfoDTO);
        bankInfoDTO.setId(currentBankInfo.getId());
        BankInfoDTO bankInfoDTO1 = bankInfoService.save(bankInfoDTO);;
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(bankInfoDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /user/get/{id} ：获取用户信息
     *
     * @param userId 用户id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/bank_info/get/{userId}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String userId) {
        BankInfoDTO BankInfoDTO = bankInfoService.findByUserId(userId);
        ResponseResult json = new ResponseResult();
        json.setData(BankInfoDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    /*@PostMapping("/bank_info/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of BankInfos");
        Page<BankInfoDTO> page = bankInfoService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bank_info");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }*/


}
