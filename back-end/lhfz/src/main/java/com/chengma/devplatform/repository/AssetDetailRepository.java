package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.Asset;
import com.chengma.devplatform.domain.AssetDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface AssetDetailRepository extends JpaRepository<AssetDetail, String> {

    List<AssetDetail> findByUserIdEquals(String userId);
}
