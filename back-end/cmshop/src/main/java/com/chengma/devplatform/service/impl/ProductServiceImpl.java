package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.repository.MobileUserRepository;
import com.chengma.devplatform.repository.ProductRepository;
import com.chengma.devplatform.service.MobileUserService;
import com.chengma.devplatform.service.ProductService;
import com.chengma.devplatform.service.dto.MobileUserDTO;
import com.chengma.devplatform.service.dto.ProductDTO;
import com.chengma.devplatform.service.mapper.MobileUserMapper;
import com.chengma.devplatform.service.mapper.ProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;


    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository=productRepository;
        this.productMapper=productMapper;
    }
    
    @Override
    public Page<ProductDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDTO)));
    }

    @Override
    public ProductDTO createProductDTO(ProductDTO productDTO) {
        //添加
        if(StringUtils.isBlank(productDTO.getId())){
            productDTO.setCreateAt(new Date());
        }else{
            //修改
        }
        
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateProductDTO(ProductDTO productDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public ProductDTO findOne(String id) {
        return productMapper.toDto(productRepository.findOne(id));
    }

    @Override
    public List<ProductDTO> findAll() {
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    public List<ProductDTO> findByType(HashMap<String, Object> params) {
        String type = params.get("type") == null ? "" : params.get("type").toString();
        Integer min = params.get("min") == null ? -1 : (Integer) params.get("min");
        Integer max = params.get("max") == null ? -1 : (Integer) params.get("max");

        StringBuffer sql =new StringBuffer("select * from t_product where 1=1 ") ;

        if(StringUtils.isNotBlank(type)){
            sql.append(" and c_type = '"+type+"'");
        }
        if(min >= 0){
            sql.append(" and i_price>="+min+"");
        }
        if(max >= 0){
            sql.append(" and i_price <="+max+"");
        }

        return baseDao.findListBySql(sql.toString(),ProductDTO.class);
    }

    @Override
    public void delete(String id) {
        productRepository.delete(id);
    }
}