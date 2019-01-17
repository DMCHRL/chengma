package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppVideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppVideoTypeRepository extends JpaRepository<HppVideoType,String> {

    @Query(value="select * from t_hpp_video_type where c_video_type_name=?1 and c_user_id=?2",nativeQuery = true)
    public HppVideoType findByTypeNameAndUserId(String typeName, String userId);

    /**
     * 每日推荐总数
     * @return
     */
    @Query(value="select count(*) from t_hpp_video_type where c_day_push='YES'",nativeQuery = true)
    public Long countDayPush();
}
