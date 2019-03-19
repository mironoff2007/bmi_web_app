package com.mironov.bmi;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceImpl;


public class MyGuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {

        return Guice.createInjector(new ServletModule(){
            @Override
            protected void configureServlets(){
                serve("/index.html").with(GuiceServlet.class);
                bind(Service.class).to(ServiceImpl.class);
            }
        });
    }
}
