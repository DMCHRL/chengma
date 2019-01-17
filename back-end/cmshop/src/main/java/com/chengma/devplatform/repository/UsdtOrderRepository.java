package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.Order;
import com.chengma.devplatform.domain.UsdtOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsdtOrderRepository extends JpaRepository<UsdtOrder,String> {

}
