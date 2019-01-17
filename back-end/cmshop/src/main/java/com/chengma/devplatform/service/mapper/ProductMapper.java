package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */

import com.chengma.devplatform.domain.MobileUser;
import com.chengma.devplatform.domain.Product;
import com.chengma.devplatform.service.dto.MobileUserDTO;
import com.chengma.devplatform.service.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO,Product> {

}

