package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.util.StringUtil;
import com.suitong.devplatform.domain.SerialNo;
import com.suitong.devplatform.repository.SerialNoRepository;
import com.suitong.devplatform.service.IGenerateSerialNo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by phwen on 2017/9/18.
 */
public class GenerateStoreSerialNo implements IGenerateSerialNo {

    private SerialNoRepository serialNoRepository;

    public GenerateStoreSerialNo(SerialNoRepository serialNoRepository) {
        this.serialNoRepository = serialNoRepository;
    }

    @Override
    public synchronized String getSerialNo(SerialNo serialNo) {
        Date now = new Date();
        Long serial = serialNo.getSerial();
        serial += 1;
        serialNo.setDate(now);
        serialNo.setSerial(serial);
        String fullNo = StringUtil.fitZero(serial.toString(), serialNo.getBit());
        String result = serialNo.getFormatter().replace("#SERIAL#", fullNo);
        serialNoRepository.save(serialNo);
        return result;
    }

}
