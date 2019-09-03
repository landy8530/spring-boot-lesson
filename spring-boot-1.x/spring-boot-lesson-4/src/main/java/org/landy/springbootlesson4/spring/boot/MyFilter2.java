package org.landy.springbootlesson4.spring.boot;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyFilter2 extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        doSomething();

        filterChain.doFilter(request, response);

    }

    public void doSomething() {
        //由于Servlet都是在同一个线程中执行的，如果某个API没有提供HttpServletRequest的时候，就可以用以下方式获取
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        ServletContext servletContext = request.getServletContext();

        String requestURI = request.getRequestURI();

        servletContext.log(requestURI + " was filtered!");

    }

}
