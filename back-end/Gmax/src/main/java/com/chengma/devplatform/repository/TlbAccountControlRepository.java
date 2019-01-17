package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbAccountControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbAccountControlRepository extends JpaRepository<TlbAccountControl,String> {



    @Query(value="select * from t_tlb_account_control where user_id=?1 and c_account=?2",nativeQuery = true)
    public TlbAccountControl findByUserIdAndAccount(String userId, String account);

    /**
     * 更改密码时，删除对应账号关联
     * @param account
     */
    @Modifying
    @Query(value="DELETE from t_tlb_account_control where c_account=?1 and c_trade=?2 ",nativeQuery = true)
    public void deleteByAccount(String account, String trade);

    public TlbAccountControl findByUserIdEqualsAndAccountEquals(String userId, String account);

    public TlbAccountControl findByUserIdEqualsAndAccountEqualsAndTradeEquals(String userId, String account, String trade);

}
