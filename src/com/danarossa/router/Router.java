package com.danarossa.router;

import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Router {

//    Map<String, Object> controllers = new HashMap<>();
    private Map<String, MyMethod> methods = new HashMap<>();

    public void init() {
        final Reflections reflections = new Reflections("com.danarossa.controllers");
        final Set<Class<?>> foundControllers = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> foundController : foundControllers) {
            Controller annotation = foundController.getAnnotation(Controller.class);
            String controllerPath = annotation.value();
            try {
                Object controllerInstance = foundController.getConstructor().newInstance();
                for (Method method : foundController.getMethods()) {
                    Url annotation1 = method.getAnnotation(Url.class);
                    if (annotation1 != null) {
                        String path = controllerPath + annotation1.value();
                        methods.put(path, new MyMethod(controllerInstance, method));
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void call(HttpServletRequest request, HttpServletResponse response) {
        final String queryString = request.getQueryString();
        final String path = request.getPathInfo();
        System.out.println("queryString   " + queryString);
        methods.forEach((key, value) -> {
            if (path.matches(key)) {
                value.call(request, response);
            }
        });
    }

}
