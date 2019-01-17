package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.service.SysOperateLogService;
import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.common.dao.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/25.
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class SysOperateLogResource {

    private final Logger log = LoggerFactory.getLogger(SysOperateLogResource.class);

    private static final String ENTITY_NAME = "sysOperateLog";

    private final SysOperateLogService sysOperateLogService;


    public SysOperateLogResource(SysOperateLogService sysOperateLogService) {
        this.sysOperateLogService = sysOperateLogService;
    }

    /**
     * post /sysOperateLog/getAllSysOperateLogs ：获取所有的账户。
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{account:"",userName:"",type:"",operateContent:"",startTime:"",endTime:""}}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sysOperateLog/getAllSysOperateLogs")
    @Timed
    public ResponseEntity<ResponseResult> getAllSysOperateLogs(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysOperateLogs");
        ResponseResult json = sysOperateLogService.findAll(params);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sysOperateLog/OssPutObject ：上传文件
     *
     * @param file     文件
     * @param request  HTTP请求
     * @param response HTTP响应
     * @return statusCode:成功0000，失败0100。
     * @throws Exception 异常
     */
    @PostMapping("/sysOperateLog/OssPutObject")
    @Timed
    public HashMap<String, Object> OssPutObject(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap<String, Object> json = new HashMap<String, Object>();

        /*if(file.getSize()>5242880){
            json.put("statusCode", "error");
            json.put("msg", "上传的图片超过5M,请重新上传！");
            return json;
        }

        String FilenameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if(!".JPG".equals(FilenameExtension) && !".jpg".equals(FilenameExtension)&& !".png".equals(FilenameExtension) && !".PNG".equals(FilenameExtension)){
            json.put("statusCode", "false");
            json.put("msg", "上传失败，上传的文件不是JPG、PNG格式文件！");
        }

        BufferedImage src = null;
        int width = -1;
        int height = -1;
        try {
            src = javax.imageio.ImageIO.read(file.getInputStream());
            width = src.getWidth(null); // 得到源图宽
            height = src.getHeight(null); // 得到源图高
            if( width>750 || height>560 ){
                json.put("statusCode", "false");
                json.put("msg", "上传失败，上传的图片文件尺寸应在750px*560px内！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = OssUtil.OssPutObject(OssUtil.ENEPOINT, OssUtil.ACCESS_KEY_ID, OssUtil.ACCESS_KEY_SECRET,
                OssUtil.BUCKET_NAME, OssUtil.ACCESS_URL, file);
        System.out.println(url);
        if("error".equals(url)){
            json.put("statusCode", "false");
            json.put("msg", "上传的文件不是Excel格式文件！");
        }else{
            json.put("statusCode", "true");
            json.put("msg", "上传成功！");
        }*/
        return json;
    }

}
