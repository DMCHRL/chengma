package com.chengma.devplatform.domain;

/**
 * Created by Administrator on 2018/8/9.
 */

import com.chengma.devplatform.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 线下培训实体
 */
@Entity
@Table(name = "t_course_meal")
public class CourseMeal extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "c_course_id")
    private String courseId;     //课程Id

    @Column(name = "c_course_meal_id")
    private String courseMealId;     //套餐Id

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseMealId() {
        return courseMealId;
    }

    public void setCourseMealId(String courseMealId) {
        this.courseMealId = courseMealId;
    }
}
