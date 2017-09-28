package cn.hejinyo;

import cn.hejinyo.model.dto.RolePermissionTreeDTO;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
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

    @Test
    public void json() {
        String json = "[{\"resName\":\"人员修改\",\"permId\":13,\"resCode\":\"user:update\",\"permCode\":\"update\",\"type\":\"permission\",\"permName\":\"人员修改\",\"resId\":1},{\"resName\":\"人员删除\",\"permId\":12,\"resCode\":\"user:delete\",\"permCode\":\"delete\",\"type\":\"permission\",\"permName\":\"人员删除\",\"resId\":1},{\"resName\":\"人员管理查看\",\"permId\":4,\"resCode\":\"user:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"人员管理查看\",\"resId\":1},{\"resName\":\"人员添加\",\"permId\":1,\"resCode\":\"user:create\",\"permCode\":\"create\",\"type\":\"permission\",\"permName\":\"人员添加\",\"resId\":1},{\"resName\":\"资源权限管理查看\",\"permId\":5,\"resCode\":\"permission:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"资源权限管理查看\",\"resId\":2},{\"resName\":\"开发工具查看\",\"permId\":2,\"resCode\":\"tools:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"开发工具查看\",\"resId\":3},{\"resName\":\"clip查看\",\"permId\":7,\"resCode\":\"clip:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"clip查看\",\"resId\":4},{\"resName\":\"jqGrid查看\",\"permId\":8,\"resCode\":\"jqGrid:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"jqGrid查看\",\"resId\":5},{\"resName\":\"测试查看\",\"permId\":10,\"resCode\":\"test:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"测试查看\",\"resId\":8},{\"resName\":\"Poshy tip\",\"permId\":6,\"resCode\":\"Poshy tip:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"Poshy tip\",\"resId\":6},{\"resName\":\"资源管理查看\",\"permId\":9,\"resCode\":\"resource:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"资源管理查看\",\"resId\":7},{\"resName\":\"41-view\",\"permId\":11,\"resCode\":\"41:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"41-view\",\"resId\":9},{\"resName\":\"sql监控\",\"permId\":3,\"resCode\":\"sql:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"sql监控\",\"resId\":10},{\"resName\":\"角色管理查看\",\"permId\":35,\"resCode\":\"role:view\",\"permCode\":\"view\",\"type\":\"permission\",\"permName\":\"角色管理查看\",\"resId\":48}]";
        List<RolePermissionTreeDTO> a = JSON.parseArray(json, RolePermissionTreeDTO.class);
        System.out.println(JSON.parseArray(json, RolePermissionTreeDTO.class));
    }

}
