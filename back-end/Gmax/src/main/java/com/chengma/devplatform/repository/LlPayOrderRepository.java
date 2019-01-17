package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.BindBank;
import com.chengma.devplatform.domain.LlPayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LlPayOrderRepository extends JpaRepository<LlPayOrder,String> {

    LlPayOrder findByNoOrderEquals(String noOrder);
}
