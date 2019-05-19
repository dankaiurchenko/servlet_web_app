package com.danarossa.router;

import com.danarossa.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

class MyMethod {
    private Object controller;
    private Method method;
    private ArrayList<Role> roles = new ArrayList<>();

    MyMethod(Object controller, Method method, Role[] roles) {
        this.controller = controller;
        this.method = method;
        this.roles.addAll(Arrays.asList(roles));
    }

    void call(HttpServletRequest request, HttpServletResponse response, User user) {
        try {
            if (roles.contains(user.getRole()))
                this.method.invoke(controller, request, response);
            else response.sendError(response.SC_BAD_REQUEST, "important_parameter needed");
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }




}
