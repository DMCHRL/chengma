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
import com.chengma.devplatform.service.dto.TlbTradeDTO;
import com.chengma.devplatform.service.dto.TradeBackDTO;
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
        Page<TlbTradeDTO> page = tlbTradeService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tbl-user");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
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

      /*  for(int i = 1 ; i <= 330; i++){
            trade(i);
        }
        for(int i = 1 ; i <= 22; i++){
            account(i);
        }*/

       /* String toMail = "443099745@qq.com";
        String subject = "测试图片";
        StringBuilder content = new StringBuilder("<!DOCTYPE html>\n" +
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"utf-8\" />\n" +
                "\t\t<title></title>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<style>\n" +
                "\t\t\t* {padding: 0;margin: 0;}\n" +
                "\t\t\t.head_div img, .foot_div img {width: 80%;}\n" +
                "\t\t\t.body_div p, .foot_div p {font-size: 14px;color: #000;line-height: 30px;text-indent: 2em;}\n" +
                "\t\t\t.account_div {margin: 20px 0;}\n" +
                "\t\t\t.foot_div p i {color: #ff0000;font-size: 12px;}\n" +
                "\t\t</style>\n" +
                "\t\t<div class=\"head_div\">\n" +
                "\t\t\t<img src=\"http://tlb.txasfx.com/crm/img2/_20180723163728.png\"/>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"body_div\">\n" +
                "\t\t\t<p>尊敬的XXX，您好！</p>\n" +
                "\t\t\t<p>我們很高興通知您，您的真實賬戶已經申請成功。</p>\n" +
                "\t\t\t<p>賬戶信息如下：</p>\n" +
                "\t\t\t\n" +
                "\t\t\t<div class=\"account_div\">\n" +
                "\t\t\t\t<p><b>交易賬戶：12121212</b></p>\n" +
                "\t\t\t    <p><b>交易密碼：12121212</b></p>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"foot_div\">\n" +
                "\t\t\t<p>非常感謝您選擇Txasfx，祝您交易愉快！</p>\n" +
                "\t\t\t<p><i>*請妥善保存這份郵件，避免他人獲取您的賬戶資訊</i></p>\n" +
                "\t\t\t<img src=\"http://tlb.txasfx.com/crm/img2/_20180723163742.png\"/>\n" +
                "\t\t\t<p>該郵件由系統自動發送，請勿直接回復。</p>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>\n");*/
        //mailService.sendEmail(toMail, subject, content.toString(), true, true);
        //mailService.sendCreateAccountMail(userService.getUserWithAuthorities());


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


    private TradeBackDTO tradeBackDTO(Map<String, Object> map){
        TradeBackDTO tradeBackDTO = new TradeBackDTO();

        tradeBackDTO.setResult(map.get("result").toString());
        tradeBackDTO.setLots(map.get("lots").toString());
        tradeBackDTO.setSymbol(map.get("symbol").toString());
        tradeBackDTO.setRemark(map.get("remark").toString());
        tradeBackDTO.setState(map.get("state").toString());
        tradeBackDTO.setRstoplost(map.get("rstoplost").toString());
        tradeBackDTO.setClosetime(map.get("closetime").toString());
        tradeBackDTO.setRorderid(map.get("rorderid").toString());
        tradeBackDTO.setType(map.get("type").toString());
        tradeBackDTO.setRprice(map.get("rprice").toString());
        tradeBackDTO.setCloseprice(map.get("closeprice").toString());
        tradeBackDTO.setId(map.get("id").toString());
        tradeBackDTO.setTime(map.get("time").toString());
        tradeBackDTO.setRtime(map.get("rtime").toString());
        tradeBackDTO.setIsReal(map.get("isReal").toString());
        tradeBackDTO.setRstopwin(map.get("rstopwin").toString());
        tradeBackDTO.setAccount(map.get("buserId.account").toString());
        tradeBackDTO.setOpenprice(map.get("openprice").toString());
        return tradeBackDTO;
    }

    private List<TradeBackDTO> tradeBackDTOList(List<Map<String, Object>> mapList){
        List<TradeBackDTO> list = new ArrayList<>();
        for(Map<String, Object> map : mapList){
            TradeBackDTO tradeBackDTO = tradeBackDTO(map);
            list.add(tradeBackDTO);
        }
        return list;
    }

    private void saveTlbTradeBack(List<TradeBackDTO> list){
        String dateFormat      = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        for(TradeBackDTO tradeBackDTO : list){
            TlbTradeBack tlbTradeBack = new TlbTradeBack();
            tlbTradeBack.setAccount(tradeBackDTO.getAccount());
            tlbTradeBack.setLots(Double.valueOf(tradeBackDTO.getLots()));
            tlbTradeBack.setOrderNo(Long.valueOf(tradeBackDTO.getRorderid()));
            tlbTradeBack.setOpenPrice(Double.valueOf(tradeBackDTO.getOpenprice()));
            tlbTradeBack.setSymbol(tradeBackDTO.getSymbol());
            tlbTradeBack.setOrderType(tradeBackDTO.getType());
            tlbTradeBack.setOrdered("Y");
            tlbTradeBack.setClosePrice(tradeBackDTO.getCloseprice().equals("") ? 0 : Double.valueOf(tradeBackDTO.getCloseprice()));
            tlbTradeBack.setSl(Double.valueOf(tradeBackDTO.getRstoplost()));
            tlbTradeBack.setTp(Double.valueOf(tradeBackDTO.getRstopwin()));
            tlbTradeBack.setClosed(tradeBackDTO.getResult().equals("0")? "Y" : "N");
            try {
                tlbTradeBack.setOpenTime(formatter.parse(tradeBackDTO.getRtime()));
                tlbTradeBack.setCreateAt(formatter.parse(tradeBackDTO.getRtime()));
                tlbTradeBack.setServerTime(formatter.parse(tradeBackDTO.getTime()));
                if(tradeBackDTO.getResult().equals("0")) {
                    tlbTradeBack.setCloseTime(formatter.parse(tradeBackDTO.getClosetime()));
                    if(Double.valueOf(tradeBackDTO.getRemark()) > 0){
                        tlbTradeBack.setGainAmount(Double.valueOf(tradeBackDTO.getRemark()));
                        tlbTradeBack.setGain("Y");
                    }else{
                        tlbTradeBack.setGain("N");
                        tlbTradeBack.setGainAmount(Double.valueOf(0));
                    }
                }

            }catch (Exception e){

            }
            //2018-07-19 04:00:25

            tlbTradeBackRepository.save(tlbTradeBack);
        }
    }

    private void trade(int page){
        try{
            String path = "http://47.92.155.199/jeecg/ryBbbOrderdetailController.do?datagrid&field=id,buserId.account,symbol,openprice,time,closeprice,closetime,lots,type,result,isReal,rorderid,rprice,rstopwin,rstoplost,rtime,remark,&page=" + page + "&rows=18";
            URL url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length",
                    String.valueOf(path.length()));
            con.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
            con.setRequestProperty("Cookie", "JSESSIONID=020DE1D8298ABED210C2CED18B507B89");
            con.connect();
            if (con.getResponseCode() == 200) {

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {// 循环读取流
                    sb.append(line);
                }
                br.close();// 关闭流
                //System.out.println(sb);
                Map<String, Object> map = JSONUtils.json2map(sb.toString());
                List<Map<String, Object>> mapList = (List<Map<String, Object>>)map.get("rows");
                //List<TradeBackDTO> list = JSONUtils.json2list(map.get("rows").toString(), TradeBackDTO.class);
                List<TradeBackDTO> list = tradeBackDTOList(mapList);
                //JSONObject.parse(list.toString());
                saveTlbTradeBack(list);
            }else{
                System.out.println(con.getResponseCode());
            }
            con.disconnect();// 断开连接
        }catch (Exception e){

            System.out.println(e);
        }

    }

    private void account(int page){
        try{
            String path = "http://47.92.155.199/jeecg/ryBbbAccountController.do?datagrid&field=id,account,name,inputmoney,outmoney,margin" +
                    ",&page=" + page + "&rows=18";
            URL url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length",
                    String.valueOf(path.length()));
            con.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
            con.setRequestProperty("Cookie", "JSESSIONID=020DE1D8298ABED210C2CED18B507B89");
            con.connect();
            if (con.getResponseCode() == 200) {

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {// 循环读取流
                    sb.append(line);
                }
                br.close();// 关闭流
                //System.out.println(sb);
                Map<String, Object> map = JSONUtils.json2map(sb.toString());
                List<Map<String, Object>> mapList = (List<Map<String, Object>>)map.get("rows");
                /*List<TradeBackDTO> list = tradeBackDTOList(mapList);
                saveTlbTradeBack(list);*/
                for(Map<String, Object> objectMap : mapList){
                    TlbAccountBack tlbAccountBack = new TlbAccountBack();
                    tlbAccountBack.setAccount(objectMap.get("account").toString());
                    tlbAccountBack.setAccountName(objectMap.get("name").toString());
                    String in = "".equals(objectMap.get("inputmoney").toString()) ? "0" : objectMap.get("inputmoney").toString();
                    String out = "".equals(objectMap.get("outmoney").toString()) ? "0" : objectMap.get("outmoney").toString();
                    String bal = "".equals(objectMap.get("margin").toString()) ? "0" : objectMap.get("margin").toString();
                    tlbAccountBack.setIn(Double.valueOf(in));
                    tlbAccountBack.setOut(Double.valueOf(out));
                    tlbAccountBack.setBalance(Double.valueOf(bal));
                    tlbAccountBack.setBal(Double.valueOf(bal));
                    tlbAccountBack.setProfit(Double.valueOf(bal) + Double.valueOf(out) - Double.valueOf(in) );
                    tlbAccountBack.setCreateAt(new Date());
                    tlbAccountBackRepository.save(tlbAccountBack);
                }
            }else{
                System.out.println(con.getResponseCode());
            }
            con.disconnect();// 断开连接
        }catch (Exception e){

            System.out.println(e);
        }

    }

}

