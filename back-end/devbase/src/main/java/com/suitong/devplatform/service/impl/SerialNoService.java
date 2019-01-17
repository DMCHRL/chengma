package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.dao.BaseDao;
import com.suitong.devplatform.domain.SerialNo;
import com.suitong.devplatform.repository.SerialNoRepository;
import com.suitong.devplatform.service.IGenerateSerialNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by phwen on 2017/9/18.
 */
@Service
@Transactional
public class SerialNoService {

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SerialNoRepository serialNoRepository;

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public String getStoreSerialNo() {
        SerialNo serialNo = this.querySerialNoByType("01");
        IGenerateSerialNo gsn = new GenerateStoreSerialNo(serialNoRepository);
        return gsn.getSerialNo(serialNo);
    }

    private SerialNo querySerialNoByType(String type) {
        StringBuffer sql = new StringBuffer();
        sql.append("select s.* from t_serial_no s where s.c_type = '").append(type).append("'");
        List<SerialNo> list = baseDao.findListBySql(sql.toString(), "", SerialNo.class);
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public String getRewardSerialNo() {
        SerialNo serialNo = this.querySerialNoByType("02");
        IGenerateSerialNo gsn = new GenerateStoreSerialNo(serialNoRepository);
        return gsn.getSerialNo(serialNo);
    }

}
