package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppIntegral;
import com.chengma.devplatform.domain.HppStrategy;
import com.chengma.devplatform.domain.HppStrategyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppStrategyRepository extends JpaRepository<HppStrategy,String> {

    public HppStrategy findByStrategyNameEqualsAndStatusEquals(String strategyName,String status);
}
