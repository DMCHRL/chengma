package com.chengma.devplatform.service;

import com.chengma.devplatform.common.dao.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by ddgui on 2017/10/16.
 */
public interface FileStreamManageService {
    ResponseResult uploadFile(InputStream inputStream, HashMap<String, Object> params);
    void uploadVideo(MultipartFile file, String videoPath, String newFileName);
}
