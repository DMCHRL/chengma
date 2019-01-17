package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

    Order findByNoOrderEquals(String noOrder);

    List<Order> findByUserIdEquals(String userId);
}
