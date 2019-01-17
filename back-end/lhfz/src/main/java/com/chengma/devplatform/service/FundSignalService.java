package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.FundSignalDTO;
import com.chengma.devplatform.service.dto.view.FindListFundSignalDTO;
import com.chengma.devplatform.service.dto.view.PreFundJoinDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface FundSignalService {

    Page<FundSignalDTO> pageList(HashMap<String, Object> params);

    FundSignalDTO save(FundSignalDTO hppVideoDTO);

    FundSignalDTO createFundSignalDTO(FundSignalDTO hppVideoDTO);

    HashMap<String, Object> checkCreateFundSignalDTO(FundSignalDTO hppVideoDTO);

    FundSignalDTO findOne(String id);

    FundSignalDTO findOneDetail(String id);

    /*
    立即入伙
     */
    PreFundJoinDTO preFundJoin(String id);

    List<FundSignalDTO> findAll();

   /*
   for app
    */
    List<FindListFundSignalDTO> findList(HashMap<String, Object> params);

    /*
  for app 加载我的合伙配置
   */
    List<FindListFundSignalDTO> loadMyFund(HashMap<String, Object> params);

    /*
    把信号改为操作状态,初始化基金信号数据
     */
    void operation(String id);




    /*
    把信号改为结束状态,分配收益
     */
    void finish(String id);

    
    void delete(String id);
}

