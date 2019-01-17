package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.UnionPayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnionPayInfoRepository extends JpaRepository<UnionPayInfo,String> {

    /**
     * 根据订单号获取实体
     * @param merchantOrderNo
     * @return
     */
    public UnionPayInfo findByMerchantOrderNoEquals(String merchantOrderNo);
}
