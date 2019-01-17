package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.TlbEaGroup;
import com.chengma.devplatform.repository.TlbEaGroupRepository;
import com.chengma.devplatform.service.TlbEaGroupService;
import com.chengma.devplatform.service.dto.TlbEaGroupDTO;
import com.chengma.devplatform.service.mapper.TlbEaGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class TlbEaGroupServiceImpl implements TlbEaGroupService {

    private final Logger log = LoggerFactory.getLogger(TlbEaGroupServiceImpl.class);

    private final TlbEaGroupRepository tlbEaGroupRepository;

    private final TlbEaGroupMapper tlbEaGroupMapper;


    public TlbEaGroupServiceImpl(TlbEaGroupRepository tlbEaGroupRepository, TlbEaGroupMapper tlbEaGroupMapper) {
        this.tlbEaGroupRepository = tlbEaGroupRepository;
        this.tlbEaGroupMapper = tlbEaGroupMapper;
    }

    @Override
    public HashMap<String, Object> save(TlbEaGroupDTO tlbEaGroupDTO) {
        log.debug("Request to save SysForm : {}", tlbEaGroupDTO);
        HashMap<String,Object> response = new HashMap<>();
        try {
            TlbEaGroup tlbEaGroup = tlbEaGroupMapper.toEntity(tlbEaGroupDTO);
            tlbEaGroup = tlbEaGroupRepository.save(tlbEaGroup);

            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    @Override
    public Page<TlbEaGroupDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public List<TlbEaGroupDTO> findAll() {

        List<TlbEaGroupDTO> list = tlbEaGroupMapper.toDto(tlbEaGroupRepository.findAll());
        return list;
    }

    /**
     * Save a SysUser.
     *
     * @param TlbEaGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TlbEaGroupDTO update(TlbEaGroupDTO TlbEaGroupDTO) {
        log.debug("Request to save tlbEaGroup : {}", TlbEaGroupDTO);
        TlbEaGroup TlbEaGroup = tlbEaGroupMapper.toEntity(TlbEaGroupDTO);
        TlbEaGroup = tlbEaGroupRepository.save(TlbEaGroup);
        return tlbEaGroupMapper.toDto(TlbEaGroup);
    }


    @Override
    public TlbEaGroupDTO findOne(String id) {
        return tlbEaGroupMapper.toDto(tlbEaGroupRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        tlbEaGroupRepository.delete(id);
    }

}
