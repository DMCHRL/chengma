package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.SysFormDTO;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysForm.
 *
 * @author administrator
 */
public interface SysFormService {

    /**
     * Save a sysForm.
     *
     * @param sysFormDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String, Object> save(SysFormDTO sysFormDTO);

    /**
     * 根据选中的菜单，获取他下面的表单。
     *
     * @param menuIds 菜单id
     * @param visible 是否可见
     * @return List
     */
    List<SysFormDTO> queryFormPage(String menuIds, String visible);

    /**
     * Get the "id" sysForm.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SysFormDTO findOne(String id);

    /**
     * Delete the "id" sysForm.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * 刪除角色表单
     *
     * @param id 表单id
     */
    void deleteSysRoleForm(String id);

    /**
     * 检查表单英文名。
     *
     * @param params 参数：{formEnglishName:""}
     * @return 数据库里同名formEnglishName的数量
     */
    int checkFormEnglishName(HashMap<String, Object> params);
}
