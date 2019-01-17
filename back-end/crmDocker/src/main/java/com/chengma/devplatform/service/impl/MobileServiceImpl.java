package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.Mobile;
import com.chengma.devplatform.repository.MobileRepository;
import com.chengma.devplatform.service.MobileService;
import com.chengma.devplatform.service.dto.MobileDTO;
import com.chengma.devplatform.service.mapper.MobileMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


/**
 * Service Implementation for managing Mobile.
 *
 * @author administrator
 */
@Service
@Transactional
public class MobileServiceImpl implements MobileService {

    private final Logger log = LoggerFactory.getLogger(MobileServiceImpl.class);

    private final MobileRepository mobileRepository;

    private final MobileMapper mobileMapper;


    @Autowired
    private PageCommon pageCommon;

    public MobileServiceImpl(MobileRepository mobileRepository, MobileMapper mobileMapper) {
        this.mobileRepository = mobileRepository;
        this.mobileMapper = mobileMapper;
    }

    /**
     * 保存
     *
     * @param mobileDTO 短信验证码信息
     * @return MobileDTO
     */
    @Override
    public MobileDTO save(MobileDTO mobileDTO) {
        log.debug("Request to save Mobile : {}", mobileDTO);
        Mobile mobile = mobileMapper.toEntity(mobileDTO);
        mobile = mobileRepository.save(mobile);
        return mobileMapper.toDto(mobile);
    }

    @Override
    public MobileDTO update(MobileDTO mobileDTO) {
        Mobile cuurentMobile=mobileRepository.findMobileByMobileNumEquals(mobileDTO.getMobileNum());
        if(cuurentMobile==null){
            return save(mobileDTO);
        }else{
            cuurentMobile.setText(mobileDTO.getText());
            cuurentMobile.setChangeUser(mobileDTO.getChangeUser());
        }
        log.debug("Request to update Mobile : {}", mobileDTO);
        Mobile mobile = mobileRepository.save(cuurentMobile);
        return mobileMapper.toDto(mobile);
    }



    @Override
    public MobileDTO findByMobile(String moblie) {
        return mobileMapper.toDto(mobileRepository.findMobileByMobileNumEquals(moblie));
    }

    @Override
    public List<MobileDTO> findMobileList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Page<MobileDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String mobile = formParams.get("mobile") == null ? "" : formParams.get("mobile").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();


        StringBuilder column = new StringBuilder(" select a.* ");
        StringBuilder cond = new StringBuilder(" from t_mobile a ");
        cond.append(" where 1 = 1 ");
        cond.append(" and not exists(select null from jhi_user b where a.c_mobile_num = b.c_mobile) ");

        if (StringUtils.isNotBlank(mobile)) {
            cond.append(" and c_mobile_num like '%" + mobile + "%' ");
        }
       // String orderBy = " order by c_mobile_num asc ";

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName="a."+ ReflectUtils.getColumnName(Mobile.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<MobileDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, MobileDTO.class);
        HashMap<String, Object> response = new HashMap<>();
        return page;
    }


}
