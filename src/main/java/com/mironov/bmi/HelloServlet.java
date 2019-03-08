package com.mironov.bmi;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceModule;


@WebServlet("/")
public class HelloServlet extends HttpServlet {
    private Service service;

    private Gson gson = new Gson();

    public HelloServlet(){
        Injector injector= Guice.createInjector(new ServiceModule());
        service= injector.getInstance(Service.class);

        service.saveUser(1,178,"Vasja");
        service.saveBmi(1,178,65);
        service.saveBmi(1,178,68);
    }

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        String jsonString=gson.toJson(service.getUserBmiList(1));
        httpServletResponse.getWriter().write(jsonString);
        System.out.println(jsonString);


    }
}