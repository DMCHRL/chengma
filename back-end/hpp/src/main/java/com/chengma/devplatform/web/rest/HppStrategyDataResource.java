package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.HppStrategyData;
import com.chengma.devplatform.service.HppStrategyDataService;
import com.chengma.devplatform.service.HppStrategyService;
import com.chengma.devplatform.service.dto.HppStrategyDTO;
import com.chengma.devplatform.service.dto.HppStrategyDataDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managingHppStrategy.
 */
@RestController
@RequestMapping("/api")
public class HppStrategyDataResource {

    private final Logger log = LoggerFactory.getLogger(HppStrategyDataResource.class);

    private static final String ENTITY_NAME = "hppStrategyData";

    private final HppStrategyDataService hppStrategyDataService;

    public HppStrategyDataResource(HppStrategyDataService hppStrategyDataService) {
        this.hppStrategyDataService =hppStrategyDataService;
    }


    /**
     *获取策略具体数据(app)
     * @param account
     * @return
     */
    @GetMapping("/hpp_strategy_data/findByAccount/{account}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String account) {
        log.debug("REST request to get a hppStrategyDataDTO : {}", account);
        ResponseResult json = new ResponseResult();
        HppStrategyDataDTO hppStrategyDataDTO= hppStrategyDataService.findByAccount(account);
        json.setData(hppStrategyDataDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     *获取账号具体数据(app)
     * @param account
     * @return
     */
    @GetMapping("/hpp_strategy_data/loadByAccount/{account}")
    @Timed
    public ResponseEntity<ResponseResult> loadByAccount(@PathVariable String account) {
        log.debug("REST request to loadByAccount of hppStrategyDataDTO : {}", account);
        ResponseResult json = new ResponseResult();
        HppStrategyDataDTO hppStrategyDataDTO= hppStrategyDataService.loadByAccount(account);
        json.setData(hppStrategyDataDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
