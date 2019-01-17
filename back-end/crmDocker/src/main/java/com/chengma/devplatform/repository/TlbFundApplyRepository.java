package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.TlbFundApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbFundApplyRepository extends JpaRepository<TlbFundApply,String> {

    List<TlbFundApply> findTlbFundAppliesByAccountEqualsAndFundTypeAndStatusEquals(String account, String fundType, String status);

    List<TlbFundApply> findTlbFundAppliesByAccountEqualsAndStatus(String account, String status);

    
}
