package com.danarossa.router;

import com.danarossa.controllers.AuthorisationController;
import com.danarossa.entities.User;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Router {

    private AuthorisationController authorisationController = new AuthorisationController();
    private Map<String, MyMethod> methods = new HashMap<>();

    public Router() {
        System.out.println("INSIDE OF ROUTER CONSTRUSTOR!!!!");
        final Reflections reflections = new Reflections("com.danarossa.controllers");
        final Set<Class<?>> foundControllers = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> foundController : foundControllers) {
            Controller annotation = foundController.getAnnotation(Controller.class);
            String controllerPath = annotation.value();
            try {
                Object controllerInstance = foundController.getConstructor().newInstance();
                for (Method method : foundController.getMethods()) {
                    Url annotation1 = method.getAnnotation(Url.class);
                    Accessible accessible = method.getAnnotation(Accessible.class);
                    if (annotation1 != null) {
                        Role[] roles = (accessible == null) ? Role.values() : accessible.value();
//                        String servletPath = "/index.php";
                        String path = controllerPath + annotation1.value();
                        methods.put(path, new MyMethod(controllerInstance, method, roles));
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        System.out.println(methods);
    }

    public void call(HttpServletRequest request, HttpServletResponse response) {
        User user = authorisationController.getUser(request);
        final String path = request.getPathInfo();
        System.out.println("path   " + path);
        methods.forEach((key, value) -> {
            if (path.matches(key)) {
                value.call(request, response, user);
            }
        });
    }

}
