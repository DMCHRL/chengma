package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.DateUtil;
import com.chengma.devplatform.common.util.StringUtil;
import com.chengma.devplatform.repository.HppMsgNoticeRepository;
import com.chengma.devplatform.service.HppMobileUserService;
import com.chengma.devplatform.service.HppMsgNoticeService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppIntegralDTO;
import com.chengma.devplatform.service.dto.HppMobileUserDTO;
import com.chengma.devplatform.service.dto.HppMsgNoticeDTO;
import com.chengma.devplatform.service.mapper.HppMsgNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class HppMsgNoticeServiceImpl implements HppMsgNoticeService {

    private final HppMsgNoticeRepository hppMsgNoticeRepository;

    private final HppMsgNoticeMapper hppMsgNoticeMapper;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HppMobileUserService hppMobileUserService;

    public HppMsgNoticeServiceImpl(HppMsgNoticeRepository hppMsgNoticeRepository, HppMsgNoticeMapper hppMsgNoticeMapper){
        this.hppMsgNoticeRepository=hppMsgNoticeRepository;
        this.hppMsgNoticeMapper=hppMsgNoticeMapper;
    }

    @Override
    public HppMsgNoticeDTO save(HppMsgNoticeDTO hppMsgNoticeDTO) {
        hppMsgNoticeDTO.setCreateAt(new Date());
        hppMsgNoticeDTO.setStatus(DevplatformConstants.MSG_STATUS_UNREAD);
        return hppMsgNoticeMapper.toDto(hppMsgNoticeRepository.save(hppMsgNoticeMapper.toEntity(hppMsgNoticeDTO)));
    }

    @Override
    public HppMsgNoticeDTO findOne(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<HppMsgNoticeDTO> readMsg(String mobileNum) {
        String sql="select * from t_hpp_msg_notice where c_mobile='"+mobileNum+"' and c_status='UNREAD'";
        List<HppMsgNoticeDTO> hppMsgNoticeDTOList=baseDao.findListBySql(sql,HppMsgNoticeDTO.class);

        if(hppMsgNoticeDTOList!=null&&hppMsgNoticeDTOList.size()>0) {
            //消息标记已读
            StringBuilder column = new StringBuilder(" update t_hpp_msg_notice set c_status = 'READ', d_read_at = '" + DateUtil.format(new Date()) + "'  where c_mobile='" + mobileNum + "'");
            jdbcTemplate.execute(column.toString());

            //用户标记无未读消息
            HppMobileUserDTO hppMobileUserDTO = hppMobileUserService.findByMobile(mobileNum);
            hppMobileUserDTO.setFlag(DevplatformConstants.MOBILE_FLAG_NO);
            hppMobileUserService.save(hppMobileUserDTO);
        }
        return hppMsgNoticeDTOList;
    }
}
