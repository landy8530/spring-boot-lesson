package org.landy.springbootlesson4.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;


@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    //请求之前初始化操作
    @Override
    public void requestInitialized(ServletRequestEvent sre) {

        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

        ServletContext servletContext = request.getServletContext();

        servletContext.log("request was initialized!");
    }

    //请求之后销毁操作
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

        ServletContext servletContext = request.getServletContext();

        servletContext.log("request was destroyed!");
    }
}
