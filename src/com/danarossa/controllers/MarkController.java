package com.danarossa.controllers;

import com.danarossa.router.Controller;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/marks")
public class MarkController {

    @Url("/get-by-course")
    public void allOfCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("all marks and students of course");
        PrintWriter writer = response.getWriter();
        writer.println("all marks and students of course");
        writer.flush();
        writer.close();
    }

    @Url("/all-of-student")
    public void allOfStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("all marks of student");
        PrintWriter writer = response.getWriter();
        writer.println("all marks of student");
        writer.flush();
        writer.close();
    }

    @Url("/by-student-and-course")
    public void ofStudentAndCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("mark of student on course");
        PrintWriter writer = response.getWriter();
        writer.println("mark of student on course");
        writer.flush();
        writer.close();
    }


}
