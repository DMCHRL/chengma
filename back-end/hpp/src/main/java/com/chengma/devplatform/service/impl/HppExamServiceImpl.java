package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppCourseMeal;
import com.chengma.devplatform.domain.HppExam;
import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppExamRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppExamMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/14.
 */
@Service
@Transactional
public class HppExamServiceImpl implements HppExamService {

    private final HppExamRepository hppExamRepository;

    private final HppExamMapper hppExamMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private HppCourseMealService hppCourseMealService;

    @Autowired
    private CourseMealService courseMealService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserService userService;

    @Autowired
    private WxOrderService wxOrderService;

    public HppExamServiceImpl(HppExamRepository hppExamRepository, HppExamMapper hppExamMapper){
        this.hppExamRepository=hppExamRepository;
        this.hppExamMapper=hppExamMapper;
    }

    @Override
    public Page<HppExamDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String trainName = formParams.get("trainName") == null ? "" : formParams.get("trainName").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_hpp_exam where 1=1 ");

        //非超级管理员
       /* User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and c_create_by='"+currentUser.getId()+"'");
        }*/

        if(StringUtils.isNotBlank(trainName)){
            cond.append(" and c_train_name like '%"+trainName+"%' ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(HppVideo.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppExamDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, HppExamDTO.class);
        return page;
    }

    @Override
    public HppExamDTO save(HppExamDTO hppExamDTO) {
        return hppExamMapper.toDto(hppExamRepository.save(hppExamMapper.toEntity(hppExamDTO)));
    }

    @Override
    public HppExamDTO createHppExamDTO(HppExamDTO hppExamDTO) {
        //添加
        if(StringUtils.isBlank(hppExamDTO.getId())){
            Date now = new Date();
            hppExamDTO.setCreateTime(now);
            hppExamDTO.setUpdateTime(now);
            hppExamDTO.setExchangeNum(0);
        }else{
            //修改
            HppExam hppExam = hppExamRepository.findOne(hppExamDTO.getId());
            hppExamDTO.setCreateTime(hppExam.getCreateTime());
            hppExamDTO.setUpdateTime(new Date());
            hppExamDTO.setExchangeNum(hppExam.getExchangeNum() == null? 0 : hppExam.getExchangeNum());
        }
        User user = userService.getUserWithAuthorities();
        hppExamDTO.setCreateBy(user.getId());

        HppExamDTO currentHppExamDTO = hppExamMapper.toDto(hppExamRepository.save(hppExamMapper.toEntity(hppExamDTO)));

        //保存套餐
        for(HppCourseMealDTO hppExamMealDTO :hppExamDTO.getMealList()){

            if(StringUtils.isBlank(hppExamMealDTO.getId())){
                //添加
                Date now = new Date();
                hppExamMealDTO.setCreateTime(now);
                hppExamMealDTO.setUpdateTime(now);
                if(hppExamMealDTO.getSort() == null) hppExamMealDTO.setSort(99);
                HppCourseMealDTO hppExamMealDTO1 = hppCourseMealService.save(hppExamMealDTO);

                //保存课程套餐关联
                CourseMealDTO courseMealDTO =new CourseMealDTO();
                courseMealDTO.setCourseId(currentHppExamDTO.getId());
                courseMealDTO.setCourseMealId(hppExamMealDTO1.getId());
                courseMealService.save(courseMealDTO);
            }else{
                HppCourseMealDTO hppExamMealDTO2 = hppCourseMealService.findOne(hppExamMealDTO.getId());
                hppExamMealDTO.setCreateTime(hppExamMealDTO2.getCreateTime());
                hppExamMealDTO.setUpdateTime(new Date());
                hppCourseMealService.save(hppExamMealDTO);
            }
        }
        return currentHppExamDTO;
    }

    @Override
    public HashMap<String, Object> checkCreateHppExamDTO(HppExamDTO hppExamDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        //没有check套餐
        if(StringUtils.isBlank(hppExamDTO.getTrainName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入证件名称");
            return retMap;
        }

        if(StringUtils.isBlank(hppExamDTO.getTrainTime())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入考证时长");
            return retMap;
        }

       /* if(StringUtils.isBlank(hppExamDTO.getHotTag())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入热门标签");
            return retMap;
        }*/

        if(StringUtils.isBlank(hppExamDTO.getContext())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入详情");
            return retMap;
        }

        if(StringUtils.isBlank(hppExamDTO.getAddress())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入考试地点");
            return retMap;
        }

        /*if(StringUtils.isBlank(hppExamDTO.getTeacher())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入授课老师");
            return retMap;
        }
*/
        /*if(StringUtils.isBlank(hppExamDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入课程类型");
            return retMap;
        }*/

        if(StringUtils.isBlank(hppExamDTO.getIntroduction())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入证件介绍");
            return retMap;
        }

        if(hppExamDTO.getMealList() == null || hppExamDTO.getMealList().size() == 0){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入考证套餐");
            return retMap;
        }

        for(HppCourseMealDTO hppExamMealDTO:hppExamDTO.getMealList()){
            if(StringUtils.isBlank(hppExamMealDTO.getName())){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请输入套餐");
                return retMap;
            }
            /*if(StringUtils.isBlank(hppExamMealDTO.getInclude())){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请输入套餐包含");
                return retMap;
            }*/
            if(hppExamMealDTO.getPrice() == null ||hppExamMealDTO.getPrice()<0){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请输入有效价格");
                return retMap;
            }
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppExamDTO findOne(String id) {
        return hppExamMapper.toDto(hppExamRepository.findOne(id));
    }

    @Override
    public HppExamDTO findOneDetailed(String id) {
        HppExamDTO hppExamDTO = this.findOne(id);

        WxOrderDTO wxOrderDTO = wxOrderService.countNum(id);
        if(wxOrderDTO != null){
            Integer total = wxOrderDTO.getTotal() == null ? 0 : wxOrderDTO.getTotal();
            Integer monthTotal = wxOrderDTO.getMonthTotal() == null ? 0 : wxOrderDTO.getMonthTotal();
            hppExamDTO.setTotal(DevplatformConstants.APPLY_EXAM+ total);
            hppExamDTO.setMonthTotal(monthTotal);
        }

        //获取套餐
        String sql = "SELECT\n" +
                "\tt3.*\n" +
                "FROM\n" +
                "\tt_hpp_exam t1,\n" +
                "\tt_course_meal t2,\n" +
                "\tt_hpp_course_meal t3\n" +
                "WHERE\n" +
                "\tt1.c_id = t2.c_course_id\n" +
                "AND t3.c_id = t2.c_course_meal_id\n" +
                "AND t1.c_id = '"+id+"'\n"+
                "\torder by t3.i_sort asc ";
        List<HppCourseMealDTO> list = baseDao.findListBySql(sql,HppCourseMealDTO.class);
        hppExamDTO.setMealList(list);
        return hppExamDTO;
    }

    @Override
    public List<HppExamDTO> findAll() {
        List<HppExamDTO> list =  hppExamMapper.toDto(hppExamRepository.findAll());
        for(HppExamDTO hppExamDTO : list){
            String sql = "SELECT\n" +
                    "\tt3.*\n" +
                    "FROM\n" +
                    "\tt_hpp_exam t1,\n" +
                    "\tt_course_meal t2,\n" +
                    "\tt_hpp_course_meal t3\n" +
                    "WHERE\n" +
                    "\tt1.c_id = t2.c_course_id\n" +
                    "AND t3.c_id = t2.c_course_meal_id\n" +
                    "AND t1.c_id = '"+hppExamDTO.getId()+"'";
            List<HppCourseMealDTO> hppExamMealDTOList = baseDao.findListBySql(sql,HppCourseMealDTO.class);
            hppExamDTO.setMealList(hppExamMealDTOList);
        }
        return list;
    }

    @Override
    public void delete(String id) {
        hppExamRepository.delete(id);
    }

    @Override
    public HppExamDTO findByCourseMealId(String courseMealId) {
        CourseMealDTO courseMealDTO = courseMealService.findByCourseMealId(courseMealId);
        HppExamDTO hppExamDTO = null;
        if(courseMealDTO != null){
            hppExamDTO = this.findOne(courseMealDTO.getCourseId());
        }
        //套餐
        HppCourseMealDTO hppCourseMealDTO = hppCourseMealService.findOne(courseMealId);
        List<HppCourseMealDTO> list = new ArrayList<>();
        list.add(hppCourseMealDTO);
        hppExamDTO.setMealList(list);
        return hppExamDTO;
    }

    @Override
    public void deleteCourseMealById(String courseMealId) {
        //删除套餐关联
        courseMealService.deleteByCourseMealId(courseMealId);
        //删除套餐
        hppCourseMealService.delete(courseMealId);
    }
}