package com.suitong.devplatform.service;


import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.dto.SysDictDTO;
import com.suitong.devplatform.service.dto.SysSetting;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysDict.
 */
public interface SysDictService {

    /**
     * Save a sysDict.
     */
    ResponseResult save(SysDictDTO sysDictDTO);

    /**
     * findAll
     */
    ResponseResult findAll(HashMap<String, Object> params);

    /**
     * Get the "id" sysDict.
     */
    SysDictDTO findOne(HashMap<String, Object> params);

    /**
     * logicalDelete
     */
    ResponseResult logicalDelete(String id);

    /**
     * 导出excel
     */
    List<SysDictDTO> getSysDictList(String group, String dictKey, String dictDesc, String parentGroup);

    SysDictDTO getSysDictShowHeader();

    /**
     * 获取数据字典列表
     */
    List<SysDictDTO> getDictList(String parentGroup);

    /**
     * 启用、禁用数据
     */
    HashMap<String, Object> enableSysDict(HashMap<String, Object> params);

    SysSetting getSysShowHeader();
}
