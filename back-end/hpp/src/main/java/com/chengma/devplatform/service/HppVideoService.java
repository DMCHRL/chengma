package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.HppVideoDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface HppVideoService {

    /**
     *
     * @param params
     * @return
     */
    Page<HppVideoDTO> pageList(HashMap<String, Object> params);

    HppVideoDTO save(HppVideoDTO hppVideoDTO);

    HppVideoDTO createHppVideoDTO(HppVideoDTO hppVideoDTO);

    HppVideoDTO findOne(String id);

    HppVideoDTO showOnApp(String id);

    /**
     * 根据videoTypeId获取记录数
     * @param typeId
     * @return
     */
    Long countByTypeId(String typeId);


    void delete(String id);

   /* void deleteByTypeId(String typeId);*/

    /**
     * 根据类别id和视频名称查询
     * @param typeId
     * @param videoName
     * @return
     */
    HppVideoDTO findByTypeIdAndVideoName(String typeId, String videoName);

    /**
     * 根据类型id返回当前最大期数
     * @param typeId
     * @return
     */
    public Integer maxPhase(String typeId);

    public HashMap<String, Object> checkHppVideo(HppVideoDTO hppVideoDTO);

    /**
     *加载积分兑换列表
     * @return
     */
    public List<HppVideoDTO> loadIntegral();

    /**兑换明细列表
     * @return
     */
    public List<HppVideoDTO> loadExchange(String mobileNum);

    /**
     * act on app
     * @param typeId
     * @return
     */
    public List<HppVideoDTO> findByTypeId(String typeId);

}
