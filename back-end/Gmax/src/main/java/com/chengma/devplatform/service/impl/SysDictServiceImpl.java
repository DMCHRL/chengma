package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.SysDict;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.SysDictRepository;
import com.chengma.devplatform.service.SysDictService;
import com.chengma.devplatform.service.SysOperateLogService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.SysDictDTO;
import com.chengma.devplatform.service.dto.SysSetting;
import com.chengma.devplatform.service.mapper.SysDictMapper;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
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
 *
 * @author administrator
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
    @Autowired
    private DBService dbService;

    public SysDictServiceImpl(SysDictRepository sysDictRepository, SysDictMapper sysDictMapper) {
        this.sysDictRepository = sysDictRepository;
        this.sysDictMapper = sysDictMapper;
    }

    /**
     * 保存
     *
     * @param sysDictDTO 字典信息
     * @return ResponseResult
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
     * 获取字典page。
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{group:"",dictKey:"",dictDesc:"",parentGroup:""}}
     * @return ResponseResult
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
     * 获取字典信息
     *
     * @param params 参数：{id:"",parentGroup:""}
     * @return SysDictDTO
     */
    @Override
    @Transactional(readOnly = true)
    public SysDictDTO findOne(HashMap<String, Object> params) {
        String id = params.get("id") == null ? "" : params.get("id").toString();
        String parentGroup = params.get("parentGroup") == null ? "" : params.get("parentGroup").toString();
        SysDictDTO dto;
        if (StringUtils.isNotBlank(id)) {
            SysDict sysDict = sysDictRepository.findOne(id);
            dto = sysDictMapper.toDto(sysDict);
        } else {
            dto = findByGroup(parentGroup);
        }
        return dto;
    }

    /**
     * 根据组别获取字典信息
     *
     * @param group 组别
     * @return SysDictDTO
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
     * 从数据库查找重复数据
     *
     * @param sysDictDTO 字典信息
     * @return SysDictDTO
     */
    @Transactional(readOnly = true)
    public SysDictDTO duplicateCode(SysDictDTO sysDictDTO) {
        StringBuilder column = getColumn();

        StringBuilder cond = new StringBuilder(" from t_sys_dict a where 1 = 1 ");
        cond.append(" and (a.c_group = '" + sysDictDTO.getGroup() + "' or ");
        cond.append(" (a.c_dict_key = '" + sysDictDTO.getDictKey() + "' and a.c_parent_group = '" + sysDictDTO.getParentGroup() + "')) ");
        if (null != sysDictDTO.getId()) {
            cond.append(" and a.c_id <> '" + sysDictDTO.getId() + "' ");
        }
        List<SysDictDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), SysDictDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 逻辑删除(改为物理删)
     *
     * @param ids 字典id，多个id之间用“,”隔开。
     * @return ResponseResult
     */
    @Override
    public ResponseResult logicalDelete(String ids) {
        ResponseResult response = new ResponseResult();

        //添加日志
        User user = userService.getUserWithAuthorities();
        ids = ids.replace("'", "");//去掉单引号
        String[] idArray = ids.split(",");
        HashMap<String, Object> params = new HashMap<>();
        for (String id : idArray) {
            params.put("id", id);
            SysDictDTO sysDictDTO = findOne(params);
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "删除数据字典：" + sysDictDTO.getGroup());
        }

        //删除
        StringBuilder column = new StringBuilder(" delete from t_sys_dict a where " + dbService.inSql("a.c_id", ids) + " ");
        jdbcTemplate.execute(column.toString());

        response.setStatusCode(ResponseResult.SUCCESS_CODE);
        return response;
    }


    /**
     * 导出excel
     *
     * @param group       组别
     * @param dictKey     键
     * @param dictDesc    描述（值）
     * @param parentGroup 父组别
     * @return List
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

    /**
     * 需要查询的字段，默认全查，默认表的别名是a
     *
     * @return StringBuilder
     */
    public StringBuilder getColumn() {
        StringBuilder column = new StringBuilder(" select a.c_id ");
        column.append(" ,a.c_group,a.c_dict_key,a.c_dict_desc,a.c_del_flag ");
        column.append(" ,a.c_parent_group,a.i_sort,a.c_create_by,a.d_create_at ");
        column.append(" ,a.c_last_update_by,a.d_last_update_at,a.c_dict_shop ");
        column.append(" ,a.c_dict_key status,a.c_group show_name ");
        return column;
    }

    /**
     * 获取数据字典列表
     *
     * @param parentGroup 父组别
     * @return List
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
     *
     * @param params 参数：{id:"",delFlag:""}
     * @return HashMap
     */
    @Override
    public HashMap<String, Object> enableSysDict(HashMap<String, Object> params) {
        String id = params.get("id") == null ? "" : params.get("id").toString();
        String delFlag = params.get("delFlag") == null ? null : params.get("delFlag").toString();
        StringBuilder column = new StringBuilder(" update t_sys_dict set c_del_flag = " + delFlag + " ");
        column.append(" where c_id = '" + id + "' ");
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

    /**
     * 获取系统头部菜单。
     *
     * @return SysSetting
     */
    @Override
    public SysSetting getSysShowHeader() {
        StringBuilder column = new StringBuilder();
        column.append("select ss.* ");
        StringBuilder cond = new StringBuilder();
        cond.append(" from t_sys_setting ss where 1=1 and ss.c_status = '1' ");
        List<SysSetting> list = baseDao.findListBySql(column.toString(), cond.toString(), SysSetting.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}