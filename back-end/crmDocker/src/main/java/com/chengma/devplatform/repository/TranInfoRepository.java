package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TranInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranInfoRepository extends JpaRepository<TranInfo,String> {
    
}
