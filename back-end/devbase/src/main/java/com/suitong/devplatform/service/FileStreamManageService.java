package com.suitong.devplatform.service;

import com.suitong.devplatform.common.dao.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by ddgui on 2017/10/16.
 */
public interface FileStreamManageService {
    ResponseResult uploadFile(InputStream inputStream, HashMap<String, Object> params);
}
