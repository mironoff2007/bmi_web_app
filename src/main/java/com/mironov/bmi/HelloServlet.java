package com.mironov.bmi;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        String jsonString = gson.toJson(service.getUserBmiList(1));
        httpServletResponse.getWriter().write(jsonString);
        httpServletRequest.setAttribute("bmi", jsonString);
        System.out.println(jsonString);
    }

    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        System.out.println(getBody(httpServletRequest));
        System.out.println(httpServletResponse.toString());

    }

    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }
}