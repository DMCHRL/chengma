package com.chengma.devplatform.service;


import com.chengma.devplatform.domain.HppStrategyData;
import com.chengma.devplatform.service.dto.HppStrategyDataDTO;
import org.springframework.data.jpa.repository.Query;

public interface HppStrategyDataService {
    /**
     * 读取策略数据
     * @param account
     * @return
     */
     HppStrategyDataDTO findByAccount(String account);

    /**
     * 读取账号数据
     * @param account
     * @return
     */
    HppStrategyDataDTO loadByAccount(String account);


     HppStrategyDataDTO save(HppStrategyDataDTO hppStrategyDataDTO);


     void delete(String id);

    /**
     * 删除账号数据
     * @param account
     */
    void deleteByAccount(String account);
}
