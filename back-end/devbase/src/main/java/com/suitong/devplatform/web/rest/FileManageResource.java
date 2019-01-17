package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.FileManageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ddgui on 2017/10/16.
 */
@RestController
@RequestMapping("/api")
public class FileManageResource {

    private final Logger log = LoggerFactory.getLogger(FileManageResource.class);

    @Autowired
    private FileManageService fileManageService;
    @Autowired
    private Environment env;

    @PostMapping("/uploadFile")
    @Timed
    public ResponseEntity<ResponseResult> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam HashMap<String, Object> params) {
        log.debug("REST request to get a page of activitySchedule");

        ResponseResult response = new ResponseResult();
        if (!file.isEmpty()) {
            String filePath = env.getProperty("fileManage.uploadPath");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            filePath = filePath + sdf.format(new Date()) + "/";
            String systemType = env.getProperty("serverConfig.systemType");
            // 获取图片的文件名
            String fileName = file.getOriginalFilename();
            // 获取图片的扩展名
            String extensionName = StringUtils.substringAfter(fileName, ".");
            // 新的图片文件名 = 获取时间戳+"."图片扩展名
            String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
            params.put("uploadPath",filePath);
            params.put("newFileName",newFileName);
            params.put("systemType",systemType);
            response = fileManageService.uploadFile(file,params);
            return new ResponseEntity<>(response, null, HttpStatus.OK);
        } else {
            response.setMsgCode("reselection");//上传失败，请选择要上传的图片!
            return new ResponseEntity<>(response, null, HttpStatus.OK);
        }
    }

    @GetMapping(value="/downloadFile/{srcPath}")
    @Timed
    public ResponseEntity<ResponseResult> downloadFile(@PathVariable String srcPath,HttpServletResponse response) {
        log.debug("REST request to get a page of activitySchedule");
        ResponseResult json = fileManageService.downloadFile(srcPath,response);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @GetMapping(value="/downloadFile2/{params}")
    @Timed
    public ResponseEntity<byte[]> downloadFile2(@PathVariable Object params) throws IOException{
        log.debug("REST request to get a page of activitySchedule");
      /*  try {
            srcPath = URLDecoder.decode(srcPath,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String fileName = srcPath.substring(srcPath.lastIndexOf("/")+1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        InputStream in = null;
        byte[] buff = new byte[100];
        byte[] in2b = null;
        try {
            in =  new URL(srcPath).openStream();
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            in2b = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return new ResponseEntity<byte[]>(null,null, HttpStatus.CREATED);
    }

}
