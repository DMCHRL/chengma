package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankInfoRepository extends JpaRepository<BankInfo,String> {

    @Query(value = "select * from t_bank_info where c_user_id =?1",nativeQuery = true)
    BankInfo findByUserId(String userId);
}
