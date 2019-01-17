package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbTradeBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbTradeBackRepository extends JpaRepository<TlbTradeBack,String> {
    List<TlbTradeBack> findTlbTradesByAccountEqualsAndOrderedAndClosed(String account, String orderd, String closed);
}

