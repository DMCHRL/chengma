package com.chengma.devplatform.repository;

/**
 * Created by Administrator on 2018/10/12.
 */
import com.chengma.devplatform.domain.HppIntegralDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface HppIntegralDetailRepository extends JpaRepository<HppIntegralDetail,String> {

    //分享朋友圈
    //public List<HppIntegralDetail> findByTypeEqualsAndCreateTimeEquals(String type,Date createTime);

    //分享好友
    public HppIntegralDetail findByTypeEqualsAndUuidEquals(String type,String uuid);

    List<HppIntegralDetail> findByMobileEqualsOrderByCreateTimeDesc(String mobile);
}
