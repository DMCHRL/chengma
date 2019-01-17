package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.MobileUserDTO;
import com.chengma.devplatform.service.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface ProductService {

    Page<ProductDTO> pageList(HashMap<String, Object> params);

    ProductDTO save(ProductDTO hppVideoDTO);

    ProductDTO createProductDTO(ProductDTO hppVideoDTO);

    HashMap<String, Object> checkCreateProductDTO(ProductDTO hppVideoDTO);

    ProductDTO findOne(String id);

    List<ProductDTO> findAll();

    List<ProductDTO> findByType(HashMap<String, Object> params);

    void delete(String id);
}

