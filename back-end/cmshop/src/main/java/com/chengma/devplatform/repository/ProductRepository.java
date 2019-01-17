package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.MobileUser;
import com.chengma.devplatform.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface ProductRepository extends JpaRepository<Product, String> {

}
