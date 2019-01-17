package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.util.StringUtil;
import com.chengma.devplatform.domain.SerialNo;
import com.chengma.devplatform.repository.SerialNoRepository;
import com.chengma.devplatform.service.IGenerateSerialNo;

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
        Long serial = serialNo.getSerial(); //流水数
        serial += 1;
        serialNo.setDate(now);
        serialNo.setSerial(serial);
        String fullNo = StringUtil.fitZero(serial.toString(), serialNo.getBit()); //生成除开头外后面的数字
        String result = serialNo.getFormatter().replace("#SERIAL#", fullNo);//拼成唯一的账号
        serialNoRepository.save(serialNo);
        return result;
    }

}
