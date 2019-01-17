package com.chengma.devplatform.service;

import com.chengma.devplatform.common.dao.ResponseResult;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by ddgui on 2017/10/16.
 */
public interface FileStreamManageService {
    ResponseResult uploadFile(InputStream inputStream, HashMap<String, Object> params);

    public void uploadImage(BASE64Decoder decoder, String str, String imagePath, String prefix, String newFileName);

    void uploadVideo(MultipartFile file, String videoPath, String newFileName);

     ResponseResult downloadFileFromLinux(String directory,String remoteFileName,String localFile);
}
