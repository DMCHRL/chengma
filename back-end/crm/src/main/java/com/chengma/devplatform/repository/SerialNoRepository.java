package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SerialNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by phwen on 2017/9/18.
 */
@SuppressWarnings("unused")
@Repository
public interface SerialNoRepository extends JpaRepository<SerialNo,String> {

}
