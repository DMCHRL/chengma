package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.PaymentImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentImgRepository extends JpaRepository<PaymentImg,String> {

    PaymentImg findByFlagEquals(String flag);
}
