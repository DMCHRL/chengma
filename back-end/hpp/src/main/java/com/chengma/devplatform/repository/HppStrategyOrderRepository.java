package com.chengma.devplatform.repository;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.domain.HppStrategyData;
import com.chengma.devplatform.domain.HppStrategyOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface HppStrategyOrderRepository extends JpaRepository<HppStrategyOrder,String> {

    /**
     * 根据strategyId获取有效记录数
     * @param strategyId
     * @return
     */
    @Query(value="SELECT count(*) total from t_hpp_strategy_order where c_strategy_id=?1 and (c_status='PASSED' or c_status='APPLYING') and c_state='EFFECTIVE'",nativeQuery = true)
    public Long findByStrategyId(String strategyId);

    /**
     * 查询账号是否已跟单或申请中
     * @param account
     * @param password
     * @return
     */
    @Query(value="select * from t_hpp_strategy_order where c_account=?1 and c_password=?2 and (c_status='PASSED' or c_status='APPLYING') and c_state='EFFECTIVE' and c_type = '"+ DevplatformConstants.STRATEGY_IN+"'" ,nativeQuery = true)
    public HppStrategyOrder existFollow(String account,String password);

    /**
     * 查询是否首次使用策略跟单
     * @param mobileNum
     * @return
     */
    public List<HppStrategyOrder> findByMobileNumEquals(String mobileNum);

    /**
     * 根据手机号检查是否有跟单
     * @param mobileNum
     * @param type
     * @param status
     * @param state
     * @return
     */
    public List<HppStrategyOrder> findByMobileNumEqualsAndTypeEqualsAndStatusEqualsAndStateEquals(String mobileNum,String type,String status,String state);
}
