package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.repository.HppCourseRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppCourseMapper;
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
public class HppCourseServiceImpl implements HppCourseService {

    private final HppCourseRepository hppCourseRepository;

    private final HppCourseMapper hppCourseMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private HppCourseMealService hppCourseMealService;

    @Autowired
    private CourseMealService courseMealService;

    @Autowired
    private WxOrderService wxOrderService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserService userService;

    public HppCourseServiceImpl(HppCourseRepository hppCourseRepository, HppCourseMapper hppCourseMapper){
        this.hppCourseRepository=hppCourseRepository;
        this.hppCourseMapper=hppCourseMapper;
    }

    @Override
    public Page<HppCourseDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String trainName = formParams.get("trainName") == null ? "" : formParams.get("trainName").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_hpp_course where 1=1 ");

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

        Page<HppCourseDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, HppCourseDTO.class);
        return page;
    }

    @Override
    public HppCourseDTO save(HppCourseDTO hppCourseDTO) {
        return hppCourseMapper.toDto(hppCourseRepository.save(hppCourseMapper.toEntity(hppCourseDTO)));
    }

    @Override
    public HppCourseDTO createHppCourseDTO(HppCourseDTO hppCourseDTO) {
        //添加
        if(StringUtils.isBlank(hppCourseDTO.getId())){
            Date now = new Date();
            hppCourseDTO.setCreateTime(now);
            hppCourseDTO.setUpdateTime(now);
            hppCourseDTO.setExchangeNum(0);
        }else{
            //修改
            HppCourse hppCourse = hppCourseRepository.findOne(hppCourseDTO.getId());
            hppCourseDTO.setCreateTime(hppCourse.getCreateTime());
            hppCourseDTO.setUpdateTime(new Date());
            hppCourseDTO.setExchangeNum(hppCourse.getExchangeNum() == null ? 0 : hppCourse.getExchangeNum());
        }
        User user = userService.getUserWithAuthorities();
        hppCourseDTO.setCreateBy(user.getId());

        HppCourseDTO currentHppCourseDTO = hppCourseMapper.toDto(hppCourseRepository.save(hppCourseMapper.toEntity(hppCourseDTO)));

        //保存套餐
        for(HppCourseMealDTO hppCourseMealDTO :hppCourseDTO.getMealList()){

            if(StringUtils.isBlank(hppCourseMealDTO.getId())){
                //添加
                Date now = new Date();
                hppCourseMealDTO.setCreateTime(now);
                hppCourseMealDTO.setUpdateTime(now);
                if(hppCourseMealDTO.getSort() == null) hppCourseMealDTO.setSort(99);
                HppCourseMealDTO hppCourseMealDTO1 = hppCourseMealService.save(hppCourseMealDTO);

                //保存课程套餐关联
                CourseMealDTO courseMealDTO =new CourseMealDTO();
                courseMealDTO.setCourseId(currentHppCourseDTO.getId());
                courseMealDTO.setCourseMealId(hppCourseMealDTO1.getId());
                courseMealService.save(courseMealDTO);
            }else{
                HppCourseMealDTO hppCourseMealDTO2 = hppCourseMealService.findOne(hppCourseMealDTO.getId());
                hppCourseMealDTO.setCreateTime(hppCourseMealDTO2.getCreateTime());
                hppCourseMealDTO.setUpdateTime(new Date());
                hppCourseMealService.save(hppCourseMealDTO);
            }
        }
        return currentHppCourseDTO;
    }

    @Override
    public HashMap<String, Object> checkCreateHppCourseDTO(HppCourseDTO hppCourseDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        //没有check套餐
        if(StringUtils.isBlank(hppCourseDTO.getTrainName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入课程名称");
            return retMap;
        }

        if(StringUtils.isBlank(hppCourseDTO.getTrainTime())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入培训时长");
            return retMap;
        }

        if(StringUtils.isBlank(hppCourseDTO.getHotTag())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入热门标签");
            return retMap;
        }

        if(StringUtils.isBlank(hppCourseDTO.getContext())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入详情");
            return retMap;
        }

        if(StringUtils.isBlank(hppCourseDTO.getTeacher())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入授课老师");
            return retMap;
        }

        if(StringUtils.isBlank(hppCourseDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入课程类型");
            return retMap;
        }

        if(StringUtils.isBlank(hppCourseDTO.getIntroduction())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入课程介绍");
            return retMap;
        }

        if(hppCourseDTO.getMealList() == null || hppCourseDTO.getMealList().size() == 0){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入课程套餐");
            return retMap;
        }

        for(HppCourseMealDTO hppCourseMealDTO:hppCourseDTO.getMealList()){
            if(StringUtils.isBlank(hppCourseMealDTO.getName())){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请输入套餐");
                return retMap;
            }
            /*if(StringUtils.isBlank(hppCourseMealDTO.getInclude())){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请输入套餐包含");
                return retMap;
            }*/
            if(hppCourseMealDTO.getPrice() == null ||hppCourseMealDTO.getPrice()<0){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请输入有效价格");
                return retMap;
            }
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppCourseDTO findOne(String id) {
        return hppCourseMapper.toDto(hppCourseRepository.findOne(id));
    }

    @Override
    public HppCourseDTO findOneDetailed(String id) {
        HppCourseDTO hppCourseDTO = this.findOne(id);

        WxOrderDTO wxOrderDTO = wxOrderService.countNum(id);
        if(wxOrderDTO != null){
            Integer total = wxOrderDTO.getTotal() == null ? 0 : wxOrderDTO.getTotal();
            Integer monthTotal = wxOrderDTO.getMonthTotal() == null ? 0 : wxOrderDTO.getMonthTotal();
            hppCourseDTO.setTotal(DevplatformConstants.APPLY_COURSE + total);
            hppCourseDTO.setMonthTotal(monthTotal);
        }

        //获取套餐
        String sql = "SELECT\n" +
                "\tt3.*\n" +
                "FROM\n" +
                "\tt_hpp_course t1,\n" +
                "\tt_course_meal t2,\n" +
                "\tt_hpp_course_meal t3\n" +
                "WHERE\n" +
                "\tt1.c_id = t2.c_course_id\n" +
                "AND t3.c_id = t2.c_course_meal_id\n" +
                "AND t1.c_id = '"+id+"'\n"+
                "\torder by t3.i_sort asc ";
        List<HppCourseMealDTO> list = baseDao.findListBySql(sql,HppCourseMealDTO.class);
        hppCourseDTO.setMealList(list);
        return hppCourseDTO;
    }

    @Override
    public List<HppCourseDTO> findAll() {
        List<HppCourseDTO> list =  hppCourseMapper.toDto(hppCourseRepository.findAll());
        for(HppCourseDTO hppCourseDTO : list){
            String sql = "SELECT\n" +
                    "\tt3.*\n" +
                    "FROM\n" +
                    "\tt_hpp_course t1,\n" +
                    "\tt_course_meal t2,\n" +
                    "\tt_hpp_course_meal t3\n" +
                    "WHERE\n" +
                    "\tt1.c_id = t2.c_course_id\n" +
                    "AND t3.c_id = t2.c_course_meal_id\n" +
                    "AND t1.c_id = '"+hppCourseDTO.getId()+"'";
            List<HppCourseMealDTO> hppCourseMealDTOList = baseDao.findListBySql(sql,HppCourseMealDTO.class);
            hppCourseDTO.setMealList(hppCourseMealDTOList);
        }
        return list;
    }

    @Override
    public void delete(String id) {
        List<CourseMealDTO> list = courseMealService.findByCourseId(id);
        if(list !=null){
            for(CourseMealDTO courseMealDTO : list){
                hppCourseMealService.delete(courseMealDTO.getCourseMealId()); //删除套餐
                courseMealService.delete(courseMealDTO.getId()); //删除套餐关联
            }
        }
        hppCourseRepository.delete(id); //删除课程
    }

    @Override
    public HppCourseDTO findByCourseMealId(String courseMealId) {
        CourseMealDTO courseMealDTO = courseMealService.findByCourseMealId(courseMealId);
        HppCourseDTO hppCourseDTO = null;
        if(courseMealDTO != null){
            hppCourseDTO = this.findOne(courseMealDTO.getCourseId());
        }
        //套餐
        HppCourseMealDTO hppCourseMealDTO = hppCourseMealService.findOne(courseMealId);
        List<HppCourseMealDTO> list = new ArrayList<>();
        list.add(hppCourseMealDTO);
        hppCourseDTO.setMealList(list);
        return hppCourseDTO;
    }

    @Override
    public void deleteCourseMealById(String courseMealId) {
        //删除套餐关联
        courseMealService.deleteByCourseMealId(courseMealId);
        //删除套餐
        hppCourseMealService.delete(courseMealId);
    }
}