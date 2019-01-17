package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.PaymentImgDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface PaymentImgService {

    /**
     * Save a sysComponent.
     *
     * @param paymentImgDTO the entity to save
     * @return the persisted entity
     */
    PaymentImgDTO save(PaymentImgDTO paymentImgDTO);

    HashMap<String, Object>  checkPaymentImgDTO(PaymentImgDTO paymentImgDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<PaymentImgDTO> pageList(HashMap<String, Object> params);

    List<PaymentImgDTO> findAll();


    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentImgDTO findOne(String id);



    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    /**
     * 獲取最新支付圖片
     * @return
     */
    PaymentImgDTO findNewImg();

    /**
     * 設置為最新支付圖片
     * @param id
     * @return
     */
    PaymentImgDTO setImgFlag(String id);



}
