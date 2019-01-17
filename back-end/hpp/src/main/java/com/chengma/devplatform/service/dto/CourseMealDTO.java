package com.chengma.devplatform.service.dto;

import com.chengma.devplatform.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/25.
 */
public class CourseMealDTO {

    private String id;

    private String courseId;     //课程Id

    private String courseMealId;     //套餐Id

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCourseMealId(String courseMealId) {
        this.courseMealId = courseMealId;
    }

    public String getId() {
        return id;
    }

    public String getCourseMealId() {
        return courseMealId;
    }
}
