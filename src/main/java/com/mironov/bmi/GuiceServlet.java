package com.mironov.bmi;

import com.google.gson.Gson;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.mironov.bmi.Service.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Singleton
public class GuiceServlet extends HttpServlet {
 Service service;

    private Gson gson = new Gson();

    private class JsonObject{
        int height;
        int weight;
        String name;

    }

    public GuiceServlet(){
        Injector injector= Guice.createInjector(new MyModule());
        service= injector.getInstance(Service.class);

        service.saveBmi("Vasja",178,65);
        service.saveBmi("Vasja",178,68);
        System.out.println("Guice servlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("doGet");
        setAccessControlHeaders(response);
        String jsonString = gson.toJson(service.getBmiList());
        response.getWriter().write(jsonString);
        request.setAttribute("bmi", jsonString);
        System.out.println(jsonString);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //get body of request
        System.out.println("doPost");
        String body=getBody(request);
        System.out.println(body);

        //get fields and check
        JsonObject obj=gson.fromJson(body,JsonObject.class);
        if(obj.weight>0&&obj.height>0) {
            service.saveBmi(obj.name, obj.height, obj.weight);
        }
        else {
            response.setStatus(415);
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
