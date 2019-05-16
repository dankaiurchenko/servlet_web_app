package com.danarossa.controllers;

import com.danarossa.router.Accessible;
import com.danarossa.router.Controller;
import com.danarossa.router.Role;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/courses")
public class CourseController  extends ParentController {

    @Url("/get-all")
    public void all(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("alllll!!!");
        writer.flush();
        writer.close();
    }

    @Url("/new-one")
    @Accessible({Role.TRAINER, Role.TRAINER})
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("adding something");
        writer.flush();
        writer.close();
    }

    @Url("/get-by-student")
    public void allOfStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("student's courses");
        writer.flush();
        writer.close();
    }

    @Url("/get-by-lecturer")
    public void allOfLecturer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("lecturer's courses");
        writer.flush();
        writer.close();
    }


}
