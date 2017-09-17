package cn.hejinyo.shiro.filter;

import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class URLFilter extends AdviceFilter {
    private static final Logger logger = LoggerFactory.getLogger(URLFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        logger.debug("请求的URL:" + contextPath + uri);
        return Boolean.TRUE;
    }

}
