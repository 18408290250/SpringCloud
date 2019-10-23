package com.junzhang.servicezuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class MyPostFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyPostFilter.class);
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
//        默认为500-1000之间
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletResponse response=context.getResponse();
        HttpServletRequest request=context.getRequest();

        String method=request.getMethod();
        long startTime= (long) context.get("startTime");
        Throwable throwable=context.getThrowable();
        StringBuffer requestURL = request.getRequestURL();
        int responseStatusCode = context.getResponseStatusCode();

        log.info("请求token:" + request.getParameter("X-Auth-Token"));
        log.info("请求方式:" + method);
        log.info("请求开始时间:" + LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) context.get("startTime")), ZoneId.systemDefault()).toString());
        log.info("异常:" + throwable);
        log.info("请求URL:" + requestURL);
        log.info("响应状态码:" + responseStatusCode);
        log.info("耗时:" + (System.currentTimeMillis()-startTime));

        return null;
    }
}
