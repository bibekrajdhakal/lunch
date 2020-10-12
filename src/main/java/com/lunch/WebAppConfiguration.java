package com.lunch;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

public class WebAppConfiguration {

    private WebAppConfiguration() {}

    @WebListener
    public static class RestGuiceListener extends GuiceResteasyBootstrapServletContextListener {
        @Override
        public void contextInitialized(ServletContextEvent event) {
            final ServletContext servletContext = event.getServletContext();
            servletContext.setInitParameter("resteasy.guice.modules", WebApplication.class.getName());
            super.contextInitialized(event);
        }
    }

    @WebServlet(name = "Resteasy", urlPatterns = "/*")
    public static class RestEasyServletDispatcher extends HttpServletDispatcher {}

}
