package com.danarossa.controllers;

import com.danarossa.router.Controller;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/realized-courses")
public class RealizedCourseController  extends ParentController  {

    @Url("/get-all")
    public void all(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("all realized courses");
        writer.flush();
        writer.close();
    }

    @Url("/get-by-lecturer")
    public void allOfLecturer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("lecturer's realized courses");
        writer.flush();
        writer.close();
    }

    @Url("/get-by-course")
    public void allOfCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("course's realized courses");
        writer.flush();
        writer.close();
    }

    @Url("/archived")
    public void allArchived(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("archived courses");
        writer.flush();
        writer.close();
    }


}
