package com.danarossa.controllers;

import com.danarossa.database.daointerfaces.CourseDao;
import com.danarossa.database.daointerfaces.RealizedCourseDao;
import com.danarossa.database.daointerfaces.StudentMarkDao;
import com.danarossa.database.daointerfaces.UserDao;
import com.danarossa.dto.ArchiveItem;
import com.danarossa.dto.DtoMark;
import com.danarossa.entities.Course;
import com.danarossa.entities.RealizedCourse;
import com.danarossa.entities.StudentMark;
import com.danarossa.entities.User;
import com.danarossa.router.Accessible;
import com.danarossa.router.Controller;
import com.danarossa.router.Role;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
        try (StudentMarkDao markDao = abstractDaoFactory.getStudentMarkDao();
             CourseDao courseDao = abstractDaoFactory.getCourseDao();
             RealizedCourseDao realizedCourseDao = abstractDaoFactory.getRealizedCourseDao();
             UserDao userDao = abstractDaoFactory.getUserDao()) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            List<ArchiveItem> archiveItems = new ArrayList<>();
            List<StudentMark> studentMarksForStudent = markDao.getStudentMarksForStudent(studentId);
            for (StudentMark studentMark : studentMarksForStudent) {
                Course course = courseDao.getByRealized(studentMark.getRealizedCourseId());
                RealizedCourse realizedCourse = realizedCourseDao.getEntityById(studentMark.getRealizedCourseId());
                User user = userDao.getEntityById(course.getLecturerId());
                archiveItems.add(new ArchiveItem(studentMark.getId(), course.getTitle(),
                        studentMark.getRealizedCourseId(),
                        user.getName() + " " + user.getSurname(),
                        realizedCourse.getEndDate(), studentMark.getMark()));
            }

            writeToResponseBody(archiveItems, response);
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

    @Url("/set-for-student-and-course")
    @Accessible({Role.TRAINER, Role.ADMIN})
    public void editMark(HttpServletRequest request, HttpServletResponse response) {
        try (StudentMarkDao markDao = abstractDaoFactory.getStudentMarkDao(); UserDao userDao = abstractDaoFactory.getUserDao()) {
            System.out.println("into /new-one");
            String body = getBody(request);
            System.out.println("user from front    " + body);
            DtoMark mark = gson.fromJson(body, DtoMark.class);
            StudentMark studentMark = markDao.getEntityById(mark.getId());
            studentMark.setMark(mark.getMark());
            markDao.update(studentMark);
            writeToResponseBody(studentMark.getId(), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get mark of student for realized course");
        }
    }

    @Url("/join")
    @Accessible({Role.STUDENT})
    public void joinCourse(HttpServletRequest request, HttpServletResponse response) {
        try (StudentMarkDao markDao = abstractDaoFactory.getStudentMarkDao(); UserDao userDao = abstractDaoFactory.getUserDao()) {
            System.out.println("into /new-one");
            String body = getBody(request);
            System.out.println("user from front    " + body);
            DtoMark mark = gson.fromJson(body, DtoMark.class);
            if (mark.getId() == null || mark.getId() == 0) {
                StudentMark studentMark = new StudentMark(userDao.getEntityById(mark.getStudentId()), mark.getRealizedCourseId(), 0d);
                markDao.insert(studentMark);
                writeToResponseBody(studentMark.getId(), response);
            } else throw new ControllerException("Student already joined the course!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get mark of student for realized course");
        }
    }


}
