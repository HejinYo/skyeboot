package cn.hejinyo.utils;

import cn.hejinyo.consts.UserToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jodd.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 字符串，字符，日期 工具类
 *
 * @author HejinYo
 * @version 1.0
 * @email hejinyo@gmail.com
 * @since 1.0
 */
public class Tools {

    /**
     * BASE64编码
     *
     * @param str String
     */
    public static String base64Encode(String str) {
        return Base64.encodeToString(str);
    }

    /**
     * BASE64编码
     *
     * @param arr byte[]
     */
    public static String base64Encode(byte[] arr) {
        return Base64.encodeToString(arr);
    }

    /**
     * BASE64解码
     */
    public static String base64Decoder(String str) {
        return Base64.decodeToString(str);
    }

    /**
     * 数据库密码加密
     *
     * @param password
     * @return
     * @throws IOException
     */
    public static String[] encryptDBPassword(String password) {
        String path = "D:/java/jdk/";
        String druid = "druid-1.0.16.jar com.alibaba.druid.filter.config.ConfigTools ";
        String fileInfo = "java -cp " + path + druid + password + " ;exit;";
        String pw[] = new String[3];
        try {
            Process proc = Runtime.getRuntime().exec(fileInfo);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader in = new BufferedReader(is);
            pw[1] = in.readLine();//privateKey
            pw[2] = in.readLine();//publicKey
            pw[0] = in.readLine();//encryptPassword
            in.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pw;
    }

    /**
     * HmacSHA256 加密，返回 base64编码
     *
     * @param key     密钥
     * @param content 内容
     * @return
     */
    public static String hmacSHA256Digest(String key, String content) {
        try {
            //创建加密对象
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            //处理密钥Key
            SecretKey secret_key = new SecretKeySpec(key.getBytes("utf-8"), "HmacSHA256");
            //初始化加密
            sha256_HMAC.init(secret_key);
            //加密内容
            byte[] doFinal = sha256_HMAC.doFinal(content.getBytes("utf-8"));
            //16进制内容
            // byte[] hexB = new Hex().encode(doFinal);
            //base64编码内容
            return base64Encode(doFinal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * byte 转 字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString();
    }

    /**
     * 创建jwt token
     *
     * @param expires  n小时后失效
     * @param userid
     * @param username
     * @param password
     * @return
     */
    public static String createToken(int expires, int userid, String username, String password) {
        String token = "";
        try {
            token = JWT.create().
                    withIssuedAt(new Date()).
                    withExpiresAt(new Date(System.currentTimeMillis() + (expires * 60 * 60 * 1000))).
                    withClaim(UserToken.USERID.getValue(), userid).
                    withClaim(UserToken.USERNAME.getValue(), username).
                    sign(Algorithm.HMAC256(password));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 获得token 信息
     *
     * @param token
     * @param key
     * @return
     */
    public static String getTokenInfo(String token, String key) {
        DecodedJWT dJWT = JWT.decode(token);
        return dJWT.getClaim(key).asString();
    }

    /**
     * 验证token 有效性
     *
     * @param token
     * @param password
     * @throws UnsupportedEncodingException
     */
    public static void verifyToken(String token, String password) throws UnsupportedEncodingException {
        JWT.require(Algorithm.HMAC256(password)).build().verify(token);
    }


    /**************************** 测试 *********************************/
    public static void main(String agrs[]) {

    }
}
