package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppCommentDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;

/**
 * Service Interface for managing SysComponent.
 */
public interface HppCommentService {

    /**
     * Save a sysComponent.
     *
     * @param hppCommentDTO the entity to save
     * @return the persisted entity
     */
    HppCommentDTO save(HppCommentDTO hppCommentDTO);

    HashMap<String,Object> checkSave(HppCommentDTO hppCommentDTO);

    /**
     * 根据视频id查询评论
     * @param videoId
     * @return
     */
    HashMap<String,Object>  findForApp(String videoId);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<HppCommentDTO> pageList(HashMap<String, Object> params);


    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HppCommentDTO findOne(String id);



    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);



}
