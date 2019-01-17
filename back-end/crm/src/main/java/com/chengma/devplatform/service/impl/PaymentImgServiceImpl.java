package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.PaymentImg;
import com.chengma.devplatform.repository.PaymentImgRepository;
import com.chengma.devplatform.service.FileStreamManageService;
import com.chengma.devplatform.service.PaymentImgService;
import com.chengma.devplatform.service.dto.PaymentImgDTO;
import com.chengma.devplatform.service.mapper.PaymentImgMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class PaymentImgServiceImpl implements PaymentImgService {

    private final Logger log = LoggerFactory.getLogger(PaymentImgServiceImpl.class);

    private final PaymentImgRepository paymentImgRepository;

    private final PaymentImgMapper paymentImgMapper;

    public PaymentImgServiceImpl(PaymentImgRepository paymentImgRepository, PaymentImgMapper paymentImgMapper) {
        this.paymentImgRepository = paymentImgRepository;
        this.paymentImgMapper = paymentImgMapper;
    }

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private DBService dbService;

    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private Environment environment;

    @Override
    public PaymentImgDTO save(PaymentImgDTO paymentImgDTO) {
        BASE64Decoder decoder = new BASE64Decoder();
        String videoImageWebPath = environment.getProperty("fileManage.videoImageWebPath");
        String imagePath = environment.getProperty("fileManage.imagePath");
        String prefix = environment.getProperty("fileManage.base64Prefix");
        String newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg" ;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        imagePath = imagePath + sdf.format(new Date()) + "/";

        File dest =new File(imagePath);
        //如果文件夹不存在则创建
        if  (!dest .exists()  && !dest .isDirectory()) {
            dest .mkdir();
        }

        if(!StringUtils.isEmpty(paymentImgDTO.getImg()) && paymentImgDTO.getImg().startsWith(prefix)) {
            this.uploadPaymentImage(decoder, paymentImgDTO.getImg(), imagePath, prefix, newFileName);
            paymentImgDTO.setImg(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(StringUtils.isBlank(paymentImgDTO.getId())){
            Date now =new Date();
            paymentImgDTO.setCreateAt(now);
            paymentImgDTO.setUpdateAt(now);
            paymentImgDTO.setFlag("0");
        }else{
             PaymentImg currentPatmentImg = paymentImgRepository.findOne(paymentImgDTO.getId());
             paymentImgDTO.setCreateAt(currentPatmentImg.getCreateAt());
             paymentImgDTO.setUpdateAt(new Date());
             paymentImgDTO.setFlag(currentPatmentImg.getFlag());
        }

        return paymentImgMapper.toDto(paymentImgRepository.save(paymentImgMapper.toEntity(paymentImgDTO)));
    }

    private void uploadPaymentImage(BASE64Decoder decoder, String str, String imagePath, String prefix, String newFileName){
        HashMap<String, Object> params = new HashMap<>();
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(decoder.decodeBuffer(str.substring(prefix.length())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        params.put("uploadPath", imagePath);
        params.put("newFileName", newFileName);
        fileStreamManageService.uploadFile(inputStream,params);
    }

    @Override
    public HashMap<String, Object> checkPaymentImgDTO(PaymentImgDTO paymentImgDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(paymentImgDTO.getImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請上傳圖片");
            return retMap;
        }

        if(StringUtils.isBlank(paymentImgDTO.getShopName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入商鋪名");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Page<PaymentImgDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public PaymentImgDTO findOne(String id) {
        return paymentImgMapper.toDto(paymentImgRepository.findOne(id));
    }

    @Override
    public List<PaymentImgDTO> findAll() {
        return paymentImgMapper.toDto(paymentImgRepository.findAll());
    }

    @Override
    public void delete(String id) {
        paymentImgRepository.delete(id);
    }

    @Override
    public PaymentImgDTO findNewImg() {
        return paymentImgMapper.toDto(paymentImgRepository.findByFlagEquals("1"));
    }

    @Override
    public PaymentImgDTO setImgFlag(String id) {
        List<PaymentImgDTO> list= this.findAll();
        PaymentImgDTO paymentImgDTO=null;
        for(PaymentImgDTO p:list){
            if(p.getId().equals(id)){
                String flag=p.getFlag();
                if(flag.equals("1")) {
                    p.setFlag("0");
                }else{
                    p.setFlag("1");
                }
                paymentImgDTO=p;
            }else{
                p.setFlag("0");
            }
            paymentImgRepository.save(paymentImgMapper.toEntity(p));
        }
        return paymentImgDTO;
    }
}
