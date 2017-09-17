package cn.hejinyo.shiro.filter;

import cn.hejinyo.consts.StatusCode;
import cn.hejinyo.consts.UserToken;
import cn.hejinyo.model.dto.CurrentUserDTO;
import cn.hejinyo.shiro.token.StatelessAuthcToken;
import cn.hejinyo.utils.*;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/7/29 18:05
 * @Description :
 */
public class StatelessAuthcFilter extends AccessControlFilter {
    private static final Logger logger = LoggerFactory.getLogger(StatelessAuthcFilter.class);

    private static final String DEFAULT_AUTHOR_PARAM = "Authorization";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String userToken = ((HttpServletRequest) request).getHeader(DEFAULT_AUTHOR_PARAM);
        try {
            //从header 中获得 userToken
            //解析token
            String username = Tools.getTokenInfo(userToken, UserToken.USERNAME.getValue());
            //缓存中是否有此用户
            CurrentUserDTO userDTO = redisUtils.get(RedisKeys.getTokenCacheKey(username), CurrentUserDTO.class, 1800);
            if (null != userDTO) {
                //验证Token有效性
                Tools.verifyToken(userToken, userDTO.getUserPwd());
                //委托给Realm进行登录
                try {
                    getSubject(request, response).login(new StatelessAuthcToken(username, userToken, userDTO));
                } catch (Exception e) {
                    //userToken验证失败
                    logger.debug("[ username:" + username + "] userToken验证失败：" + userToken);
                    ResponseUtils.response(response, Result.error(StatusCode.TOKEN_OVERDUE));
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            logger.debug("非法的userToken：" + userToken);
        }
        ResponseUtils.response(response, Result.error(StatusCode.TOKEN_OVERDUE));
        return false;
    }

    protected String getHost(ServletRequest request) {
        return request.getRemoteHost();
    }

}

