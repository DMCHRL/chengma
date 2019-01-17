package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbCommission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbCommissionRepository extends JpaRepository<TlbCommission,String>,JpaSpecificationExecutor<TlbCommission> {
	
	@Query(value="SELECT * FROM t_tlb_commission WHERE c_user_id =?1",nativeQuery=true)
	public TlbCommission findByUserId(String userId);
}
