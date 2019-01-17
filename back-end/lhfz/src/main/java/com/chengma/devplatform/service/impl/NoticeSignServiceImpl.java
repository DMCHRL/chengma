package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.NoticeSign;
import com.chengma.devplatform.repository.NoticeSignRepository;
import com.chengma.devplatform.service.NoticeSignService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.NoticeDTO;
import com.chengma.devplatform.service.dto.NoticeSignDTO;
import com.chengma.devplatform.service.mapper.NoticeSignMapper;
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
public class NoticeSignServiceImpl implements NoticeSignService {

    private final NoticeSignRepository hppNoticeSignRepository;

    private final NoticeSignMapper hppNoticeSignMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    public NoticeSignServiceImpl(NoticeSignRepository hppNoticeSignRepository, NoticeSignMapper hppNoticeSignMapper){
        this.hppNoticeSignRepository=hppNoticeSignRepository;
        this.hppNoticeSignMapper=hppNoticeSignMapper;
    }

    @Override
    public Page<NoticeSignDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public NoticeSignDTO save(NoticeSignDTO hppNoticeSignDTO) {
        return hppNoticeSignMapper.toDto(hppNoticeSignRepository.save(hppNoticeSignMapper.toEntity(hppNoticeSignDTO)));
    }

    @Override
    public NoticeSignDTO createNoticeSignDTO(NoticeSignDTO hppNoticeSignDTO) {
        //添加
        if(StringUtils.isBlank(hppNoticeSignDTO.getId())){

        }else{
            //修改
        }

        return hppNoticeSignMapper.toDto(hppNoticeSignRepository.save(hppNoticeSignMapper.toEntity(hppNoticeSignDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateNoticeSignDTO(NoticeSignDTO hppNoticeSignDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public NoticeSignDTO findOne(String id) {
        return hppNoticeSignMapper.toDto(hppNoticeSignRepository.findOne(id));
    }

    @Override
    public List<NoticeSignDTO> findAll() {
        return hppNoticeSignMapper.toDto(hppNoticeSignRepository.findAll());
    }

    @Override
    public void delete(String id) {
        NoticeSign hppNoticeSign = hppNoticeSignRepository.findOne(id);
        if(hppNoticeSign != null){
            hppNoticeSign.setDelFlag("Y");
            hppNoticeSignRepository.save(hppNoticeSign);
        }
    }

    @Override
    public NoticeSignDTO unreadNum(String mobile) {
        String sql = "select count(*) as unread_num from  t_notice_sign where c_mobile='"+mobile+"' and c_status='N'";
        List<NoticeSignDTO> list = baseDao.findListBySql(sql,NoticeSignDTO.class);
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
    public List<NoticeDTO> read(String mobile) {
        /**
         * 标记已读
         */
        String signSql = "select * from  t_notice_sign where c_mobile='"+mobile+"' and c_status='N'";
        List<NoticeSignDTO> signList = baseDao.findListBySql(signSql,NoticeSignDTO.class);
        if(signList != null){
            Date now = new Date();
            for(NoticeSignDTO hppNoticeSignDTO: signList){
                hppNoticeSignDTO.setStatus("Y");
                hppNoticeSignDTO.setReadTime(now);
                hppNoticeSignRepository.save(hppNoticeSignMapper.toEntity(hppNoticeSignDTO));
            }
        }

        //获取消息体
        String sql ="SELECT\n" +
                "\tt1.*,\n" +
                "\tt2.c_id as sign_id\n" +
                "FROM\n" +
                "\tt_notice t1,\n" +
                "\tt_notice_sign t2\n" +
                "WHERE\n" +
                "\tt1.c_id = t2.c_notice_id\n" +
                "AND c_mobile = '"+mobile+"'\n"+
                "AND (ISNULL(t2.c_del_flag) or t2.c_del_flag !='Y')\n"+
                "\t order by d_create_time desc";
        List<NoticeDTO> noticeList = baseDao.findListBySql(sql, NoticeDTO.class);
        return noticeList;
    }

    @Override
    public void deleteByNoticeId(String noticeId) {
        hppNoticeSignRepository.deleteAllByNoticeIdEquals(noticeId);
    }
}
