package com.chengma.devplatform.service;

import com.chengma.devplatform.common.dao.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by ddgui on 2017/10/16.
 */
public interface FileManageService {

    ResponseResult uploadFile(MultipartFile file, HashMap<String, Object> params);

    ResponseResult downloadFile(String srcPath, HttpServletResponse response);
}
