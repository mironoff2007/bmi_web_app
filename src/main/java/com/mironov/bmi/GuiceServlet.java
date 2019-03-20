package com.mironov.bmi;

import com.mironov.bmi.Service.Service;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;


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
        //create injector
        Injector injector= Guice.createInjector(new MyModule());
        service= injector.getInstance(Service.class);

        try {
            service.saveBmi("Vasja",178,65);
            service.saveBmi("Petja",170,68);
        } catch (WrongNumberException e) {
            e.printStackTrace();
        }


    }

    //Returns List of Json object with fields - bmi:float,weight:int,name:string,height:int,dateTime:string
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = gson.toJson(service.getBmiList());
        response.getWriter().write(jsonString);
        request.setAttribute("bmi", jsonString);
        System.out.println(jsonString);
    }

    //Requires json Object with fields - name:string,weight:int,height:int
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //get body of request
        String body=getBody(request);
        System.out.println(body);

        //get fields and check
        JsonObject obj=gson.fromJson(body,JsonObject.class);

        try {
            service.saveBmi(obj.name, obj.height, obj.weight);
        } catch (WrongNumberException e) {
            response.setStatus(415);
        }


    }

    public static String getBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

}
