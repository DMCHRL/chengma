package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.SysComponent;
import com.chengma.devplatform.domain.SysRoleComponent;
import com.chengma.devplatform.repository.SysComponentRepository;
import com.chengma.devplatform.repository.SysRoleComponentRepository;
import com.chengma.devplatform.service.SysComponentService;
import com.chengma.devplatform.service.dto.SysComponentDTO;
import com.chengma.devplatform.service.dto.SysRoleComponentDTO;
import com.chengma.devplatform.service.mapper.SysComponentMapper;
import com.chengma.devplatform.service.mapper.SysRoleComponentMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


/**
 * Service Implementation for managing SysComponent.
 *
 * @author administrator
 */
@Service
@Transactional
public class SysComponentServiceImpl implements SysComponentService {

    private final Logger log = LoggerFactory.getLogger(SysComponentServiceImpl.class);

    private final SysComponentRepository sysComponentRepository;

    private final SysComponentMapper sysComponentMapper;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private SysRoleComponentRepository sysRoleComponentRepository;
    @Autowired
    private SysRoleComponentMapper sysRoleComponentMapper;

    public SysComponentServiceImpl(SysComponentRepository sysComponentRepository, SysComponentMapper sysComponentMapper) {
        this.sysComponentRepository = sysComponentRepository;
        this.sysComponentMapper = sysComponentMapper;
    }

    /**
     * Save a sysComponent.
     *
     * @param sysComponentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HashMap<String, Object> save(SysComponentDTO sysComponentDTO) {
        log.debug("Request to save SysComponent : {}", sysComponentDTO);
        HashMap<String, Object> response = new HashMap<>();
        try {
            SysComponent sysComponent = sysComponentMapper.toEntity(sysComponentDTO);
            sysComponent = sysComponentRepository.save(sysComponent);
            if (sysComponent != null) {
                String adminRoleId = DevplatformConstants.ADMIN_ROLE_ID;
                int count = sysRoleComponentRepository.checkRoleComponentExists(adminRoleId, sysComponent.getId());
                if (count < 1) {
                    SysRoleComponentDTO sysRoleComponentDTO = new SysRoleComponentDTO();
                    sysRoleComponentDTO.setSysComponentId(sysComponent.getId());
                    sysRoleComponentDTO.setSysRoleId(adminRoleId);
                    SysRoleComponent sysRoleComponent = sysRoleComponentMapper.toEntity(sysRoleComponentDTO);
                    sysRoleComponentRepository.save(sysRoleComponent);
                }
            }
            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    /**
     * 根据表单id获取他下面的控件
     *
     * @param formId  表单id
     * @param visible 是否可见
     * @return List
     */
    @Override
    public List<SysComponentDTO> queryComponentPage(String formId, String visible) {
        StringBuilder column = new StringBuilder();
        column.append("select sc.c_id,sc.c_component_name,sc.c_english_name,");
        column.append("sc.c_form_id,sc.c_access_edit,sc.c_visible,sf.c_english_name form_english_name ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_component sc ");
        cond.append(" left join t_sys_form sf on sc.c_form_id = sf.c_id ");
        cond.append(" where 1=1 ");
        if (StringUtils.isNotBlank(visible)) {
            cond.append(" and sc.c_visible = '" + visible + "' ");
        }
        if (formId != null) {
            cond.append(" and sc.c_form_id = '" + formId + "' ");
        }
        return baseDao.findListBySql(column.toString(), cond.toString(), SysComponentDTO.class);
    }

    /**
     * Get one sysComponent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysComponentDTO findOne(String id) {
        log.debug("Request to get SysComponent : {}", id);
        SysComponent sysComponent = sysComponentRepository.findOne(id);
        return sysComponentMapper.toDto(sysComponent);
    }

    /**
     * Delete the  sysComponent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SysComponent : {}", id);
        sysComponentRepository.delete(id);
    }

    @Override
    public void deleteSysRoleComponent(String componentId) {
        sysRoleComponentRepository.deleteSysRoleComponent(componentId);
    }
}
