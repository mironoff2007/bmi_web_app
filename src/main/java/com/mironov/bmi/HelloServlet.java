package com.mironov.bmi;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceModule;


@WebServlet("/Hello")
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
        setAccessControlHeaders(httpServletResponse);
        String jsonString=gson.toJson(service.getUserBmiList(1).get(1));
        httpServletResponse.getWriter().write(jsonString);
        httpServletRequest.setAttribute("bmi",jsonString);
        System.out.println(jsonString);

    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }
}