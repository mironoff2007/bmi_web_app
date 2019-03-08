package com.mironov.bmi;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@WebServlet("/Hello")
public class HelloServlet extends HttpServlet {


private Gson gson = new Gson();
private ObjectMapper mapper = new ObjectMapper();

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        ArrayList<Man> list = new ArrayList<>();
        Man man1 = new Man(80,30,"Vasja");
        Man man2 = new Man(75,27,"Ivan");
        list.add(man1);
        list.add(man2);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        String jsonString=gson.toJson(list);
        httpServletResponse.getWriter().write(jsonString);
        System.out.println(jsonString);

    }
}