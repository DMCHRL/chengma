package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.dao.BaseDao;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.domain.SysForm;
import com.suitong.devplatform.domain.SysRoleForm;
import com.suitong.devplatform.repository.SysComponentRepository;
import com.suitong.devplatform.repository.SysFormRepository;
import com.suitong.devplatform.repository.SysRoleComponentRepository;
import com.suitong.devplatform.repository.SysRoleFormRepository;
import com.suitong.devplatform.service.SysFormService;
import com.suitong.devplatform.service.dto.SysComponentDTO;
import com.suitong.devplatform.service.dto.SysFormDTO;
import com.suitong.devplatform.service.dto.SysRoleFormDTO;
import com.suitong.devplatform.service.mapper.SysFormMapper;
import com.suitong.devplatform.service.mapper.SysRoleFormMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


/**
 * Service Implementation for managing SysForm.
 */
@Service
@Transactional
public class SysFormServiceImpl implements SysFormService {

    private final Logger log = LoggerFactory.getLogger(SysFormServiceImpl.class);

    private final SysFormRepository sysFormRepository;

    private final SysFormMapper sysFormMapper;

    @Autowired
    private SysRoleFormRepository sysRoleFormRepository;
    @Autowired
    private SysRoleComponentRepository sysRoleComponentRepository;
    @Autowired
    private SysRoleFormMapper sysRoleFormMapper;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private SysComponentRepository sysComponentRepository;

    public SysFormServiceImpl(SysFormRepository sysFormRepository, SysFormMapper sysFormMapper) {
        this.sysFormRepository = sysFormRepository;
        this.sysFormMapper = sysFormMapper;
    }

    /**
     * Save a sysForm.
     *
     * @param sysFormDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HashMap<String,Object> save(SysFormDTO sysFormDTO) {
        log.debug("Request to save SysForm : {}", sysFormDTO);
        HashMap<String,Object> response = new HashMap<>();
        try {
            SysForm sysForm = sysFormMapper.toEntity(sysFormDTO);
            sysForm = sysFormRepository.save(sysForm);
            if(sysForm != null){
                SysRoleFormDTO sysRoleFormDTO = new SysRoleFormDTO();
                sysRoleFormDTO.setSysFormId(sysForm.getId());
                sysRoleFormDTO.setSysRoleId((long) 3);
                SysRoleForm sysRoleForm = sysRoleFormMapper.toEntity(sysRoleFormDTO);
                sysRoleFormRepository.save(sysRoleForm);
            }
            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    /**
     *  Get all the sysForms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysForms");
        return sysFormRepository.findAll(pageable)
            .map(sysFormMapper::toDto);
    }

    //根据菜单id获取他下面的表单
    @Override
    public List<SysFormDTO> queryFormPage(String menuIds,String visible){
        StringBuilder column = new StringBuilder();
        column.append("select sf.id,sf.c_form_name form_name,sf.c_english_name english_name,");
        column.append("sf.i_menu_id menu_id,sf.c_access_edit access_edit,sf.c_visible visible ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_form sf where 1=1 ");
        if(StringUtils.isNotBlank(visible)){
            cond.append(" and sf.c_visible = "+ visible);
        }
        if(StringUtils.isNotBlank(menuIds)){
            cond.append(" and sf.i_menu_id in ("+ menuIds + ")");
        }
        return baseDao.findListBySql(column.toString(),cond.toString(), SysFormDTO.class);
    }

    /**
     *  Get one sysForm by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysFormDTO findOne(Long id) {
        log.debug("Request to get SysForm : {}", id);
        SysForm sysForm = sysFormRepository.findOne(id);
        return sysFormMapper.toDto(sysForm);
    }

    /**
     *  Delete the  sysForm by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysForm : {}", id);
        sysFormRepository.delete(id);
        sysComponentRepository.deleteComponentByFormId(id);
    }

    @Override
    public void deleteSysRoleForm(Long formId){
        sysRoleFormRepository.deleteSysRoleForm(formId);
        List<SysComponentDTO> componentDTOList = queryComponentByForm(formId);
        if(componentDTOList != null && componentDTOList.size()>0){
            for(SysComponentDTO componentDTO : componentDTOList){
                sysRoleComponentRepository.deleteSysRoleComponent(componentDTO.getId());
            }
        }
    }

    public List<SysComponentDTO> queryComponentByForm(Long formId){
        StringBuilder column = new StringBuilder();
        column.append("select sc.id ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_component sc where 1=1 ");
        cond.append(" and sc.i_form_id = "+ formId);
        return baseDao.findListBySql(column.toString(),cond.toString(), SysComponentDTO.class);
    }

    @Override
    public int checkFormEnglishName(HashMap<String, Object> params){
        String formEnglishName = params.get("formEnglishName") == null ? "" : params.get("formEnglishName").toString();
        int count = 0;
        if(StringUtils.isNotBlank(formEnglishName)){
            count = sysFormRepository.checkFormEnglishName(formEnglishName);
        }
        return count;
    }
}
