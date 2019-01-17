package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.SmallTools;
import com.chengma.devplatform.domain.UnionPayInfo;
import com.chengma.devplatform.service.UnionPayInfoService;
import com.chengma.devplatform.service.dto.UnionPayInfoDTO;
import com.chengma.devplatform.service.impl.XXPayServiceImpl;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing users.
 * <p>
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * </p>
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>Another option would be to have a specific JPA entity graph to handle this case.</p>
 */
@RestController
@RequestMapping("/api")
public class UnionPayInfoResource {

    private final Logger log = LoggerFactory.getLogger(UnionPayInfoResource.class);

    @Autowired
    private UnionPayInfoService unionPayInfoService;


    /**
     * 下单
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysRoles in body
     */
    @PostMapping("/union-pay-info/createOrder")
    @Timed
    public ResponseEntity<ResponseResult> save(@RequestBody UnionPayInfoDTO unionPayInfoDTO) {
        log.info("save unionPayInfo :" + unionPayInfoDTO);
        ResponseResult json = new ResponseResult();
       /* HashMap<String,Object> checkMap = unionPayInfoService.checkSave(unionPayInfoDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }*/
        UnionPayInfoDTO UnionPayInfoDTO1 = unionPayInfoService.createOrder(unionPayInfoDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(UnionPayInfoDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 异步通知
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysRoles in body
     */
    @PostMapping("/union-pay-info/notifyUrl")
    @Timed
    public void paySuccess(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BufferedReader reader = request.getReader();
        String input = null;
        StringBuffer requestBody = new StringBuffer();
        while((input = reader.readLine()) != null) {
            requestBody.append(input);
        }
        String msg = requestBody.toString();
        System.out.println(msg);
        //返回参数进行验签
        String str = SmallTools.toJson(msg);
        Boolean isAttestationBy = SmallTools.checkSign(str,"77f214b4b5e6e18b15dd97796815d915");

        //{"result":2001,"amount":"10.00","merchantId":101053,"sign":"c3568ca86847c28bd047e7f2b27bc274","transNo":"11175100021535963151","retCode":1,"merchantOrderNo":"tlb20180903162544","retMsg":"处理中"}
        //{"successAmount":"10","payAmount":"10","transNo":"11175100021535963151","result":"1000","merchantId":"101053","merchantOrderNo":"tlb20180903162544","version":"1.0","sign":""}
        String merchantOrderNo="";
        String transNo="";
        String result=""; //1000成功，1002失败
        if (isAttestationBy){
            UnionPayInfoDTO unionPayInfoDTO = unionPayInfoService.findByMerchantOrderNoEquals(merchantOrderNo);
            if(unionPayInfoDTO != null){
                unionPayInfoDTO.setTransNo(transNo);
                if(result.equals("1000")){
                    unionPayInfoDTO.setPaySuccess(DevplatformConstants.UNION_PAY_STATUS_Y);
                }
                unionPayInfoDTO.setUpdateAt(new Date());
                unionPayInfoService.save(unionPayInfoDTO);
            }
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.println("ok");
            out.flush();
            out.close();
        }else {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.println("error");
            out.flush();
            out.close();
        }
    }

    @GetMapping("/union-pay-info/queryOrder/{orderNo}")
    @Timed
    public ResponseEntity<ResponseResult> save(@PathVariable String orderNo) {
        log.info("save unionPayInfo :" + orderNo);
        ResponseResult json = new ResponseResult();
       /* HashMap<String,Object> checkMap = unionPayInfoService.checkSave(unionPayInfoDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }*/
        UnionPayInfoDTO UnionPayInfoDTO1 = unionPayInfoService.findUnionPayByTransactionId(orderNo);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(UnionPayInfoDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
