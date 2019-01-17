package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbAccountControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbAccountControlRepository extends JpaRepository<TlbAccountControl,String> {

    @Query(value="select * from t_tlb_account_control where user_id=?1",nativeQuery = true)
    public List<TlbAccountControl>  findByUserId(String userId);

    @Query(value="select * from t_tlb_account_control where user_id=?1 and c_account=?2",nativeQuery = true)
    public TlbAccountControl findByUserIdAndAccount(String userId,String account);

}
