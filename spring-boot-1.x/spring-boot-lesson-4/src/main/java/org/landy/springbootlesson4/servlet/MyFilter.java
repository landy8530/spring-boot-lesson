package org.landy.springbootlesson4.servlet;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter只执行一次的Filter
 * @WebFilter 可以指定某个类(servletNames = {"myServlet"})，也可指定某个url
 */
@WebFilter(urlPatterns = "/myservlet")
public class MyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ServletContext servletContext = request.getServletContext();
//        ServletContext servletContext = getServletContext();
        servletContext.log("/myservlet was filtered!");

        filterChain.doFilter(request, response);

    }

}
