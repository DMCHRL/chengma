package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppCourseService;
import com.chengma.devplatform.service.dto.HppCourseDTO;
import com.chengma.devplatform.service.dto.HppExamDTO;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managingHppCourse.
 */
@RestController
@RequestMapping("/api")
public class HppCourseResource {

    private final Logger log = LoggerFactory.getLogger(HppCourseResource.class);

    private static final String ENTITY_NAME = "hppCourse";

    private final HppCourseService hppCourseService;

    public HppCourseResource(HppCourseService hppCourseService) {
        this.hppCourseService =hppCourseService;
    }


    /**
     * 保存培训信息
     * @param hppCourseDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_course/saveHppCourse")
    @Timed
    public ResponseEntity<ResponseResult> saveHppCourse(@RequestBody HppCourseDTO hppCourseDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppCourseService.checkCreateHppCourseDTO(hppCourseDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save HppCourse : {}", hppCourseDTO);
        HppCourseDTO hppCourseDTO1 = hppCourseService.createHppCourseDTO(hppCourseDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppCourseDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_course/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppCourse");
        Page<HppCourseDTO> page = hppCourseService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_course");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    @GetMapping("/hpp_course/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id){
        log.debug("REST request to get one of HppCourse");
        HppCourseDTO hppCourseDTO = hppCourseService.findOneDetailed(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppCourseDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 删除套餐
     * @param courseMealId
     * @return
     */
    @GetMapping("/hpp_course/deleteCourseMeal/{courseMealId}")
    @Timed
    public ResponseEntity<ResponseResult> deleteCourseMeal(@PathVariable String courseMealId){
        log.debug("REST request to get one of HppCourse");
        hppCourseService.deleteCourseMealById(courseMealId);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /*@GetMapping("/hpp_course/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppCourse : {}", id);
        ResponseResult json = new ResponseResult();
        hppCourseService.delete(id);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/


    /**
     * Post  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_course/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to get a list of HppCourse");
        List<HppCourseDTO> list = hppCourseService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据套餐id获取课程实体实体
     * @param courseMealId
     * @return
     */
    @GetMapping("/hpp_course/findByCourseMealId/{courseMealId}")
    @Timed
    public ResponseEntity<ResponseResult> findByCourseMealId(@PathVariable String courseMealId) {
        log.debug("REST request to findByCourseMealId of HppCourse");
        HppCourseDTO hppCourseDTO = hppCourseService.findByCourseMealId(courseMealId);
        ResponseResult json = new ResponseResult();
        json.setData(hppCourseDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
