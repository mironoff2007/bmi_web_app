package com.mironov.bmi;

import com.google.gson.Gson;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceModule;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Singleton
public class GuiceServlet extends HttpServlet {
    @Inject Service service;

    private Gson gson = new Gson();

    private class JsonObject{
        int height;
        int weight;
        String name;

    }

    public GuiceServlet(){
        //Injector injector= Guice.createInjector(new ServiceModule());
        //service= injector.getInstance(Service.class);

        service.saveBmi("Vasja",178,65);
        service.saveBmi("Vasja",178,68);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String jsonString = gson.toJson(service.getBmiList());
        response.getWriter().write(jsonString);
        request.setAttribute("bmi", jsonString);

    }
}
