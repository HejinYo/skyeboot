package cn.hejinyo.config;

import cn.hejinyo.shiro.token.StatelessAuthcToken;
import cn.hejinyo.consts.StatusCode;
import cn.hejinyo.consts.UserToken;
import cn.hejinyo.exception.InfoException;
import cn.hejinyo.system.model.dto.CurrentUserDTO;
import cn.hejinyo.utils.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/20 18:21
 * @Description : 登录验证拦截器
 */
public class AuthcInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthcInterceptor.class);
    private static final String DEFAULT_AUTHOR_PARAM = "Authorization";
    private static final String DEFAULT_TOKEN_CACHENAME = "tokenCache";

    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        System.out.println("用户:" + ip + ",访问目标:" + method.getDeclaringClass().getName() + "." + method.getName());

        String userToken = request.getHeader(DEFAULT_AUTHOR_PARAM);
        try {
            //从header 中获得 userToken
            //解析token
            String username = Tools.getTokenInfo(userToken, UserToken.USERNAME.getValue());
            Cache cache = cacheManager.getCache(DEFAULT_TOKEN_CACHENAME);
            CurrentUserDTO userDTO = (CurrentUserDTO) cache.get(username);
            //缓存中是否有此用户
            if (null != userDTO) {
                //验证Token有效性
                Tools.verifyToken(userToken, userDTO.getUserPwd());
                SecurityUtils.getSubject().login(new StatelessAuthcToken(username, userToken, userDTO));
                return true;
            }
            // /token失效
            throw new InfoException(StatusCode.TOKEN_OVERDUE);
        } catch (Exception e) {
            logger.debug("userToken 验证失败：" + userToken);
            throw new InfoException(StatusCode.TOKEN_OVERDUE);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        long startTime = (Long) request.getAttribute("requestStartTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        // 打印方法执行时间
        if (executeTime > 1000) {
            System.out.println("[" + method.getDeclaringClass().getName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        } else {
            System.out.println("[" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}