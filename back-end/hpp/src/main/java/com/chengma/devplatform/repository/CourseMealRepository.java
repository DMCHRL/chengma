package com.chengma.devplatform.repository;

/**
 * Created by Administrator on 2018/9/14.
 */
import com.chengma.devplatform.domain.CourseMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface CourseMealRepository extends JpaRepository<CourseMeal,String> {

    List<CourseMeal> findByCourseIdEquals(String courseId);

    CourseMeal findByCourseMealIdEquals(String courseMealId);

    //根据套餐id删除关联
    void deleteByCourseMealIdEquals(String courseMealId);
}
