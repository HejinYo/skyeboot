package cn.hejinyo.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpRequestUtils {


    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 5000;

    static {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时  
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时  
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时  
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();
    }


    public static String sendPost(String url, Map<String, String> map) {
        return sendPost(url, map, "UTF-8", null, null);
    }


    /**
     * 方法名称: sendPost XML格式数据post请求
     */
    public static String sendPost(String url, Map<String, String> map, String charset, String token_type, String token) {
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            httpPost.setConfig(requestConfig);
            //装填参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            if (map != null) {
                for (Entry<String, String> entry : map.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            //设置参数到请求对象中
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
            //System.out.println("请求地址："+url);
            //System.out.println("请求参数："+nvps.toString());
            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //添加http请求证书
            if (token_type != null && !"".equals(token_type)) {
                httpPost.setHeader("Authorization", token_type + " " + token);
            }
            //执行请求操作，并拿到结果（同步阻塞）
            response = client.execute(httpPost);
            //获取结果实体
            entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, charset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (entity != null) {
                    EntityUtils.consume(entity);
                }
                if (httpPost != null) {
                    httpPost.abort();
                }
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    /**
     * 方法名称: sendPostJson json数据格式的post请求
     */
    public static String sendPostJson(String url, Object json) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            //httpPost.setHeader("Authorization",token_type+" "+token);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            //System.out.println(response.getStatusLine().getStatusCode());
            if (entity != null) {
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (entity != null) {
                    EntityUtils.consume(entity);
                }
                if (httpPost != null) {
                    httpPost.abort();
                }
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return httpStr;
    }

    public static String pictureToByte(String imgPath) {

        File file = new File(imgPath);
        FileInputStream fis;
        StringBuilder str = new StringBuilder();
        byte[] b = null;
        try {
            fis = new FileInputStream(file);
            b = new byte[fis.available()];
            fis.read(b);
            for (byte bs : b) {
                str.append(bs);
            }
            fis.close();
//            File apple = new File("E:/345.txt"); 
//            FileOutputStream fos = new FileOutputStream(apple);    
//            fos.write(b);    
//            fos.flush();    
//            fos.close();
//            System.out.println(str.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public static String getImageBinary(String imgPath) {
        //File f = new File("C://templates//ttt.jpg");  
        File f = new File(imgPath);

        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            baos.close();

            return Base64.encodeBase64String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getImageByFileReader(String imgPath) {
        String str = "";
        try {
            File file = new File(imgPath);
            BufferedReader in = new BufferedReader(new FileReader(file));
            int temp = 0;
            //the all content of this stream read
            while ((temp = in.read()) != -1) {
                str += (char) temp;
            }
            System.out.println("文件内容:" + str);

            File apple = new File("E:/testxx.jpg");
            FileOutputStream fos = new FileOutputStream(apple);
            fos.write(str.toString().getBytes());
            fos.flush();
            fos.close();
            System.out.println(str.toString());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;

    }


    public static void main(String[] args) {

        Map<String, String> map = new HashMap<String, String>();

		/*String url = PropertiesUtils.getStringByKey("msg_url");
        map.put("apikey", PropertiesUtils.getStringByKey("msg_apikey"));
		map.put("mobile", "13268001165");
		map.put("content", MessageFormat.format(PropertiesUtils.getStringByKey("msg_content"), "925612"));
		String result = HttpRequestUtils.sendPost(url, map);
		System.out.println(result);
		JSONObject json = JSONObject.fromObject(result);*/

		/*String url = "http://kyhlinterface.unishep.cn/oauth/token";
        map.put("client_id", "24");
		map.put("client_secret", "NauI6U2k8WOim1lzkxnaxagzHjeeU6NEqoYyiFKS");
		map.put("username", "1126684373@qq.com");
		map.put("password", "15202281729");
		map.put("grant_type", "password");
		System.out.println(HttpRequestUtils.sendPost(url, map));*/

        String url = "http://kyhlinterface.unishep.cn/api/interface?dataType=base64";
        //String url = "http://kyhlinterface.unishep.cn/api/test?dataType=binary";
        //String url = "http://182.90.254.109:12000/api/interface?dataType=binary";
        String imgpath = "E://test.jpg";
        String ss = HttpRequestUtils.getImageBinary(imgpath);
        System.out.println(ss);
        //ss = "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";
        map.put("imgData", "data:image/jpg;base64," + ss);
        map.put("redirect_uri", "");
        map.put("userId", "");
        //JSONObject json = JSONObject.fromObject(map);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjI3OWM3NDczMmJkYmQ3NGQyMGZjM2YyMjdkNTNmYjhkZTdmNTZjZTg2YmZhYzkxNzdlN2UzZTIwNWZmNDVjMDZiNTkxODdiYjdhNzhiMjJkIn0.eyJhdWQiOiIyNCIsImp0aSI6IjI3OWM3NDczMmJkYmQ3NGQyMGZjM2YyMjdkNTNmYjhkZTdmNTZjZTg2YmZhYzkxNzdlN2UzZTIwNWZmNDVjMDZiNTkxODdiYjdhNzhiMjJkIiwiaWF0IjoxNDkzMzQyNzM4LCJuYmYiOjE0OTMzNDI3MzgsImV4cCI6MTQ5MzUxNTUzOCwic3ViIjoiNiIsInNjb3BlcyI6W119.CYn_QH_M4eQQU4WbqX-IRNqxomRcN12BRLRlh5fd1wj2U8W3a8AY0RaQk0zt8Mk9ylzTz7ivUhfB7sInHK0HrQx5v9KpHwzqBC49NQtaBgM7TnF9dGosS7BZ_N6D2PWSJPKV4Y7D8jajY7xwIXpjXwX6yibq0dxSADo_55TDRQ3WkcK6nc8rf0G0Sky9GhSC503UbIW0z4IDZntnpHB5M2IluldImTXgf4c1QkcgU39LM1IwWiYjHc1EG0a3b5aBCZpT61flFqV-O69cXdisn1N7KFa8fUKe8x-BVdTRVZ4IlZVgQfHYKH_e_9arKXCIR0aWMG0c0-IrgReHdzNdk4qQFJLEAxw9qRNqx3INjr1hsaBYCAxh_8vTjXvcrb29n18AAHow0tga4AUMgg4dIeQyDcGOM2ziErMzMPzVol3N2fs7_W9fSNdjiBRPbsi8NQjLGjXs0u2edzzIcUQiDCJxqO9VLRU1d9DJAVS-enAdP0dpRkfmjsQYLKOtGF-b26cA47qId_TSEKj7kqs1ANMM-OwNr8OIC9X0wXjFtN9h8T7rZaf3Lp7mnY73NMpI7Y2uUWr219cU9pHRcnaXEfEgIVRispczwNgl-eRU0fEzc_4Q6mV8-S8_Q7kq06s7fe3qgQOc_9dvJjUa4_1RphDBdpOo4eAmSKWIyEfnIyE";
        //System.out.println(HttpRequestUtils.sendPostJson(url, json, "Bearer", token));
        System.out.println(HttpRequestUtils.sendPost(url, map, "UTF-8", "Bearer", token));

    }

}
