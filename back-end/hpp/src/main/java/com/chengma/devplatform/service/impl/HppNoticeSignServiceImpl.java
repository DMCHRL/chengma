package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.HppNotice;
import com.chengma.devplatform.domain.HppNoticeSign;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppNoticeSignRepository;
import com.chengma.devplatform.service.HppNoticeSignService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppNoticeDTO;
import com.chengma.devplatform.service.dto.HppNoticeSignDTO;
import com.chengma.devplatform.service.mapper.HppNoticeSignMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */
@Service
@Transactional
public class HppNoticeSignServiceImpl implements HppNoticeSignService {

    private final HppNoticeSignRepository hppNoticeSignRepository;

    private final HppNoticeSignMapper hppNoticeSignMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    public HppNoticeSignServiceImpl(HppNoticeSignRepository hppNoticeSignRepository, HppNoticeSignMapper hppNoticeSignMapper){
        this.hppNoticeSignRepository=hppNoticeSignRepository;
        this.hppNoticeSignMapper=hppNoticeSignMapper;
    }

    @Override
    public Page<HppNoticeSignDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public HppNoticeSignDTO save(HppNoticeSignDTO hppNoticeSignDTO) {
        return hppNoticeSignMapper.toDto(hppNoticeSignRepository.save(hppNoticeSignMapper.toEntity(hppNoticeSignDTO)));
    }

    @Override
    public HppNoticeSignDTO createHppNoticeSignDTO(HppNoticeSignDTO hppNoticeSignDTO) {
        //添加
        if(StringUtils.isBlank(hppNoticeSignDTO.getId())){

        }else{
            //修改
        }

        return hppNoticeSignMapper.toDto(hppNoticeSignRepository.save(hppNoticeSignMapper.toEntity(hppNoticeSignDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateHppNoticeSignDTO(HppNoticeSignDTO hppNoticeSignDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppNoticeSignDTO findOne(String id) {
        return hppNoticeSignMapper.toDto(hppNoticeSignRepository.findOne(id));
    }

    @Override
    public List<HppNoticeSignDTO> findAll() {
        return hppNoticeSignMapper.toDto(hppNoticeSignRepository.findAll());
    }

    @Override
    public void delete(String id) {
        HppNoticeSign hppNoticeSign = hppNoticeSignRepository.findOne(id);
        if(hppNoticeSign != null){
            hppNoticeSign.setDelFlag("Y");
            hppNoticeSign.setStatus("Y");
            hppNoticeSignRepository.save(hppNoticeSign);
        }
    }

    @Override
    public HppNoticeSignDTO unreadNum(String mobile) {
        String sql = "select count(*) as unread_num from  t_hpp_notice_sign where c_mobile='"+mobile+"' and c_status='N'";
        List<HppNoticeSignDTO > list = baseDao.findListBySql(sql,HppNoticeSignDTO.class);
        if(list == null ||list.size() ==0){
            return null;
        }
        return list.get(0);
    }

    /**
     * 读取消息通知
     * @param mobile
     * @return
     */
    @Override
    @Transactional
    public List<HppNoticeDTO> readList(String mobile) {
        /**
         * 标记已读
         */
       /* String signSql = "select * from  t_hpp_notice_sign where c_mobile='"+mobile+"' and c_status='N'";
        List<HppNoticeSignDTO > signList = baseDao.findListBySql(signSql,HppNoticeSignDTO.class);
        if(signList != null){
            Date now = new Date();
            for(HppNoticeSignDTO hppNoticeSignDTO: signList){
                hppNoticeSignDTO.setStatus("Y");
                hppNoticeSignDTO.setReadTime(now);
                hppNoticeSignRepository.save(hppNoticeSignMapper.toEntity(hppNoticeSignDTO));
            }
        }*/

        //获取消息体
        String sql ="SELECT\n" +
                "\tt1.*,\n" +
                "\tt2.c_id as sign_id,\n" +
                "\tt2.c_status as status\n" +
                "FROM\n" +
                "\tt_hpp_notice t1,\n" +
                "\tt_hpp_notice_sign t2\n" +
                "WHERE\n" +
                "\tt1.c_id = t2.c_notice_id\n" +
                "AND c_mobile = '"+mobile+"'\n"+
                "AND (ISNULL(t2.c_del_flag) or t2.c_del_flag !='Y')\n"+
                "\t order by d_create_time desc";
        List<HppNoticeDTO> noticeList = baseDao.findListBySql(sql, HppNoticeDTO.class);
        return noticeList;
    }

    @Override
    public void deleteByNoticeId(String noticeId) {
        hppNoticeSignRepository.deleteAllByNoticeIdEquals(noticeId);
    }

    @Override
    public void readOne(String id) {
        HppNoticeSign hppNoticeSign = hppNoticeSignRepository.findOne(id);
        hppNoticeSign.setStatus("Y");
        hppNoticeSign.setReadTime(new Date());
        hppNoticeSignRepository.save(hppNoticeSign);
    }
}
