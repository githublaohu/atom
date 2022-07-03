package com.lamp.atom.service.operator.consumers.function;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import java.io.IOException;

public class DispatcherFilter implements Filter {

    public void init(FilterConfig config) {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);//servlet
//        if (isExinclude(httpServletRequest.getRequestURI())) {
//            response.setContentType("application/json");
//            String url = "http://120.78.148.188:9990/lamp/electron" + ((HttpServletRequest) request).getRequestURI();
//
//            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

//            String result = HttpRequest.post(url).contentType("application/json").body(JSONObject.toJSONString(parameterMap)).execute().body();
//            response.getWriter().write(result);
//        } else {
            filterChain.doFilter(request, response);
//        }
    }

    public void destroy() {

    }

    private static boolean isExinclude(String uri) {
        return !((StringUtils.contains(uri, "/lamp/atom/service/operator") ||
                (StringUtils.contains(uri, "/static/dist"))));
    }


}