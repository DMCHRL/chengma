package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.SysForm;
import com.chengma.devplatform.domain.SysRoleForm;
import com.chengma.devplatform.repository.SysComponentRepository;
import com.chengma.devplatform.repository.SysFormRepository;
import com.chengma.devplatform.repository.SysRoleComponentRepository;
import com.chengma.devplatform.repository.SysRoleFormRepository;
import com.chengma.devplatform.service.SysFormService;
import com.chengma.devplatform.service.dto.SysComponentDTO;
import com.chengma.devplatform.service.dto.SysFormDTO;
import com.chengma.devplatform.service.dto.SysRoleFormDTO;
import com.chengma.devplatform.service.mapper.SysFormMapper;
import com.chengma.devplatform.service.mapper.SysRoleFormMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


/**
 * Service Implementation for managing SysForm.
 *
 * @author administrator
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
    @Autowired
    private DBService dbService;

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
    public HashMap<String, Object> save(SysFormDTO sysFormDTO) {
        log.debug("Request to save SysForm : {}", sysFormDTO);
        HashMap<String, Object> response = new HashMap<>();
        try {
            SysForm sysForm = sysFormMapper.toEntity(sysFormDTO);
            sysForm = sysFormRepository.save(sysForm);
            if(sysForm != null){
                String adminRoleId = DevplatformConstants.ADMIN_ROLE_ID;
                int count = sysRoleFormRepository.checkRoleFormExists(adminRoleId ,sysForm.getId());
                if(count < 1) {
                    SysRoleFormDTO sysRoleFormDTO = new SysRoleFormDTO();
                    sysRoleFormDTO.setSysFormId(sysForm.getId());
                    sysRoleFormDTO.setSysRoleId(adminRoleId);
                    SysRoleForm sysRoleForm = sysRoleFormMapper.toEntity(sysRoleFormDTO);
                    sysRoleFormRepository.save(sysRoleForm);
                }
            }
            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    //根据菜单id获取他下面的表单
    @Override
    public List<SysFormDTO> queryFormPage(String menuIds, String visible) {
        StringBuilder column = new StringBuilder();
        column.append("select sf.c_id,sf.c_form_name form_name,sf.c_english_name english_name,");
        column.append("sf.c_menu_id menu_id,sf.c_access_edit access_edit,sf.c_visible visible ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_form sf where 1=1 ");
        if (StringUtils.isNotBlank(visible)) {
            cond.append(" and sf.c_visible = '" + visible + "' ");
        }
        if (StringUtils.isNotBlank(menuIds)) {
            cond.append(" and " + dbService.inSql("sf.c_menu_id", menuIds));
        }
        return baseDao.findListBySql(column.toString(), cond.toString(), SysFormDTO.class);
    }

    /**
     * Get one sysForm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysFormDTO findOne(String id) {
        log.debug("Request to get SysForm : {}", id);
        SysForm sysForm = sysFormRepository.findOne(id);
        return sysFormMapper.toDto(sysForm);
    }

    /**
     * Delete the  sysForm by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SysForm : {}", id);
        sysFormRepository.delete(id);
        sysComponentRepository.deleteComponentByFormId(id);
    }

    /**
     * 刪除角色表单
     *
     * @param formId 表单id
     */
    @Override
    public void deleteSysRoleForm(String formId) {
        sysRoleFormRepository.deleteSysRoleForm(formId);
        List<SysComponentDTO> componentDTOList = queryComponentByForm(formId);
        if (componentDTOList != null && componentDTOList.size() > 0) {
            for (SysComponentDTO componentDTO : componentDTOList) {
                sysRoleComponentRepository.deleteSysRoleComponent(componentDTO.getId());
            }
        }
    }

    /**
     * 刪除表单下的组件
     *
     * @param formId 表单id
     * @return List
     */
    public List<SysComponentDTO> queryComponentByForm(String formId) {
        StringBuilder column = new StringBuilder();
        column.append("select sc.c_id ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_component sc where 1=1 ");
        cond.append(" and sc.c_form_id = '" + formId + "' ");
        return baseDao.findListBySql(column.toString(), cond.toString(), SysComponentDTO.class);
    }

    /**
     * 检查表单英文名。
     *
     * @param params 参数：{formEnglishName:""}
     * @return 数据库里同名formEnglishName的数量
     */
    @Override
    public int checkFormEnglishName(HashMap<String, Object> params) {
        String formEnglishName = params.get("formEnglishName") == null ? "" : params.get("formEnglishName").toString();
        int count = 0;
        if (StringUtils.isNotBlank(formEnglishName)) {
            count = sysFormRepository.checkFormEnglishName(formEnglishName);
        }
        return count;
    }
}
