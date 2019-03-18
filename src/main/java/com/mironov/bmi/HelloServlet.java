package com.mironov.bmi;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceModule;


//@WebServlet("/Hello")
@Singleton
public class HelloServlet extends HttpServlet {
    private Service service;

    private Gson gson = new Gson();

    private class JsonObject{
        int height;
        int weight;
        String name;

    }

    public HelloServlet(){
        Injector injector= Guice.createInjector(new ServiceModule());
        service= injector.getInstance(Service.class);

        service.saveBmi("Vasja",178,65);
        service.saveBmi("Vasja",178,68);

    }

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        //set access
        setAccessControlHeaders(httpServletResponse);

        //get query parameter
        /*
        String qS=httpServletRequest.getQueryString();
        String[] arr=qS.split("=");
        String name=arr[1];
        */
        //get and send list of records
        String jsonString = gson.toJson(service.getBmiList());
        httpServletResponse.getWriter().write(jsonString);
        httpServletRequest.setAttribute("bmi", jsonString);

        System.out.println("Get-"+jsonString);
    }

    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        //get body of request
        String body=getBody(httpServletRequest);
        System.out.println(body);

        //get fields and check
        JsonObject obj=gson.fromJson(body,JsonObject.class);
        if(obj.weight>0&&obj.height>0) {
            service.saveBmi(obj.name, obj.height, obj.weight);
        }
        else {
            httpServletResponse.setStatus(415);
        }
    }

    public static String getBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }


}
