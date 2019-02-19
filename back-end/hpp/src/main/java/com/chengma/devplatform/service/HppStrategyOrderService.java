package com.chengma.devplatform.service;


import com.chengma.devplatform.domain.HppStrategyOrder;
import com.chengma.devplatform.service.dto.HppStrategyDTO;
import com.chengma.devplatform.service.dto.HppStrategyOrderDTO;
import com.chengma.devplatform.service.dto.HppStrategyOrderDTO;
import javafx.beans.binding.ObjectExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;

public interface HppStrategyOrderService {

    /**
     * 跟单(app)
     * @param hppStrategyOrderDTO
     * @return
     */
    HppStrategyOrderDTO follow(HppStrategyOrderDTO hppStrategyOrderDTO);

    /**
     * 解绑(app)
     * @param hppStrategyOrderDTO
     * @return
     */
    HppStrategyOrderDTO relieve(HppStrategyOrderDTO hppStrategyOrderDTO);

    HashMap<String, Object> checkApply(HppStrategyOrderDTO hppStrategyOrderDTO,String type);

    /**
     * 处理申请信息
     * @param params
     * @return
     */
    HppStrategyOrderDTO approve(HashMap<String, Object> params);

    HashMap<String, Object> checkApprove(HashMap<String, Object> params);


    /**
     * 根据strategyId获取有效记录数
     * @param strategyId
     * @return
     */
     Long findByStrategyId(String strategyId);

    Page<HppStrategyOrderDTO> pageList(HashMap<String, Object> params);

    public boolean existFollow(String account,String password);


    /**
     * 根据策略Id获取跟单着具体数据
     * @param params
     * @return
     */
    Page<HppStrategyOrderDTO> findList(HashMap<String, Object> params);

    /**
     * 检查手机号是否有跟单
     * @param mail
     * @return
     */
    boolean existFollowByMail(String mail);

    void setMt4Status(HashMap<String,Object> params);
}
