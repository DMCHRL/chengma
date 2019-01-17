package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppStrategyTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppStrategyTradeRepository extends JpaRepository<HppStrategyTrade,String> {

}
