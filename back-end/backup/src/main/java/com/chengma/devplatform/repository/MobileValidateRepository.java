package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.MobileValidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MobileValidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileValidateRepository extends JpaRepository<MobileValidate,String> {
    
}
