package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.TlbCommissionService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.TlbCommissionDTO;
import com.codahale.metrics.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class TlbCommissionResource {
	
	@Resource
	private TlbCommissionService tlbCommissionService;
	
	@Resource
	private UserService userService;
	

	/**
	 * 根据login或firstName查询
	 * params {formParams,page_number,page_number}
	 * @param params
	 * @return
	 */
	@PostMapping("/tlb-commission/pageList")
	@Timed
	public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params){
		Page<TlbCommissionDTO> tlbCommissionDTOList =tlbCommissionService.pageList(params);
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("list", tlbCommissionDTOList.getContent());
		response.put("total", tlbCommissionDTOList.getTotalElements());
		response.put("totalPage", tlbCommissionDTOList.getTotalPages());
		ResponseResult json = new ResponseResult();
		json.setStatusCode(ResponseResult.SUCCESS_CODE);
		json.setData(response);
		return new ResponseEntity<>(json, null, HttpStatus.OK);
	}


	@PostMapping("/tlb-commission/setConfiguration")
	@Timed
	public ResponseEntity<ResponseResult> setConfiguration(@RequestBody TlbCommissionDTO tlbCommissiondto){
		tlbCommissiondto=tlbCommissionService.setConfiguration(tlbCommissiondto);
		ResponseResult json = new ResponseResult();
		json.setStatusCode(ResponseResult.SUCCESS_CODE);
		json.setData(tlbCommissiondto);
		return new ResponseEntity<>(json, null, HttpStatus.OK);
	}

	@GetMapping("/tlb-commission/getConfiguration/{userId}")
	@Timed
	public ResponseEntity<ResponseResult> getConfiguration(@PathVariable String userId){
		TlbCommissionDTO tlbCommissiondto=tlbCommissionService.getConfiguration(userId);
		ResponseResult json = new ResponseResult();
		if(tlbCommissiondto==null){
			json.setStatusCode(ResponseResult.FAIL_CODE);
		}else{
			json.setStatusCode(ResponseResult.SUCCESS_CODE);
		}
		json.setData(tlbCommissiondto);
		return new ResponseEntity<>(json, null, HttpStatus.OK);
	}
	
	/**
	 * 平仓触发
	 * @param accountId
	 * @return
	 */
	/*@PostMapping("/tlb-commission/updateTotal/{accountId}")
    @Timed
    public ResponseEntity<ResponseResult> updateTotal(@PathVariable String accountId){
		tlbCommissionService.updateTotal(accountId);
	 	ResponseResult json = new ResponseResult();
	 	json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
	 	
	}*/
	
	/**
	 * 更新余额
	 * @param params
	 * @return
	 */
	@PostMapping("/tlb-commission/updateCommissionBalance")
    @Timed
    public ResponseEntity<ResponseResult> updateCommissionBalance(@RequestBody HashMap<String,Object> params){
		TlbCommissionDTO tlbCommissiondto=tlbCommissionService.updateCommissionBalance(params.get("accountId").toString(), (Double)params.get("amonut"));
	 	ResponseResult json = new ResponseResult();
	 	if(tlbCommissiondto==null){
	 		json.setStatusCode(ResponseResult.FAIL_CODE);
	 	}else{
	 		json.setStatusCode(ResponseResult.SUCCESS_CODE);
	 		json.setData(tlbCommissiondto);
	 	}
        return new ResponseEntity<>(json, null, HttpStatus.OK);
	}
	
}
