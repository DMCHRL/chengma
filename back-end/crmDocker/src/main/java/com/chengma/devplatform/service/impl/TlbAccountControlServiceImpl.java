package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.domain.TlbAccountControl;
import com.chengma.devplatform.repository.TlbAccountControlRepository;
import com.chengma.devplatform.service.TlbAccountControlService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.TlbAccountControlDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.mapper.TlbAccountControlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class TlbAccountControlServiceImpl implements TlbAccountControlService {

    private final Logger log = LoggerFactory.getLogger(TlbAccountControlServiceImpl.class);

    private final TlbAccountControlRepository tlbAccountControlRepository;

    private final TlbAccountControlMapper tlbAccountControlMapper;

    @Autowired
    private SerialNoService serialNoService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private UserService userService;

    public TlbAccountControlServiceImpl(TlbAccountControlRepository tlbAccountControlRepository, TlbAccountControlMapper tlbAccountControlMapper) {
        this.tlbAccountControlRepository = tlbAccountControlRepository;
        this.tlbAccountControlMapper = tlbAccountControlMapper;
    }

    @Override
    public TlbAccountControlDTO save(TlbAccountControlDTO tlbAccountControlDTO) {
        if(tlbAccountControlDTO==null)return null;
        TlbAccountDTO tlbAccountDTO=tlbAccountService.findByAccountAndPassword(tlbAccountControlDTO.getAccount(),tlbAccountControlDTO.getAccountPassword());
        if(tlbAccountDTO==null){
            return null;
        }else{
            TlbAccountControl t=tlbAccountControlRepository.findByUserIdAndAccount(tlbAccountControlDTO.getUserId(),tlbAccountControlDTO.getAccount());
            if(t!=null){
                //已经配置
                return null;
            }else{
                TlbAccountControl tlbAccountControl=tlbAccountControlMapper.toEntity(tlbAccountControlDTO);
                tlbAccountControlRepository.save(tlbAccountControl);
                return tlbAccountControlMapper.toDto(tlbAccountControl);
            }
        }
    }

    @Override
    public List<TlbAccountControlDTO> findByUserId(String userId) {
        return tlbAccountControlMapper.toDto(tlbAccountControlRepository.findByUserId(userId));
    }

    @Override
    public void delete(String id) {
        tlbAccountControlRepository.delete(id);
    }
}
