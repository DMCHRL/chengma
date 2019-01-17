package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.HppVideoDTO;
import com.chengma.devplatform.service.dto.HppVideoTypeDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface HppVideoTypeService {

    /**
     *
     * @param params
     * @return
     */
    Page<HppVideoTypeDTO> pageList(HashMap<String, Object> params);

    HppVideoTypeDTO save(HppVideoTypeDTO hppVideoTypeDTO);

    HppVideoTypeDTO findOne(String id);

    HppVideoTypeDTO findByTypeNameAndUserId(String typeName,String userId);

    /**
     * act on app
     * @return
     */
    List<HppVideoTypeDTO> findAll();

    void delete(String id);

    /**
     * 频类别下是否有记录
     * @param typeId
     * @return
     */
    Long countByTypeId(String typeId);

    public HashMap<String, Object> checkHppVideoType(HppVideoTypeDTO hppVideoTypeDTO);

    /**
     *
     * @return
     */
    List<HppVideoTypeDTO> comboList();

    /**
     * 设置为每日推荐
     * @param id
     * @return
     */
    HppVideoTypeDTO editDayPush(String id);

    /**
     * 取消每日推荐
     * @param id
     * @return
     */
    HashMap<String, Object> cancelDayPush(String id);

    List<HppVideoTypeDTO> findDayPush();

    /**
     * 检查每日推荐
     * @return
     */
    HashMap<String, Object> checkDayPush(String id);



}
