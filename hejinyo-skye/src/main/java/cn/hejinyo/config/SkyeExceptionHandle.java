package cn.hejinyo.config;

import cn.hejinyo.utils.Result;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/13 19:12
 * @Description :
 */
@RestControllerAdvice
public class SkyeExceptionHandle {
    private static Logger logger = LoggerFactory.getLogger(SkyeExceptionHandle.class.getName());

    /**
     * 401 UNAUTHORIZED
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public Result shiroException(UnauthorizedException ex, HttpServletResponse response) {
        return Result.error(-1, "无此权限");
    }

}
