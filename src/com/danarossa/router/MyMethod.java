package com.danarossa.router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MyMethod {
    private Object controller;
    private Method method;

    MyMethod(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    void call(HttpServletRequest request, HttpServletResponse response){
        try {
            this.method.invoke(controller, request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

//    private Object[] parseArguments( String query){
//        List<String> query_pairs = new ArrayList<>();
//        String[] pairs = query.split("&");
//        for (String pair : pairs) {
//            int idx = pair.indexOf("=");
//            query_pairs.add(URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
//        }
//        return query_pairs.toArray();
//    }


}
