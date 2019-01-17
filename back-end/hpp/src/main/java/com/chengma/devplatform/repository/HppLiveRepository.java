package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.BindBank;
import com.chengma.devplatform.domain.HppLive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface HppLiveRepository extends JpaRepository<HppLive,String> {

     List<HppLive> findAllByOrderBySortNumAsc();
}
