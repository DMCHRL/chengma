package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppVideoRepository;
import com.chengma.devplatform.service.FileStreamManageService;
import com.chengma.devplatform.service.HppVideoService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import com.chengma.devplatform.service.dto.WxOrderDTO;
import com.chengma.devplatform.service.mapper.HppVideoMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class HppVideoServiceImpl implements HppVideoService {

    private final HppVideoRepository hppVideoRepository;

    private final HppVideoMapper hppVideoMapper;


    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;


    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    public HppVideoServiceImpl(HppVideoRepository hppVideoRepository, HppVideoMapper hppVideoMapper){
        this.hppVideoRepository=hppVideoRepository;
        this.hppVideoMapper=hppVideoMapper;
    }
    
    @Override
    public Page<HppVideoDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String typeName = formParams.get("typeName") == null ? "" : formParams.get("typeName").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String typeId = formParams.get("typeId") == null ? "" : formParams.get("typeId").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select h.*,t.c_video_type_name as video_type_name ");

        StringBuilder cond = new StringBuilder(" from t_hpp_video h join t_hpp_video_type t on h.c_video_type_id=t.c_id and h.c_video_type_id ='"+typeId+"' ");

        //非超级管理员
       /* User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and t.c_user_id='"+currentUser.getId()+"'");
        }*/

        if(StringUtils.isNotBlank(typeName)){
            cond.append(" and t.c_video_type_name like '%"+typeName+"%' ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "h."+ ReflectUtils.getColumnName(HppVideo.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppVideoDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, HppVideoDTO.class);
        return page;
    }

    @Override
    public HppVideoDTO createHppVideoDTO(HppVideoDTO hppVideoDTO) {

        //视频上传..


        BASE64Decoder decoder = new BASE64Decoder();
        String videoImageWebPath = environment.getProperty("fileManage.videoImageWebPath");
        String imagePath = environment.getProperty("fileManage.imagePath");
        String prefix = environment.getProperty("fileManage.base64Prefix");
        String newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg" ;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        imagePath = imagePath + sdf.format(new Date()) + "/";

        File dest =new File(imagePath);
        //如果文件夹不存在则创建
        if  (!dest .exists()  && !dest .isDirectory()) {
            dest .mkdir();
        }

        if(!StringUtils.isEmpty(hppVideoDTO.getImg()) && hppVideoDTO.getImg().startsWith(prefix)) {
            fileStreamManageService.uploadImage(decoder, hppVideoDTO.getImg(), imagePath, prefix, newFileName);
            hppVideoDTO.setImg(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        //添加
        if(StringUtils.isBlank(hppVideoDTO.getId())){
            //获取当前类别最大期数
            Integer maxPhase= hppVideoRepository.maxPhase(hppVideoDTO.getVideoTypeId());
            if(maxPhase==null){
                maxPhase=1;
            }else{
                maxPhase++;
            }
            Date now=new Date();
            hppVideoDTO.setCreateAt(now);
            hppVideoDTO.setUpdateAt(now);
            hppVideoDTO.setExchangeNum(0);
            hppVideoDTO.setPlayNum(0);
            hppVideoDTO.setPhase(maxPhase);
            if(hppVideoDTO.getSortNum() == null){
                hppVideoDTO.setSortNum(99);
            }
        }else{
            //修改
            HppVideo currentHppVideo=hppVideoRepository.findOne(hppVideoDTO.getId());
            hppVideoDTO.setCreateAt(currentHppVideo.getCreateAt());
            hppVideoDTO.setUpdateAt(new Date());
            hppVideoDTO.setExchangeNum(currentHppVideo.getExchangeNum());
            hppVideoDTO.setPlayNum(currentHppVideo.getPlayNum());
        }

        if(hppVideoDTO.getFeeFlag() == 2){//免费
            hppVideoDTO.setExchangePrice(0.0);
            hppVideoDTO.setPrice(0.0);
        }
        return hppVideoMapper.toDto(hppVideoRepository.save(hppVideoMapper.toEntity(hppVideoDTO)));
    }

    @Override
    public HppVideoDTO save(HppVideoDTO hppVideoDTO) {
        return hppVideoMapper.toDto(hppVideoRepository.save(hppVideoMapper.toEntity(hppVideoDTO)));
    }

    @Override
    public HppVideoDTO findOne(String id) {
        HppVideo hppVideo = hppVideoRepository.findOne(id);
        return hppVideoMapper.toDto(hppVideo);
    }

    @Override
    public HppVideoDTO showOnApp(String id) {
       /* HppVideo hppVideo = hppVideoRepository.findOne(id);*/
        User user = userService.getUserWithAuthorities();
        String sql = "SELECT\n" +
                "\tv.c_id,\n" +
                "\tv.c_video_type_id,\n" +
                "\tv.c_video_name,\n" +
                "\tv.c_video_text,\n" +
                "\tv.i_play_num,\n" +
                "\tv.i_fee_flag,\n" +
                "\tv.i_price,\n" +
                "\tv.d_create_at,\n" +
                "\tv.i_exchange_num,\n" +
                "\tv.d_update_at,\n" +
                "\tv.c_img,\n" +
                "\tv.i_sort_num,\n" +
                "\tv.c_video_url\n" +
              /*  "\tCASE\n" +
                "WHEN v.i_fee_flag = 2 THEN\n" +
                "\tv.c_video_url\n" +
                "WHEN v.i_fee_flag = 1\n" +
                "AND o.c_status = 'Y' THEN\n" +
                "\tv.c_video_url\n" +
                "ELSE\n" +
                "\t''\n" +
                "END AS c_video_url\n" +*/
                "FROM\n" +
                "\tt_hpp_video v left join t_wx_order o on o.c_body = 'video' and c_object='"+user.getMobile()+"' AND o.c_body_id = v.c_id  and o.c_status !='"+ DevplatformConstants.PAY_STATUS_DISABLE+"'\n" +
                "where v.c_id='"+id+"'\n";
        List<HppVideoDTO> list = baseDao.findListBySql(sql,HppVideoDTO.class);
        HppVideoDTO hppVideoDTO = list.get(0);
        if(StringUtils.isNotBlank(hppVideoDTO.getVideoUrl())){
            int playNum = hppVideoDTO.getPlayNum() == null ? 0: hppVideoDTO.getPlayNum();
            hppVideoDTO.setPlayNum(playNum+1);
            hppVideoRepository.save(hppVideoMapper.toEntity(hppVideoDTO));
        }
        return hppVideoDTO;
    }

    @Override
    public void delete(String id) {
        hppVideoRepository.delete(id);
    }

    /*@Override
    public void deleteByTypeId(String typeId) {
        hppVideoRepository.deleteByTypeId(typeId);
    }*/

    @Override
    public HppVideoDTO findByTypeIdAndVideoName(String typeId, String videoName) {
        return hppVideoMapper.toDto(hppVideoRepository.findByTypeIdAndVideoName(typeId,videoName));
    }

    @Override
    public Long countByTypeId(String typeId) {
        Long total =hppVideoRepository.countByTypeId(typeId);
        return total;
    }

    @Override
    public Integer maxPhase(String typeId) {
        return hppVideoRepository.maxPhase(typeId);
    }

    @Override
    public HashMap<String, Object> checkHppVideo(HppVideoDTO hppVideoDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppVideoDTO.getVideoTypeId())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择视频类别");
            return retMap;
        }
        if(StringUtils.isBlank(hppVideoDTO.getVideoName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入视频名称");
            return retMap;
        }
        HppVideoDTO currentHppVideo = this.findByTypeIdAndVideoName(hppVideoDTO.getVideoTypeId(),hppVideoDTO.getVideoName());
        if (currentHppVideo != null) {
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "视频名称已存在");
            return retMap;
        }
        if(StringUtils.isBlank(hppVideoDTO.getVideoUrl())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传视频");
            return retMap;
        }
        if(hppVideoDTO.getFeeFlag() == 1){
            if(hppVideoDTO.getPrice()==null||hppVideoDTO.getPrice()<0){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请输入有效价格");
                return retMap;
            }
            if(hppVideoDTO.getIntegralPrice()==null||hppVideoDTO.getIntegralPrice()<0){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请输入有效积分价格");
                return retMap;
            }
        }
        if(StringUtils.isBlank(hppVideoDTO.getImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传图片");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    //废弃
    @Override
    public List<HppVideoDTO> loadIntegral() {
        StringBuffer sql=new StringBuffer("SELECT c_id,c_img,c_video_name,i_exchange_num,i_integral_price from t_hpp_video where i_fee_flag = 1 ORDER BY i_exchange_num desc");
        return baseDao.findListBySql(sql.toString(),HppVideoDTO.class);
    }

    //废弃
    @Override
    public List<HppVideoDTO> loadExchange(String mobileNum) {
        StringBuffer sql=new StringBuffer("select v.*,e.i_price as exchange_price from t_hpp_exchange e join t_hpp_video v on v.c_id=e.c_video_id and e.c_mobile_num='"+mobileNum+"'");
        return baseDao.findListBySql(sql.toString(),HppVideoDTO.class);
    }

    @Override
    public List<HppVideoDTO> findByTypeId(String typeId) {
        User user=userService.getUserWithAuthorities();
        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\tv.c_id,\n" +
                "\tv.c_video_type_id,\n" +
                "\tv.c_video_name,\n" +
                "\tv.c_video_text,\n" +
                "\tv.i_play_num,\n" +
                "\tv.i_fee_flag,\n" +
                "\tv.i_price,\n" +
                "\tv.d_create_at,\n" +
                "\tv.i_exchange_num,\n" +
                "\tv.d_update_at,\n" +
                "\tv.c_img,\n" +
                "\tv.i_sort_num,\n" +
                "\tv.c_video_url,\n" +
                "\tt.c_video_type_name AS c_video_type_name\n" +
               /* "\tCASE\n" +
                "WHEN v.i_fee_flag = 2 THEN\n" +
                "\tv.c_video_url\n" +
                "WHEN v.i_fee_flag = 1\n" +
                "AND o.c_status = 'Y' THEN\n" +
                "\tv.c_video_url\n" +
                "ELSE\n" +
                "\t''\n" +
                "END AS c_video_url\n" +*/
                "FROM\n" +
                "\tt_hpp_video v\n" +
                "JOIN t_hpp_video_type t ON v.c_video_type_id = t.c_id\n" +
                "AND v.c_video_type_id = '"+typeId+"'\n" +
                "left JOIN t_wx_order o ON o.c_body = 'video' and c_object='"+user.getMobile()+"' and o.c_status !='"+ DevplatformConstants.PAY_STATUS_DISABLE+"'\n" +
                "AND o.c_body_id = v.c_id\n" +
                "ORDER BY\n" +
                "\tv.i_sort_num ASC");
        return baseDao.findListBySql(sql.toString(),HppVideoDTO.class);
    }
}
