package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.domain.HppVideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface HppVideoRepository extends JpaRepository<HppVideo,String> {

    /**
     *批量删除
     * @param typeId
     */
   /* @Modifying
    @Query(value="delete from t_hpp_video where c_video_type_id =?1",nativeQuery = true)
    public void deleteByTypeId(String typeId);*/

    /**
     * 根据videoTypeId获取记录数
     * @param typeId
     * @return
     */
    @Query(value="select count(*) as total from t_hpp_video where c_video_type_id =?1",nativeQuery = true)
    public Long countByTypeId(String typeId);

    /**
     * 根据类别id和视频名称查询
     * @param typeId
     * @param videoName
     * @return
     */
    @Query(value="select * from t_hpp_video where c_video_type_id=?1 and c_video_name=?2",nativeQuery = true)
    public HppVideo findByTypeIdAndVideoName(String typeId, String videoName);


    /**
     * 根据类型id返回当前最大期数
     * @param typeId
     * @return
     */
    @Query(value="select MAX(i_phase) as maxPhase from t_hpp_video where c_video_type_id=?1",nativeQuery = true)
    public Integer maxPhase(String typeId);

}
