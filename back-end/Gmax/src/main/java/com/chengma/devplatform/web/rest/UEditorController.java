package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.ueditor.ActionEnter;
import com.chengma.devplatform.common.util.ResponseUtils;
import com.chengma.devplatform.service.FileStreamManageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dmchrl on 2018/9/6.
 */
@Controller
public class UEditorController {

  @Autowired
  private Environment environment;

  @Autowired
  private FileStreamManageService fileStreamManageService;


  /**
    * ueditor文件上传（上传到外部服务器）
    * @param request
     * @param response
     * @param action
     */
  @ResponseBody
  @RequestMapping(value="/ueditorUpload", method={RequestMethod.GET, RequestMethod.POST})
  public void editorUpload(HttpServletRequest request, HttpServletResponse response, String action) throws Exception{

    response.setContentType("application/javascript");
    String rootPath = request.getSession().getServletContext().getRealPath("/");
    /*response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");*/

    if("config".equals(action)){    //如果是初始化
      String exec = new ActionEnter(request, rootPath).exec();
      //exec = exec.replace("\\","");
      PrintWriter writer = response.getWriter();
      writer.write(exec);
      writer.flush();
      writer.close();
    }else if("uploadimage".equals(action) || "uploadvideo".equals(action) || "uploadfile".equals(action)){    //如果是上传图片、视频、和其他文件
      try {
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)(request);
//        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        MultipartFile file = Murequest.getFile("upfile");
        String ueditorWebPath = environment.getProperty("fileManage.ueditorWebPath");
        String ueditorPath = environment.getProperty("fileManage.uploadPath");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        ueditorPath = ueditorPath + sdf.format(new Date()) + "/";

        //for(MultipartFile pic: files.values()){
          JSONObject jo = new JSONObject();
          long size = file.getSize();    //文件大小
          String originalFilename = file.getOriginalFilename();  //原来的文件名
          String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
          String newFileName = String.valueOf(System.currentTimeMillis()) + suffixName ;
          try {
            fileStreamManageService.uploadVideo(file,ueditorPath,newFileName);
            String url= ueditorWebPath+sdf.format(new Date()) + "/"+newFileName;
            jo.put("state", "SUCCESS");
            jo.put("original", originalFilename);//原来的文件名
            jo.put("size", size);//文件大小
            jo.put("title", originalFilename);//随意，代表的是鼠标经过图片时显示的文字
            jo.put("type", suffixName);//文件后缀名
            jo.put("url", url);//这里的url字段表示的是上传后的图片在图片服务器的完整地址（http://ip:端口*//****///****/*//***.jpg）*/
          } catch (IllegalStateException e) {
            e.printStackTrace();
          }

         // }
          ResponseUtils.renderJson(response, jo.toString());
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
