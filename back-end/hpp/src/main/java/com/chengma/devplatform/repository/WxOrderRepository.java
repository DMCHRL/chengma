package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.HppNotice;
import com.chengma.devplatform.domain.WxOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WxOrderRepository extends JpaRepository<WxOrder,String> {

    WxOrder findByOutTradeNoEquals(String outTradeNo);

    /**
     * 防止重复提交订单
     * @param bodyId
     * @param status
     * @return
     */
    WxOrder findByBodyIdEqualsAndStatusEqualsAndObjectEquals(String bodyId,String status,String object);

}
