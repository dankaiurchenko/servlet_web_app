package com.danarossa.controllers;

import com.danarossa.database.AbstractDaoFactory;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


class ParentController {
    AbstractDaoFactory abstractDaoFactory = AbstractDaoFactory.getDaoFactory();
    Gson gson = new Gson();

    String getBody(HttpServletRequest request) {
        try {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ControllerException("Cant read body of post request!!!");
        }
    }

    void writeToResponseBody(Object object, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            String x = gson.toJson(object);
            System.out.println("writing into response body   ");
            System.out.println(x);
            writer.println(x);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ControllerException("Cant write to  body of response!!!");
        }
    }
}
