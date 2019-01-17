package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppExchange;
import com.chengma.devplatform.domain.HppMobileUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MobileValidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HppExchangeRepository extends JpaRepository<HppExchange,String> {

    @Query(value="select * from t_hpp_exchange where c_mobile_num=?1 and c_video_id=?2",nativeQuery = true)
    public HppExchange findByMobileIdAndVideoId(String mobileNum,String videoId);
}
