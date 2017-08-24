package cn.hejinyo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/19 17:31
 * @Description :
 */
public class ResponseUtils {

    //response 返回json格式Return数据
    public static void response(HttpServletResponse httpResponse, int statusCode, Result returns) {
        httpResponse.setStatus(statusCode);
        httpResponse.setCharacterEncoding("UTF-8"); //设置编码格式
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);//设置ContentType，返回内容的MIME类型
        httpResponse.setHeader("Cache-Control", "no-cache");//告诉所有的缓存机制是否可以缓存及哪种类型
        String json = JsonUtils.toJSONString(returns);
        try {
            httpResponse.getWriter().write(json);
            httpResponse.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void response(ServletResponse response, int statusCode, Result returns) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response(httpResponse, statusCode, returns);
    }

    public static void response(ServletResponse response, Result returns) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response(httpResponse, HttpStatus.OK.value(), returns);
    }
}
