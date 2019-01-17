package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppIntegral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppIntegralRepository extends JpaRepository<HppIntegral,String> {

    @Query(value = "select * from t_hpp_integral where c_mobile_num=?1",nativeQuery = true)
    public HppIntegral findByMobileNum(String mobileNum);

}
