package com.chengma.devplatform.service;


import com.chengma.devplatform.domain.HppStrategy;
import com.chengma.devplatform.service.dto.HppStrategyDTO;
import com.chengma.devplatform.service.dto.HppStrategyOrderDTO;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

public interface HppStrategyService {

    /**
     * 查询用户当前跟单策略
     * @param mobileNum
     * @return
     */
     HppStrategyDTO findByMobileNum(String mobileNum);


    Page<HppStrategyDTO> pageList(HashMap<String, Object> params);

    /**
     * 获取策略列表(app)
     * @return
     */
    List<HppStrategyDTO> findAll();

    /**
     * 获取策略列表(后台)
     * @return
     */
    Page<HppStrategyDTO> findPage(HashMap<String, Object> params);

    HppStrategyDTO findOne(String id);

    /**
     *保存策略
     * @param hppStrategyDTO
     * @return
     */
    HppStrategyDTO save(HppStrategyDTO hppStrategyDTO);

    HppStrategyDTO edit(HppStrategyDTO hppStrategyDTO);

    HashMap<String,Object> checkHppStrategyDTO(HppStrategyDTO hppStrategyDTO);

    /**
     * 处理申请信息
     * @param params
     * @return
     */
    HppStrategyDTO approve(HashMap<String, Object> params);

    HashMap<String, Object> checkApprove(HashMap<String, Object> params);

    //删除至审核
    void delete(String id);

    //彻底删除
    void deleteAll(String id);

    Long findOrderCountById(String id);

    //控制上下线
    void downOrUpLine(String id);

    //信号中断
    void outStrategy(HashMap<String,Object> params);
}
