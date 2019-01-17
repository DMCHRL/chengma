package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppMobileUser;
import com.chengma.devplatform.domain.HppMobileValidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MobileValidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HppMobileUserRepository extends JpaRepository<HppMobileUser,String> {

    HppMobileUser findByPhoneEquals(String phone);

    List<HppMobileUser> findByUserNameEqualsAndPhoneEquals(String userName, String phone);

    HppMobileUser findByRecommendationEquals(String recommendation);

    @Query(value = "select * from t_hpp_mobile_user where c_follow_flag ='Y'",nativeQuery = true)
    List<HppMobileUser> findWithFollowFlag();

    @Query(value = "select * from t_hpp_mobile_user where c_buy_flag ='Y'",nativeQuery = true)
    List<HppMobileUser> findWithBuyFlag();

   /* List<HppMobileUser> findByRecommendationEdEquals(String recommendationEd);*/
}
