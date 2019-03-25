package com.mironov.bmi;


import com.mironov.bmi.DAO.TableManager;
import com.mironov.bmi.Service.Service;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Singleton
public class GuiceServlet extends HttpServlet {

    private final Service service;
    private final Gson gson = new Gson();

    private class JsonObject{
        int height;
        int weight;
        String name;
    }

    @Inject
    public GuiceServlet(Service service){
        this.service=service;
        try {
            TableManager.resetTable();
            service.saveBmi("Vasja",178,65);
            service.saveBmi("Petja",170,68);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Returns List of Json object with fields - bmi:float,weight:int[kg],name:string,height:int[cm],dateTime:string
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonString = gson.toJson(service.getBmiList());
        response.getWriter().write(jsonString);
        request.setAttribute("bmi", jsonString);
        System.out.println(jsonString);
    }

    //Requires json Object with fields - name:string,weight:int[kg],height:int[cm]
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //get body of request
        String body=getBody(request);
        System.out.println(body);

        //get fields
        final JsonObject obj=gson.fromJson(body,JsonObject.class);

        //add bmi and check
        try {
            service.saveBmi(obj.name, obj.height, obj.weight);
        } catch (IllegalArgumentException e) {
            response.setStatus(415);
        } catch (SQLException e) {

        }
    }

    private static String getBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

}
