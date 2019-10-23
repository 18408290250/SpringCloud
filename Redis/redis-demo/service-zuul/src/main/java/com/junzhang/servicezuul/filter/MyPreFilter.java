package com.junzhang.servicezuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyPreFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyPreFilter.class);
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        // 在第一次登录的时候，创建session后，若使用cookie机制，则会自动在浏览器创建cookie存入sessionId,并在下一次请求的时候，浏览器会在requesu自动携带该cookie(sessionId)
        // 对于使用header机制，需要用户的每一次请求都需要携带上token，证明其请求的合法性
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime", System.currentTimeMillis());
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        log.info(String.format("sessionId >>> %s", request.getSession().getId()));

        // 使用token验证：对service2（lock） 验证登录
//        if(request.getRequestURL().toString().indexOf("service2") > -1){
//            String accessToken = request.getHeader("X-Auth-Token");
//            System.out.println("accessToken:"+accessToken);
//            if(null == accessToken || StringUtils.isEmpty(accessToken)) {
//                log.warn("抱歉,当前请求没有X-Auth-Token");
//                // 过滤该请求，不往下级服务去转发请求，到此结束
//                ctx.setSendZuulResponse(false);
//                ctx.setResponseStatusCode(401);
//                ctx.setResponseBody("{\"result\":\"X-Auth-Token不存在或为空!\"}");
//                ctx.getResponse().setContentType("text/html;charset=UTF-8");
//                return null;
//            }
//        }

        log.info("请求通过过滤");
        //这里return的值没有意义，zuul框架没有使用该返回值
        return null;
    }
}