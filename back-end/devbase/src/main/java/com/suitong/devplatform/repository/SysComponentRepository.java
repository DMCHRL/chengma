package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysComponent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysComponentRepository extends JpaRepository<SysComponent,Long> {

    @Modifying
    @Transactional
    @Query(value="delete from SysComponent sc where sc.formId=?1 ")
    void deleteComponentByFormId(Long id);
}
