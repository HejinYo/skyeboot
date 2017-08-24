package cn.hejinyo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/20 19:00
 * @Description :
 */
public class BaseTest {
    @Test
    public void testJWT() throws UnsupportedEncodingException {
        String to = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqd3TmiYDpnaLlkJHnmoTnlKjmiLciLCJhdWQiOiLmjqXmlLZqd3TnmoTkuIDmlrkiLCJ1aWQiOjEyMywibmJmIjoxNTAzMjMwODQ1LCJpc3MiOiJza3llIiwiZXhwIjoxNTAzMjMwODQ1LCJpYXQiOjE1MDMyMzA4NDUsImp0aSI6Im5vMSJ9.n0db7rZVAgiHP-beiq5o9Nf8sMLBxAiOagtXBtTq3J8";
        String token = JWT.create().withIssuer("skye").withSubject("jwt所面向的用户").
                withAudience("接收jwt的一方").withExpiresAt(new Date()).withNotBefore(new Date()).
                withIssuedAt(new Date()).withJWTId("no1").withClaim("uid", 123).
                sign(Algorithm.HMAC256("123"));
        DecodedJWT jd = JWT.decode(to);
        String s = jd.getToken();
        String s1 = jd.getId();
        System.out.println(token);
        System.out.println(s1);
        System.out.println(jd.getAudience().get(0));
        System.out.println(jd.getClaim("uid").asInt());
        //JWT.require(Algorithm.HMAC256("123")).build().verify(to);
    }

    @Test
    public void testUUID() {
        System.out.println(12 * 60 * 60 * 1000);
        System.out.println(UUID.randomUUID());
    }

}
