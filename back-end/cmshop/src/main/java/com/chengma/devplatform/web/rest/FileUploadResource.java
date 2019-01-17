package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.FileStreamManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing BindBank.
 */
@RestController
@RequestMapping(value = "/api")
public class FileUploadResource {

    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private Environment environment;

    @PostMapping(value = "/upload")
    public String upload(@RequestParam("video") MultipartFile file)throws Exception {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        String filePlayPath = environment.getProperty("fileManage.filePlayPath");
        String videoPath = environment.getProperty("fileManage.videoPath");
        String newFileName = String.valueOf(System.currentTimeMillis()) + suffixName ;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        videoPath = videoPath + sdf.format(new Date()) + "/";
        //如果文件夹不存在则创建
        /*if  (!file .exists()  && !file .isDirectory()) {
            file .mkdir();
        }*/
        try {
            fileStreamManageService.uploadVideo(file,videoPath,newFileName);
            return filePlayPath+sdf.format(new Date()) + "/"+newFileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    @PostMapping(value = "/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile file)throws Exception {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        String filePlayPath = environment.getProperty("fileManage.videoImageWebPath");
        String ImagePath = environment.getProperty("fileManage.imagePath");
        String newFileName = String.valueOf(System.currentTimeMillis()) + suffixName ;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        ImagePath = ImagePath + sdf.format(new Date()) + "/";
        try {
            fileStreamManageService.uploadVideo(file,ImagePath,newFileName);
            return filePlayPath+sdf.format(new Date()) + "/"+newFileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /*@PostMapping(value = "/uploadUEditorImage")
    public String uploadUEditorImage(HttpServletRequest req){
        MultipartHttpServletRequest mReq  =  null;
        MultipartFile file = null;
        InputStream is = null ;
        String fileName = "";
        mReq = (MultipartHttpServletRequest)req;
        // 从config.json中取得上传文件的ID
        file = mReq.getFile("image");
        // 取得文件的原始文件名称
        fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        String filePlayPath = environment.getProperty("fileManage.videoImageWebPath");
        String ImagePath = environment.getProperty("fileManage.imagePath");
        String newFileName = String.valueOf(System.currentTimeMillis()) + suffixName ;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        ImagePath = ImagePath + sdf.format(new Date()) + "/";
        try {
            fileStreamManageService.uploadVideo(file,ImagePath,newFileName);
            return filePlayPath+sdf.format(new Date()) + "/"+newFileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }*/

    /*@PostMapping(value = "/downLoad")
    public ResponseEntity<ResponseResult> upload(@RequestBody HashMap<String,Object> params)throws Exception {
        String localFile = params.get("localFile") == null? "" :(String)params.get("localFile");
        String url = params.get("url") == null? "" : (String)params.get("url");



        String filePlayPath = environment.getProperty("fileManage.filePlayPath");
        String directory = environment.getProperty("fileManage.videoPath");
        directory=directory + url.substring(filePlayPath.length()).split("/")[0]+"/";
        String remoteFileName = url.substring(filePlayPath.length()).split("/")[1];
        ResponseResult json=fileStreamManageService.downloadFileFromLinux( directory, remoteFileName,localFile);
        return new ResponseEntity<ResponseResult>(json,null, HttpStatus.OK);
    }*/
}
