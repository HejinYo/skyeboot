package cn.hejinyo.aspect;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/13 18:16
 * @Description :
 */

//@Aspect
//@Component
public class HttpAspect {
    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    //@Pointcut("execution(public * cn.hejinyo.controller.*.add(..))")
    public void log() {

    }

    //@Before("log()")
    public void logBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //url
        String url = request.getRequestURL().toString();
        //method
        String method = request.getMethod();
        //ip
        String ip = request.getRemoteHost();
        //classn
        String className = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //parameter
        String parameter = joinPoint.getArgs().toString();
        String info = "";
        info = "url:'" + url + "',method:" + method + ",ip:" + ip + ",className:" + className + ",parameter:" + parameter;
        logger.info("befor={}", info);
    }

    //@After("log()")
    public void logAfter() {
        logger.info("befor={}", 2);
    }

    // @AfterReturning(returning = "object", pointcut = "log()")
    public void logAfterRetuning(Object object) {
        logger.info("logAfterRetuning={}", object);
    }


}