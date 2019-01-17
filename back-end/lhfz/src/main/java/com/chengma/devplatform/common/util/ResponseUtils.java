package com.chengma.devplatform.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dmchrl on 2018/9/9.
 */
public class ResponseUtils {
  //发送内容
  public static void render(HttpServletResponse response, String contentType, String text){
    response.setContentType(contentType);
    try {
      response.getWriter().write(text);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //发送json
  public static void renderJson(HttpServletResponse response, String text){
    render(response, "application/json;charset=utf-8", text);
  }
  //发送xml
  public static void renderXml(HttpServletResponse response, String text){
    render(response, "text/xml;charset=utf-8", text);
  }
  //发送text
  public static void renderText(HttpServletResponse response, String text){
    render(response, "text/plain;charset=utf-8", text);
  }
}
