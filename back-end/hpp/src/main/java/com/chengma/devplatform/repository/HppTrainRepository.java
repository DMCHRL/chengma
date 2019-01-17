package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.HppTrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface HppTrainRepository extends JpaRepository<HppTrain,String> {

    /**
     * 查重
     * @param name
     * @param userId
     * @return
     */
    @Query(value="select * from t_hpp_train where c_train_name =?1 and c_user_id =?2",nativeQuery = true)
    public HppTrain findByName(String name,String userId);

}
