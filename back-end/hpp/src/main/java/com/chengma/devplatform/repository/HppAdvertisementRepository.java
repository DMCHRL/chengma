package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppAdvertisement;
import com.chengma.devplatform.domain.HppTrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppAdvertisementRepository extends JpaRepository<HppAdvertisement,String> {

}
