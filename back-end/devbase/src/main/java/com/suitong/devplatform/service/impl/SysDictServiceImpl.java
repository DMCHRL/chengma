package com.suitong.devplatform.service.impl;


import com.suitong.devplatform.common.constant.DevplatformConstants;
import com.suitong.devplatform.common.dao.BaseDao;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.page.PageCommon;
import com.suitong.devplatform.domain.SysDict;
import com.suitong.devplatform.domain.User;
import com.suitong.devplatform.repository.SysDictRepository;
import com.suitong.devplatform.service.SysDictService;
import com.suitong.devplatform.service.SysOperateLogService;
import com.suitong.devplatform.service.UserService;
import com.suitong.devplatform.service.dto.SysDictDTO;
import com.suitong.devplatform.service.dto.SysSetting;
import com.suitong.devplatform.service.mapper.SysDictMapper;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Service Implementation for managing SysDict.
 */
@Service
@Transactional
public class SysDictServiceImpl implements SysDictService {

    private final Logger log = LoggerFactory.getLogger(SysDictServiceImpl.class);

    private final SysDictRepository sysDictRepository;

    private final SysDictMapper sysDictMapper;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SysOperateLogService sysOperateLogService;

    public SysDictServiceImpl(SysDictRepository sysDictRepository, SysDictMapper sysDictMapper) {
        this.sysDictRepository = sysDictRepository;
        this.sysDictMapper = sysDictMapper;
    }

    /**
     * Save a sysDict.
     */
    @Override
    public ResponseResult save(SysDictDTO sysDictDTO) {
        ResponseResult response = new ResponseResult<>();
        SysDictDTO dto = duplicateCode(sysDictDTO);

        String group = sysDictDTO.getGroup();
        String dictKey = sysDictDTO.getDictKey();
        String dictDesc = sysDictDTO.getDictDesc();
        if (null == group || "".equals(group)) {
            response.setMsgCode(DevplatformConstants.SYSDICT_INVALID_GROUP);
            return response;
        } else if (null == dictKey || "".equals(dictKey)) {
            response.setMsgCode(DevplatformConstants.SYSDICT_INVALID_KEY);
            return response;
        } else if (null == dictDesc || "".equals(dictDesc)) {
            response.setMsgCode(DevplatformConstants.SYSDICT_INVALID_DESC);
            return response;
        }

        if (null != dto) {
            if (dto.getGroup().equalsIgnoreCase(group)) {
                response.setMsgCode(DevplatformConstants.SYSDICT_DUPLICATE_GROUP);
            } else if (dto.getDictKey().equals(dictKey)) {
                response.setMsgCode(DevplatformConstants.SYSDICT_DUPLICATE_KEY);
            }
        } else {
            User user = userService.getUserWithAuthorities();
            if (sysDictDTO.getId() != null) { //更新
                sysDictDTO.setLastUpdateBy(user.getId());
                sysDictDTO.setLastUpdateAt(new Date());
            } else { //新增
                sysDictDTO.setDelFlag("1");
                sysDictDTO.setCreateBy(user.getId());
                sysDictDTO.setCreateAt(new Date());
            }
            SysDict sysDict = null;
            try {
                sysDict = sysDictMapper.toEntity(sysDictDTO);
                sysDictRepository.save(sysDict);

                if (sysDictDTO.getId() != null) {
                    sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "修改数据字典：" + sysDictDTO.getGroup());
                } else {
                    sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "新增数据字典：" + sysDictDTO.getGroup());
                }
                response.setStatusCode(ResponseResult.SUCCESS_CODE);
            } catch (Exception e) {
                response.setMsgCode(DevplatformConstants.ERROR_ERRORMSG);
            }
        }
        return response;
    }

    /**
     * findAll
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseResult findAll(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String group = formParams.get("group") == null ? "" : formParams.get("group").toString();
        String dictKey = formParams.get("dictKey") == null ? "" : formParams.get("dictKey").toString();
        String dictDesc = formParams.get("dictDesc") == null ? "" : formParams.get("dictDesc").toString();
        String parentGroup = formParams.get("parentGroup") == null ? "" : formParams.get("parentGroup").toString();

        StringBuilder column = getColumn();
        column.append(" ,case a.c_parent_group when '0' then '' else b.c_group end parent_name ");
        column.append(" ,(select count(1) from t_sys_dict c where c.c_parent_group = a.c_group) children_count ");

        StringBuilder cond = new StringBuilder(" from t_sys_dict a ");
        cond.append(" left join t_sys_dict b on a.c_parent_group = b.c_group where 1 = 1 ");
        if (StringUtils.isNotBlank(group)) {
            cond.append(" and a.c_group like '%" + group + "%'");
        }
        if (StringUtils.isNotBlank(dictKey)) {
            cond.append(" and a.c_dict_key like '%" + dictKey + "%'");
        }
        if (StringUtils.isNotBlank(dictDesc)) {
            cond.append(" and a.c_dict_desc like '%" + dictDesc + "%'");
        }
        if (StringUtils.isNotBlank(parentGroup)) {
            cond.append(" and a.c_parent_group = '" + parentGroup + "'");
        } else {
            cond.append(" and a.c_parent_group = '0' ");
        }
        cond.append(" order by a.i_sort ");
        Page<SysDictDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, SysDictDTO.class);

        ResponseResult response = new ResponseResult();
        HashMap<String, Object> data = new HashMap<>();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sysDict/list");
        data.put("link", headers.get(HttpHeaders.LINK).get(0));
        data.put("total_count", headers.get("X-Total-Count").get(0));
        data.put("headers", headers);
        data.put("list", page.getContent());
        data.put("page_number", page_number);
        data.put("page_size", page_size);

        response.setStatusCode(ResponseResult.SUCCESS_CODE);
        response.setData(data);
        return response;
    }

    /**
     * Get one sysDict by id.
     */
    @Override
    @Transactional(readOnly = true)
    public SysDictDTO findOne(HashMap<String, Object> params) {
        Long id = params.get("id") == null ? null : Long.valueOf(params.get("id").toString());
        String parentGroup = params.get("parentGroup") == null ? "" : params.get("parentGroup").toString();
        SysDictDTO dto = new SysDictDTO();
        if (null != id && id > 0) {
            SysDict sysDict = sysDictRepository.findOne(id);
            dto = sysDictMapper.toDto(sysDict);
            if (null != sysDict.getDictParent()) {
                SysDict parentSysDict = sysDictRepository.findOne(sysDict.getDictParent());
                if (null != parentSysDict) {
                    dto.setParentName(parentSysDict.getDictItem());
                }
            }
        } else {
            dto = findByGroup(parentGroup);
        }
        return dto;
    }

    /**
     * findByGroup
     */
    @Transactional(readOnly = true)
    public SysDictDTO findByGroup(String group) {
        StringBuilder column = getColumn();
        column.append(" ,(select max(b.i_sort)+1 from t_sys_dict b where b.c_parent_group = '" + group + "') max_sort ");
        StringBuilder cond = new StringBuilder(" from t_sys_dict a where a.c_group = '" + group + "' ");
        List<SysDictDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), SysDictDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * duplicateCode
     */
    @Transactional(readOnly = true)
    public SysDictDTO duplicateCode(SysDictDTO sysDictDTO) {
        StringBuilder column = getColumn();

        StringBuilder cond = new StringBuilder(" from t_sys_dict a where 1 = 1 ");
        cond.append(" and (a.c_group = '" + sysDictDTO.getGroup() + "' or ");
        cond.append(" (a.c_dict_key = '" + sysDictDTO.getDictKey() + "' and a.c_parent_group = '" + sysDictDTO.getParentGroup() + "')) ");
        if (null != sysDictDTO.getId()) {
            cond.append(" and a.id <> '" + sysDictDTO.getId() + "' ");
        }
        List<SysDictDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), SysDictDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * logicalDelete
     */
    @Override
    public ResponseResult logicalDelete(String ids) {
        ResponseResult response = new ResponseResult();
        StringBuilder column = new StringBuilder(" update t_sys_dict a set a.c_del_flag = '0' where a.id in (" + ids + ") ");
        jdbcTemplate.execute(column.toString());

        User user = userService.getUserWithAuthorities();
        ids = ids.replace("'", "");//去掉单引号
        String[] idArray = ids.split(",");
        HashMap<String, Object> params = new HashMap<>();
        for (int i = 0; i < idArray.length; i++) {
            Long id = Long.valueOf(idArray[i]);
            params.put("id", id);
            SysDictDTO sysDictDTO = findOne(params);
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "删除数据字典：" + sysDictDTO.getGroup());
        }
        response.setStatusCode(ResponseResult.SUCCESS_CODE);
        return response;
    }


    /**
     * 导出excel
     */
    @Override
    public List<SysDictDTO> getSysDictList(String group, String dictKey, String dictDesc, String parentGroup) {
        StringBuilder column = getColumn();
        column.append(" ,case a.c_parent_group when '0' then '' else b.c_group end parent_name ");
        column.append(" ,(select count(1) from t_sys_dict c where c.c_parent_group = a.c_group) children_count ");

        StringBuilder cond = new StringBuilder(" from t_sys_dict a ");
        cond.append(" left join t_sys_dict b on a.c_parent_group = b.c_group where 1 = 1 ");
        if (StringUtils.isNotBlank(group)) {
            cond.append(" and a.c_group like '%" + group + "%'");
        }
        if (StringUtils.isNotBlank(dictKey)) {
            cond.append(" and a.c_dict_key like '%" + dictKey + "%'");
        }
        if (StringUtils.isNotBlank(dictDesc)) {
            cond.append(" and a.c_dict_desc like '%" + dictDesc + "%'");
        }
        if (StringUtils.isNotBlank(parentGroup)) {
            cond.append(" and a.c_parent_group = '" + parentGroup + "'");
        } else {
            cond.append(" and a.c_parent_group = '0' ");
        }
        cond.append(" order by a.i_sort ");
        List<SysDictDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), SysDictDTO.class);
        return list;
    }

    @Override
    public SysDictDTO getSysDictShowHeader() {
        return findByGroup("showHeader");
    }

    /**
     * 需要查询的字段，默认全查，默认表的别名是a
     */
    public StringBuilder getColumn() {
        StringBuilder column = new StringBuilder(" select a.id ");
        column.append(" ,a.c_group,a.c_dict_key,a.c_dict_desc,a.c_del_flag ");
        column.append(" ,a.c_parent_group,a.i_sort,a.i_create_by,a.d_create_at ");
        column.append(" ,a.i_last_update_by,a.d_last_update_at,a.c_dict_shop ");
        column.append(" ,a.c_dict_key status,a.c_group show_name ");
        return column;
    }

    /**
     * 获取数据字典列表
     */
    @Override
    public List<SysDictDTO> getDictList(String parentGroup) {
        StringBuilder column = getColumn();
        StringBuilder cond = new StringBuilder(" from t_sys_dict a where a.c_del_flag = '1' and a.c_parent_group = '" + parentGroup + "' ");
        cond.append(" order by a.i_sort ");
        List<SysDictDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), SysDictDTO.class);
        return list;
    }

    /**
     * 启用、禁用数据
     */
    @Override
    public HashMap<String, Object> enableSysDict(HashMap<String, Object> params) {
        Long id = params.get("id") == null ? null : Long.valueOf(params.get("id").toString());
        String delFlag = params.get("delFlag") == null ? null : params.get("delFlag").toString();
        StringBuilder column = new StringBuilder(" update t_sys_dict set c_del_flag = " + delFlag + " ");
        column.append(" where id = " + id + " ");
        jdbcTemplate.execute(column.toString());

        User user = userService.getUserWithAuthorities();

        SysDictDTO sysDictDTO = findOne(params);
        if ("1".equals(delFlag)) {
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "启用数据字典：" + sysDictDTO.getGroup());
        } else {
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "禁用数据字典：" + sysDictDTO.getGroup());
        }
        return null;
    }

    @Override
    public SysSetting getSysShowHeader() {
        StringBuilder column = new StringBuilder();
        column.append("select ss.* ");
        StringBuilder cond = new StringBuilder();
        cond.append(" from t_sys_setting ss where 1=1 and ss.c_status = '1' ");
        List<SysSetting> list = baseDao.findListBySql(column.toString(), cond.toString(), SysSetting.class);
        if (list != null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

}