package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.BindBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@SuppressWarnings("unused")
@Repository
public interface BindBankRepository extends JpaRepository<BindBank,String> {
    
}
