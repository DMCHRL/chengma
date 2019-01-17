package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppStrategy;
import com.chengma.devplatform.domain.HppStrategyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppStrategyDataRepository extends JpaRepository<HppStrategyData,String> {

    /**
     * 读取策略数据
     * @param account
     * @return
     *//*
    @Query(value="SELECT sd.*, s.c_strategy_text from t_hpp_strategy_data sd join t_hpp_strategy s on sd.c_account = s.c_account and sd.c_account=?1",nativeQuery = true)
    public HppStrategyData findByAccount(String account);*/

    void deleteByAccountEquals(String account);
}
