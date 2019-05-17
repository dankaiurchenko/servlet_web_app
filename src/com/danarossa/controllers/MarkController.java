package com.danarossa.controllers;

import com.danarossa.database.daointerfaces.StudentMarkDao;
import com.danarossa.database.daointerfaces.UserDao;
import com.danarossa.dto.DtoMark;
import com.danarossa.entities.StudentMark;
import com.danarossa.router.Accessible;
import com.danarossa.router.Controller;
import com.danarossa.router.Role;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/marks")
public class MarkController extends ParentController {

    @Url("/get-by-course")
    public void allOfCourse(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("all marks and students of course");
//        PrintWriter writer = response.getWriter();
//        writer.println("all marks and students of course");
//        writer.flush();
//        writer.close();
    }

    @Url("/all-of-student")
    public void allOfStudent(HttpServletRequest request, HttpServletResponse response) {
        try (StudentMarkDao markDao = abstractDaoFactory.getStudentMarkDao()) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            writeToResponseBody(markDao.getStudentMarksForStudent(studentId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all marks of student");
        }
    }

    @Url("/by-student-and-course")
    public void ofStudentAndCourse(HttpServletRequest request, HttpServletResponse response) {
        try (StudentMarkDao markDao = abstractDaoFactory.getStudentMarkDao()) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int realizedCourseId = Integer.parseInt(request.getParameter("realizedCourseId"));
            StudentMark studentMarkForStudentAndRealizedCourse = markDao.getStudentMarkForStudentAndRealizedCourse(studentId, realizedCourseId);
            writeToResponseBody(studentMarkForStudentAndRealizedCourse, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get mark of student for realized course");
        }
    }

    @Url("/by-student-and-course")
    @Accessible({Role.TRAINER})
    public void addMark(HttpServletRequest request, HttpServletResponse response) {
        try (StudentMarkDao markDao = abstractDaoFactory.getStudentMarkDao(); UserDao userDao = abstractDaoFactory.getUserDao()) {
            System.out.println("into /new-one");
            String body = getBody(request);
            System.out.println("user from front    " + body);
            DtoMark mark = gson.fromJson(body, DtoMark.class);
            StudentMark studentMark = new StudentMark(
                    userDao.getEntityById(mark.getStudentId()),
                    mark.getRealizedCourseId(), mark.getMark());
            markDao.insert(studentMark);
            writeToResponseBody(studentMark.getId(), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get mark of student for realized course");
        }
    }


}
