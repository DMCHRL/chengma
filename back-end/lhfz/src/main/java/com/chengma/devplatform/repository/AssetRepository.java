package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.AllotRule;
import com.chengma.devplatform.domain.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface AssetRepository extends JpaRepository<Asset, String> {

    Asset findByUserIdEquals(String userId);

}
