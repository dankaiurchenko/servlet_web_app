package com.danarossa.controllers;

import com.danarossa.database.daointerfaces.CourseDao;
import com.danarossa.entities.Course;
import com.danarossa.router.Accessible;
import com.danarossa.router.Controller;
import com.danarossa.router.Role;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller("/courses")
public class CourseController extends ParentController {

    @Url("/get-all")
    public void all(HttpServletRequest request, HttpServletResponse response) {
        try (CourseDao courseDao = abstractDaoFactory.getCourseDao()) {
            System.out.println("/get-all");
            writeToResponseBody(courseDao.getAll(), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }

    @Url("/new-one")
    @Accessible({Role.ADMIN, Role.TRAINER})
    public void add(HttpServletRequest request, HttpServletResponse response) {
        try (CourseDao courseDao = abstractDaoFactory.getCourseDao()) {
            System.out.println("into /new-one");
            String body = getBody(request);
            System.out.println("user from front    " + body);
            Course courseCourse = gson.fromJson(body, Course.class);
            courseDao.insert(courseCourse);
            writeToResponseBody(courseCourse.getId(), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error while inserting the course");
        }

    }

    @Url("/get-by-student")
    public void allOfStudent(HttpServletRequest request, HttpServletResponse response) {
        try (CourseDao courseDao = abstractDaoFactory.getCourseDao()) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            writeToResponseBody(courseDao.getAllCoursesOfStudent(studentId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }

    @Url("/get-by-id")
    public void byId(HttpServletRequest request, HttpServletResponse response) {
        try (CourseDao courseDao = abstractDaoFactory.getCourseDao()) {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            writeToResponseBody(courseDao.getEntityById(courseId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }

    @Url("/get-by-lecturer")
    public void allOfLecturer(HttpServletRequest request, HttpServletResponse response) {
        try (CourseDao courseDao = abstractDaoFactory.getCourseDao()) {
            int trainerId = Integer.parseInt(request.getParameter("trainerId"));
            writeToResponseBody(courseDao.getAllCoursesOfLecturer(trainerId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }


}
