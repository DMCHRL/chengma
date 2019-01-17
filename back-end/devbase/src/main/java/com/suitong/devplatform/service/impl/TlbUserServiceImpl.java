package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.dao.BaseDao;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.page.PageCommon;
import com.suitong.devplatform.common.util.DateUtil;
import com.suitong.devplatform.common.util.IpKit;
import com.suitong.devplatform.common.util.StringUtil;
import com.suitong.devplatform.domain.SysForm;
import com.suitong.devplatform.domain.SysOperateLog;
import com.suitong.devplatform.domain.SysRoleForm;
import com.suitong.devplatform.domain.TlbUser;
import com.suitong.devplatform.repository.SysOperateLogRepository;
import com.suitong.devplatform.repository.TlbUserRepository;
import com.suitong.devplatform.service.FileStreamManageService;
import com.suitong.devplatform.service.SysOperateLogService;
import com.suitong.devplatform.service.TlbUserService;
import com.suitong.devplatform.service.dto.SysOperateLogDTO;
import com.suitong.devplatform.service.dto.SysRoleFormDTO;
import com.suitong.devplatform.service.dto.TlbUserDTO;
import com.suitong.devplatform.service.mapper.SysOperateLogMapper;
import com.suitong.devplatform.service.mapper.TlbUserMapper;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class TlbUserServiceImpl implements TlbUserService {

    private final Logger log = LoggerFactory.getLogger(TlbUserServiceImpl.class);

    private final TlbUserRepository tlbUserRepository;

    private final TlbUserMapper tlbUserMapper;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private DBService dbService;

    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private Environment environment;

    @Autowired
    private BaseDao baseDao;

    public TlbUserServiceImpl(TlbUserRepository tlbUserRepository, TlbUserMapper tlbUserMapper) {
        this.tlbUserRepository = tlbUserRepository;
        this.tlbUserMapper = tlbUserMapper;
    }


    @Override
    public HashMap<String, Object> save(TlbUserDTO tlbUserDTO) {
        log.debug("Request to save SysForm : {}", tlbUserDTO);
        HashMap<String,Object> response = new HashMap<>();
        try {

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

            if(!StringUtils.isEmpty(tlbUserDTO.getCardPositive())) {
                uploadTlbUserImage(decoder, tlbUserDTO.getCardPositive(), imagePath, prefix, newFileName);
                tlbUserDTO.setCardPositive(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
            }
            if(!StringUtils.isEmpty(tlbUserDTO.getIdPositive())) {
                newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                uploadTlbUserImage(decoder, tlbUserDTO.getIdPositive(), imagePath, prefix, newFileName);
                tlbUserDTO.setIdPositive(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
            }
            if(!StringUtils.isEmpty(tlbUserDTO.getIdOther())) {
                newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                uploadTlbUserImage(decoder, tlbUserDTO.getIdOther(), imagePath, prefix, newFileName);
                tlbUserDTO.setIdOther(videoImageWebPath+ sdf.format(new Date()) + "/"  + newFileName);
            }


            TlbUser tlbUser = tlbUserMapper.toEntity(tlbUserDTO);
            tlbUser = tlbUserRepository.save(tlbUser);

            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    @Override
    public Page<TlbUserDTO> pageList(Pageable pageable) {
        return null;
    }

    @Override
    public List<TlbUserDTO> findAll(HashMap<String, Object> params) {

        //List<TlbUser> tlbUsers = tlbUserRepository.findAll();

        //tlbUserRepository.findAll()

        List<TlbUserDTO> list = baseDao.findListBySql("select id, c_username, c_phone from t_tlb_user", TlbUserDTO.class);

        /*List<TlbUserDTO> tlbUserDTOS = new ArrayList<>();
        for(TlbUser tlbUser : tlbUsers){
            tlbUserDTOS.add(tlbUserMapper.toDto(tlbUser));
        }*/

        return list;
    }

    @Override
    public List<TlbUserDTO> queryComponentPage(Long formId, String visible) {
        return null;
    }

    @Override
    public TlbUserDTO findOne(Long id) {
        return tlbUserMapper.toDto(tlbUserRepository.findOne(id));
    }

    @Override
    public void delete(Long id) {
        tlbUserRepository.delete(id);
    }

    @Override
    public void deleteSysRoleComponent(Long id) {

    }

    private void uploadTlbUserImage(BASE64Decoder decoder, String str, String imagePath, String prefix, String newFileName){
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
}
