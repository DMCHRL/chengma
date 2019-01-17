package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppExamService;
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
 * REST controller for managingHppExam.
 */
@RestController
@RequestMapping("/api")
public class HppExamResource {

    private final Logger log = LoggerFactory.getLogger(HppExamResource.class);

    private static final String ENTITY_NAME = "hppExam";

    private final HppExamService hppExamService;

    public HppExamResource(HppExamService hppExamService) {
        this.hppExamService =hppExamService;
    }


    /**
     * 保存考证信息
     * @param hppExamDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/hpp_exam/saveHppExam")
    @Timed
    public ResponseEntity<ResponseResult> saveHppExam(@RequestBody HppExamDTO hppExamDTO) throws URISyntaxException {
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> checkMap = hppExamService.checkCreateHppExamDTO(hppExamDTO);
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }

        log.debug("REST request to save HppExam : {}", hppExamDTO);
        HppExamDTO hppExamDTO1 = hppExamService.createHppExamDTO(hppExamDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppExamDTO1);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据id获取实体
     * @param courseMealId
     * @return
     */
    @GetMapping("/hpp_exam/deleteCourseMeal/{courseMealId}")
    @Timed
    public ResponseEntity<ResponseResult> deleteCourseMeal(@PathVariable String courseMealId){
        log.debug("REST request to get one of HppCourse");
        hppExamService.deleteCourseMealById(courseMealId);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * Post  .
     *
     * @param params the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @PostMapping("/hpp_exam/pageList")
    @Timed
    public ResponseEntity<ResponseResult> pageList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of HppExam");
        Page<HppExamDTO> page = hppExamService.pageList(params);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hpp_exam");
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
    @GetMapping("/hpp_exam/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id){
        log.debug("REST request to get one of HppExam");
        HppExamDTO hppExamDTO = hppExamService.findOneDetailed(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(hppExamDTO);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

   /* @GetMapping("/hpp_exam/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> delete(@PathVariable String id) {
        log.debug("REST request to delete HppExam : {}", id);
        ResponseResult json = new ResponseResult();
        Long applyNum=hppExamService.applyCount(id);
        if(applyNum>0){
            json.setStatusCode(ResponseResult.FAIL_CODE);
            json.setMsgCode("该考证已有"+applyNum+"人报名参加,不能删除。");
        }else{
            hppExamService.delete(id);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }*/


    /**
     * Get  .
     *act on app
     * @return the ResponseEntity with status 200 (OK) and the list of sysComponents in body
     */
    @GetMapping("/hpp_exam/findAll")
    @Timed
    public ResponseEntity<ResponseResult> findAll() {
        log.debug("REST request to get a list of HppExam");
        List<HppExamDTO> list = hppExamService.findAll();
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", list);
        retMap.put("total", list.size());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据套餐id获取考证实体实体
     * @param courseMealId
     * @return
     */
    @GetMapping("/hpp_exam/findByCourseMealId/{courseMealId}")
    @Timed
    public ResponseEntity<ResponseResult> findByCourseMealId(@PathVariable String courseMealId) {
        log.debug("REST request to findByCourseMealId of HppExam");
        HppExamDTO hppExamDTO = hppExamService.findByCourseMealId(courseMealId);
        ResponseResult json = new ResponseResult();
        json.setData(hppExamDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }



}
