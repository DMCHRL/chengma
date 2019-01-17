package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.JSONUtils;
import com.chengma.devplatform.domain.TlbAccountBack;
import com.chengma.devplatform.domain.TlbTradeBack;
import com.chengma.devplatform.repository.TlbAccountBackRepository;
import com.chengma.devplatform.repository.TlbTradeBackRepository;
import com.chengma.devplatform.service.MailService;
import com.chengma.devplatform.service.TlbTradeService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.TlbDataDTO;
import com.chengma.devplatform.service.dto.TlbTradeDTO;
import com.chengma.devplatform.service.dto.TradeBackDTO;
import com.chengma.devplatform.service.dto.TradeDataDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * REST controller for managing TlbTrade.
 */
@RestController
@RequestMapping("/api")
public class TlbTradeResource {

    private final Logger log = LoggerFactory.getLogger(TlbTradeResource.class);

    private static final String ENTITY_NAME = "tlbTrade";

    private final TlbTradeService tlbTradeService;

    @Autowired
    private TlbTradeBackRepository tlbTradeBackRepository;

    @Autowired
    private TlbAccountBackRepository tlbAccountBackRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    public TlbTradeResource(TlbTradeService tlbTradeService) {
        this.tlbTradeService = tlbTradeService;
    }

    /**
     * POST  /sys-components : Create a new sysComponent.
     *
     * @param tlbTradeDTO the sysComponentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysComponentDTO, or with status 400 (Bad Request) if the sysComponent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tlb-trade/createTlbTrade")
    @Timed
    public ResponseEntity<ResponseResult> createTlbTrade(@RequestBody TlbTradeDTO tlbTradeDTO) throws URISyntaxException {
        log.debug("REST request to save TlbTrade : {}", tlbTradeDTO);
        if (tlbTradeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysComponent cannot already have an ID")).body(null);
        }
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = tlbTradeService.checkLotsTrade(tlbTradeDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        TlbTradeDTO tradeDTO = tlbTradeService.save(tlbTradeDTO);
        //ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tradeDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-trade/updateTlbUser")
    @Timed
    public ResponseEntity<ResponseResult> updateTlbUser(@RequestBody TlbTradeDTO tlbTradeDTO) throws URISyntaxException {
        log.debug("REST request to update TlbTrade : {}", tlbTradeDTO);
        if (tlbTradeDTO.getId() == null) {
            return createTlbTrade(tlbTradeDTO);
        }
        TlbTradeDTO tlbTradeDTO1 = tlbTradeService.save(tlbTradeDTO);;
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(tlbTradeDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-trade/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody  HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbTrades");
        HashMap<String,Object> response = tlbTradeService.pageList(params);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbl-user");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        Page<TlbTradeDTO> page=(Page<TlbTradeDTO>)response.get("page");
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        retMap.put("sumLots", response.get("sumLots"));
        retMap.put("sumAmount", response.get("sumAmount"));
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-trade/openPageList")
    @Timed
    public ResponseEntity<ResponseResult> openPageList(@RequestBody  HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbTrades");
        params.put("closed", "N");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        HashMap<String,Object> response = tlbTradeService.pageList(params);
        Page<TlbTradeDTO> page=(Page<TlbTradeDTO>)response.get("page");
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        retMap.put("sumLots", response.get("sumLots"));
        retMap.put("sumAmount", response.get("sumAmount"));
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * GET  /sys-components : get all the sysComponents.
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/tlb-trade/getAll")
    @Timed
    public ResponseEntity<ResponseResult> getAll(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of TlbTrades");
        List<TlbTradeDTO> tlbTradeDTOList = tlbTradeService.findAll(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbTradeDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * GET  /sys-components/:id : get the "id" sysComponent.
     *
     * @param id the id of the sysComponentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysComponentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tlb-trade/TlbTrade/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getTlbTrade(@PathVariable String id) {
        log.debug("REST request to get TlbTrade : {}", id);
        ResponseResult json = new ResponseResult();
        try {
            TlbTradeDTO sysComponentDTO = tlbTradeService.findOne(id);
            json.setData(sysComponentDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }

        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }


    @DeleteMapping("/tlb-trade/deleteTlbTrade/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteTlbTrade(@PathVariable String id) {
        log.debug("REST request to delete TlbTrade : {}", id);
        tlbTradeService.delete(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/tlb-trade/openTlbTrades/{account}")
    @Timed
    public ResponseEntity<ResponseResult> openTlbTrades(@PathVariable String account) {
        log.debug("REST request to load all open TlbTrade : {}", account);
        ResponseResult json = new ResponseResult();
        json.setData(tlbTradeService.openTlbTrads(account));
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping("/tlb-trade/closeTlbTrades/{account}")
    @Timed
    public ResponseEntity<ResponseResult> closeTlbTrades(@PathVariable String account) {
        log.debug("REST request to load all closed TlbTrade : {}", account);
        ResponseResult json = new ResponseResult();
        json.setData(tlbTradeService.closedTlbTrads(account));
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/tlb-trade/findTlbTradesWithClosePosition")
    @Timed
    public ResponseEntity<ResponseResult> findTlbTradesWithClosePosition() {
        List<TlbTradeDTO> tlbTradeDTOList = tlbTradeService.findTlbTradesWithClosePosition();
        ResponseResult json = new ResponseResult();
        json.setData(tlbTradeDTOList);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/tlb-trade/getTlbData")
    @Timed
    public ResponseEntity<ResponseResult> getTlbData() {
        TlbDataDTO tlbDataDTO = tlbTradeService.getTlbData();
        ResponseResult json = new ResponseResult();
        json.setData(tlbDataDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }


    @PostMapping("/tlb-trade/getTlbTradeProfit")
    @Timed
    public ResponseEntity<ResponseResult> getTlbTradeProfit(@RequestBody HashMap<String, Object> params) {
        List<TradeDataDTO> tlbDataDTO = tlbTradeService.getTlbTradeProfit(params);
        ResponseResult json = new ResponseResult();
        json.setData(tlbDataDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }


    @GetMapping("/tlb-trade/getTlbTradeWin/{account}")
    @Timed
    public ResponseEntity<ResponseResult> getTlbTradeProfit(@PathVariable String account) {
        TradeDataDTO tlbDataDTO = tlbTradeService.getTlbTradeWin(account);
        ResponseResult json = new ResponseResult();
        json.setData(tlbDataDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/tlb-trade/getTlbTradeSymbol/{account}")
    @Timed
    public ResponseEntity<ResponseResult> getTlbTradeSymbol(@PathVariable String account) {
        List<TradeDataDTO> tlbDataDTO = tlbTradeService.getTlbTradeSymbol(account);
        ResponseResult json = new ResponseResult();
        json.setData(tlbDataDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

}

