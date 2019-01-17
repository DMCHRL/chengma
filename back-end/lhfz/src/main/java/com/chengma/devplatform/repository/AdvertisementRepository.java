package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,String> {

}
