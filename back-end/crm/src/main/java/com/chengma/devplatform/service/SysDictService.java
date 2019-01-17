package com.chengma.devplatform.service;


import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.dto.SysDictDTO;
import com.chengma.devplatform.service.dto.SysSetting;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysDict.
 *
 * @author administrator
 */
public interface SysDictService {

    /**
     * 保存
     *
     * @param sysDictDTO 字典信息
     * @return ResponseResult
     */
    ResponseResult save(SysDictDTO sysDictDTO);

    /**
     * 获取字典page。
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{group:"",dictKey:"",dictDesc:"",parentGroup:""}}
     * @return ResponseResult
     */
    ResponseResult findAll(HashMap<String, Object> params);

    /**
     * 获取字典信息
     *
     * @param params 参数：{id:"",parentGroup:""}
     * @return SysDictDTO
     */
    SysDictDTO findOne(HashMap<String, Object> params);

    /**
     * 逻辑删除
     *
     * @param id 字典id，多个id之间用“,”隔开。
     * @return ResponseResult
     */
    ResponseResult logicalDelete(String id);

    /**
     * 导出excel
     *
     * @param group       组别
     * @param dictKey     键
     * @param dictDesc    描述（值）
     * @param parentGroup 父组别
     * @return List
     */
    List<SysDictDTO> getSysDictList(String group, String dictKey, String dictDesc, String parentGroup);

    /**
     * 获取数据字典列表
     *
     * @param parentGroup 父组别
     * @return List
     */
    List<SysDictDTO> getDictList(String parentGroup);

    /**
     * 启用、禁用数据
     *
     * @param params 参数：{id:"",delFlag:""}
     * @return HashMap
     */
    HashMap<String, Object> enableSysDict(HashMap<String, Object> params);

    /**
     * 获取系统头部菜单。
     *
     * @return SysSetting
     */
    SysSetting getSysShowHeader();

    /**
     * 根据组别获取字典信息
     *
     * @param group 组别
     * @return SysDictDTO
     */
    SysDictDTO findByGroup(String group);
}
