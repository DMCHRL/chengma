package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.Mobile;
import com.chengma.devplatform.service.dto.MobileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MobileValidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileRepository extends JpaRepository<Mobile,String> {

    Mobile findMobileByMobileNumEquals(String mobileNum);
    
}
