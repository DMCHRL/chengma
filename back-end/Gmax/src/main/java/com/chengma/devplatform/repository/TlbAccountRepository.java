package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbAccountRepository extends JpaRepository<TlbAccount,String> {
    TlbAccount findOneByAccountEquals(String account);

    @Query(value="SELECT * FROM t_tlb_account WHERE c_account=?1",nativeQuery = true)
    public TlbAccount findByAccountId(String accountId);

    @Query(value="select * from t_tlb_account where c_account=?1 and c_mt4_password=?2",nativeQuery = true)
    public TlbAccount findByAccountAndPassword(String account, String password);

    @Query(value="select * from t_tlb_account where c_user_id=?1 and ifnull(i_balance, 0) = 0 ",nativeQuery = true)
    public List<TlbAccount> findEmptyAccounts(String userId);

    /**
     * 根據觀摩密碼找賬戶
     * @param account
     * @param seePassword
     * @return
     */
    public TlbAccount findByAccountEqualsAndSeePasswordEquals(String account, String seePassword);


}
