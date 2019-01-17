package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.dao.BaseDao;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.domain.TlbRate;
import com.suitong.devplatform.repository.TlbRateRepository;
import com.suitong.devplatform.service.TlbRateService;
import com.suitong.devplatform.service.dto.TlbRateDTO;
import com.suitong.devplatform.service.mapper.TlbRateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class TlbRateServiceImpl implements TlbRateService {

    private final Logger log = LoggerFactory.getLogger(TlbRateServiceImpl.class);

    private final TlbRateRepository tlbRateRepository;

    private final TlbRateMapper tlbRateMapper;

    @Autowired
    private BaseDao baseDao;

    public TlbRateServiceImpl(TlbRateRepository tlbRateRepository, TlbRateMapper tlbRateMapper) {
        this.tlbRateRepository = tlbRateRepository;
        this.tlbRateMapper = tlbRateMapper;
    }

    @Override
    public HashMap<String, Object> save(TlbRateDTO tlbRateDTO) {
        log.debug("Request to save SysForm : {}", tlbRateDTO);
        HashMap<String,Object> response = new HashMap<>();
        try {
            TlbRate tlbRate = tlbRateMapper.toEntity(tlbRateDTO);
            tlbRate = tlbRateRepository.save(tlbRate);

            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    @Override
    public Page<TlbRateDTO> pageList(Pageable pageable) {
        return null;
    }

    @Override
    public List<TlbRateDTO> findAll(HashMap<String, Object> params) {

        //List<TlbRate> tlbRates = tlbRateRepository.findAll();

        //tlbRateRepository.findAll()

        List<TlbRateDTO> list =baseDao.findListBySql("select id, c_rate, c_recharge from t_tlb_rate", TlbRateDTO.class);

        /*List<TlbRateDTO> tlbRateDTOS = new ArrayList<>();
        for(TlbRate tlbRate : tlbRates){
            tlbRateDTOS.add(tlbRateMapper.toDto(tlbRate));
        }*/

        return list;
    }

    /**
     * Save a SysUser.
     *
     * @param TlbRateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TlbRateDTO update(TlbRateDTO TlbRateDTO) {
        log.debug("Request to save SysUser : {}", TlbRateDTO);
        TlbRate TlbRate = tlbRateMapper.toEntity(TlbRateDTO);
        TlbRate = tlbRateRepository.save(TlbRate);
        return tlbRateMapper.toDto(TlbRate);
    }


    @Override
    public List<TlbRateDTO> queryComponentPage(Long formId, String visible) {
        return null;
    }

    @Override
    public TlbRateDTO findOne(Long id) {
        return tlbRateMapper.toDto(tlbRateRepository.findOne(id));
    }

    @Override
    public void delete(Long id) {
        tlbRateRepository.delete(id);
    }

    @Override
    public void deleteSysRoleComponent(Long id) {
        System.out.println(id);
    }
}
