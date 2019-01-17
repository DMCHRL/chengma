package com.chengma.devplatform.repository;

/**
 * Created by Administrator on 2018/9/14.
 */
import com.chengma.devplatform.domain.HppCourse;
import com.chengma.devplatform.domain.HppExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppExamRepository extends JpaRepository<HppExam,String> {

}
