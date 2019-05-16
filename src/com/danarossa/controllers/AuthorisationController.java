package com.danarossa.controllers;

import com.danarossa.entities.User;
import com.danarossa.router.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.AccessControlException;

@Controller("/auth")
public class AuthorisationController extends ParentController {

    public void login(HttpServletRequest request, HttpServletResponse response) {

    }


    public void register(HttpServletRequest request, HttpServletResponse response) {
    }


    public User getUser(HttpServletRequest request) {
        User userByToken = abstractDaoFactory.getUserDao().getUserByToken(request.getHeader("token"));
        if (userByToken == null)
            throw new AccessControlException("Token expired");
        return userByToken;
    }
}
