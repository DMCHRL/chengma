package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.domain.HppStrategyData;
import com.chengma.devplatform.repository.HppStrategyDataRepository;
import com.chengma.devplatform.service.HppStrategyDataService;
import com.chengma.devplatform.service.dto.HppStrategyDataDTO;
import com.chengma.devplatform.service.mapper.HppStrategyDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class HppStrategyDataServiceImpl implements HppStrategyDataService {

    private final HppStrategyDataRepository hppStrategyDataRepository;

    private final HppStrategyDataMapper hppStrategyDataMapper;

    @Autowired
    private BaseDao baseDao;

    public HppStrategyDataServiceImpl(HppStrategyDataRepository hppStrategyDataRepository,HppStrategyDataMapper hppStrategyDataMapper){
        this.hppStrategyDataRepository=hppStrategyDataRepository;
        this.hppStrategyDataMapper=hppStrategyDataMapper;
    }

    @Override
    public HppStrategyDataDTO findByAccount(String account) {
        String sql = "SELECT\n" +
                "\tsd.*,\n" +
                "\ts.c_strategy_text,\n" +
                "\ts.c_strategy_name,\n" +
                "\ts.c_trade_type AS trade_type,\n" +
                "\ts.c_id AS strategy_id \n" +
                "FROM\n" +
                "\tt_hpp_strategy_data sd\n" +
                "\tleft JOIN t_hpp_strategy s ON sd.c_account = s.c_account \n" +
                "where sd.c_account = '"+account+"'";
        List<HppStrategyDataDTO> list = baseDao.findListBySql(sql,HppStrategyDataDTO.class);
        if(list != null&& list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public HppStrategyDataDTO loadByAccount(String account) {
        String sql = "SELECT\n" +
                "\tsd.*,\n" +
                "\ts.c_strategy_id AS strategy_id,\n" +
                "\tst.c_strategy_name,\n" +
                "\tst.c_account as strategy_account\n" +
                "FROM\n" +
                "\tt_hpp_strategy_data sd\n" +
                "\tleft JOIN t_hpp_strategy_order s ON sd.c_account = s.c_account \n" +
                "\tAND s.c_status='PASSED' and s.c_state='EFFECTIVE' AND s.c_type='IN'\n" +
                "\tleft JOIN t_hpp_strategy st on st.c_id=s.c_strategy_id\n" +
                "where sd.c_account = '"+account+"'";
        List<HppStrategyDataDTO> list = baseDao.findListBySql(sql, HppStrategyDataDTO.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public HppStrategyDataDTO save(HppStrategyDataDTO hppStrategyDataDTO) {
        return  hppStrategyDataMapper.toDto( hppStrategyDataRepository.save(hppStrategyDataMapper.toEntity(hppStrategyDataDTO)));
    }

    @Override
    public void delete(String id) {
        hppStrategyDataRepository.delete(id);
    }

    @Override
    public void deleteByAccount(String account) {
        hppStrategyDataRepository.deleteByAccountEquals(account);
    }
}
