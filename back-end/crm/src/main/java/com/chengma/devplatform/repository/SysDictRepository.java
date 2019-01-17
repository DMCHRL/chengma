package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.SysDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysDictRepository extends JpaRepository<SysDict,String> {
    
}
