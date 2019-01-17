package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.UsdtOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsdtOrderRepository extends JpaRepository<UsdtOrder,String> {

    UsdtOrder findByOrderIdEquals(String orderId);

}
