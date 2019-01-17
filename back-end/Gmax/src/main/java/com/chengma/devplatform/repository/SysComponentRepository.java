package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SysComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysComponentRepository extends JpaRepository<SysComponent, String> {

    @Modifying
    @Transactional
    @Query(value = "delete from SysComponent sc where sc.formId=?1 ")
    void deleteComponentByFormId(String id);
}
