package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppTrainApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppTrainApplyRepository extends JpaRepository<HppTrainApply,String> {

    /**
     * 根据培训id返回报名人数
     * @param courseMealId
     * @return
     */
    @Query(value="select count(*) as count from t_hpp_train_apply where c_course_meal_id=?1",nativeQuery = true)
    public Long countByCourseMealId(String courseMealId);

    HppTrainApply findByOrderNumEquals(String orderNum);

    void deleteByOrderNumEquals(String orderNum);
}
