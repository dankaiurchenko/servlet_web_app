package com.danarossa.controllers;

import com.danarossa.router.Accessible;
import com.danarossa.router.Controller;
import com.danarossa.router.Role;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/users")
public class UserController extends ParentController {
    @Url("/get-all")
    @Accessible({Role.ADMIN})
    public void all(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("/users / adding somethind /get-all");
        PrintWriter writer = response.getWriter();
        writer.println("/users / adding somethind /get-all");
        writer.flush();
        writer.close();
    }

    @Url("/new-one")
    @Accessible(Role.ADMIN)
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("/users / adding somethind /get-all");
        PrintWriter writer = response.getWriter();
        writer.println("adding somethind");
        writer.flush();
        writer.close();
    }

    @Url("/students")
    public void allStudents(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("all students");
        PrintWriter writer = response.getWriter();
        writer.println("all students");
        writer.flush();
        writer.close();
    }

    @Url("/lecturers")
    public void allLecturers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("all lecturers");
        PrintWriter writer = response.getWriter();
        writer.println("all lecturers");
        writer.flush();
        writer.close();
    }

    @Url("/get-by-course")
    public void allOfCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("all students of course");
        PrintWriter writer = response.getWriter();
        writer.println("all students of course");
        writer.flush();
        writer.close();
    }



}
