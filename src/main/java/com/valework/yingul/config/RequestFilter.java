package com.valework.yingul.config;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        //amazon produccion
        //response.setHeader("Access-Control-Allow-Origin", "http://192.168.100.51:4200");
        //response.setHeader("Access-Control-Allow-Origin", "http://192.168.100.42:4200");
        //response.setHeader("Access-Control-Allow-Origin", "http://www.yingul.com");
        //response.setHeader("Access-Control-Allow-Origin", "http://192.168.100.17:4200");        
        //response.setHeader("Access-Control-Allow-Origin", "http://192.168.100.16:4200");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        //response.setHeader("Access-Control-Allow-Origin", " https://30ff5eb3.ngrok.io");

        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "authorization,Access-Control-Allow-Origin, Access-Control-Allow-Headers, Access-Control-Allow-Methods, Access-Control-Allow-Credentials, content-type," +
                "access-control-request-headers,access-control-request-method,accept,origin,Authorization,x-requested-with,X-API-KEY");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
            try {
                chain.doFilter(req, res);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Pre-flight");
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization,Access-Control-Allow-Origin, Access-Control-Allow-Headers, Access-Control-Allow-Methods, Access-Control-Allow-Credentials, content-type," +
                    "access-control-request-headers,access-control-request-method,accept,origin,Authorization,x-requested-with,X-API-KEY");
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}
