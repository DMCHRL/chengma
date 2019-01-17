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

    @Query(value="SELECT * FROM t_tlb_trade a WHERE a.c_closed ='Y' AND a.c_calc_sum !='Y' and exists(select null from t_tlb_account as b where a.c_account = b.c_account and c_group = 'TXA3') limit 1, 100",nativeQuery=true)
    List<TlbTrade> findTlbTradesWithClosePosition();
    
}

