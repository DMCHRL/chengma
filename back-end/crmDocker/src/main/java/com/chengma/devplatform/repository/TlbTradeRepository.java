package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbTradeRepository extends JpaRepository<TlbTrade,String> {
    List<TlbTrade> findTlbTradesByAccountEqualsAndOrderedAndClosed(String account, String orderd, String closed);

    @Query(value="SELECT * FROM t_tlb_trade WHERE c_closed ='Y' AND c_calc_sum !='Y'",nativeQuery=true)
    List<TlbTrade> findTlbTradesWithClosePosition();
    
}

