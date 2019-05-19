package com.danarossa.controllers;

import com.danarossa.database.daointerfaces.RealizedCourseDao;
import com.danarossa.entities.RealizedCourse;
import com.danarossa.router.Controller;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/realized-courses")
public class RealizedCourseController extends ParentController {

    @Url("/get-all")
    public void all(HttpServletRequest request, HttpServletResponse response) {
        try (RealizedCourseDao realizedCourseDao = abstractDaoFactory.getRealizedCourseDao()) {
            writeToResponseBody(realizedCourseDao.getAll(), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }

    @Url("/get-by-lecturer")
    public void allOfLecturer(HttpServletRequest request, HttpServletResponse response) {
        try (RealizedCourseDao realizedCourseDao = abstractDaoFactory.getRealizedCourseDao()) {
            int trainerId = Integer.parseInt(request.getParameter("trainerId"));
            writeToResponseBody(realizedCourseDao.getAllRealizedCoursesOfLecturer(trainerId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }

    @Url("/get-by-student")
    public void allOfStudent(HttpServletRequest request, HttpServletResponse response) {
        try (RealizedCourseDao realizedCourseDao = abstractDaoFactory.getRealizedCourseDao()) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            writeToResponseBody(realizedCourseDao.getAllRealizedCoursesOfStudent(studentId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }

    @Url("/get-by-course")
    public void allOfCourse(HttpServletRequest request, HttpServletResponse response) {
        try (RealizedCourseDao realizedCourseDao = abstractDaoFactory.getRealizedCourseDao()) {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            writeToResponseBody(realizedCourseDao.getAllRealizedCoursesOfCourse(courseId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }

    @Url("/get-by-id")
    public void byId(HttpServletRequest request, HttpServletResponse response) {
        try (RealizedCourseDao realizedCourseDao = abstractDaoFactory.getRealizedCourseDao()) {
            int realizedCourseId = Integer.parseInt(request.getParameter("realizedCourseId"));
            writeToResponseBody(realizedCourseDao.getEntityById(realizedCourseId), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("cant get all courses");
        }
    }

    @Url("/new")
    public void createNew(HttpServletRequest request, HttpServletResponse response) {
        try (RealizedCourseDao realizedCourseDao = abstractDaoFactory.getRealizedCourseDao()) {
            String body = getBody(request);
            RealizedCourse realizedCourse = gson.fromJson(body, RealizedCourse.class);
            realizedCourseDao.insert(realizedCourse);
            writeToResponseBody(realizedCourse.getId(), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error");
        }
    }

    @Url("/edit")
    public void edit(HttpServletRequest request, HttpServletResponse response) {
        try (RealizedCourseDao realizedCourseDao = abstractDaoFactory.getRealizedCourseDao()) {
            String body = getBody(request);
            RealizedCourse realizedCourse = gson.fromJson(body, RealizedCourse.class);
            realizedCourseDao.update(realizedCourse);
            writeToResponseBody(realizedCourse.getId(), response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error");
        }
    }

//    @Url("/archived")
//    public void allArchived(HttpServletRequest request, HttpServletResponse response) {
//        try (RealizedCourseDao realizedCourseDao  = abstractDaoFactory.getRealizedCourseDao()) {
//            int courseId = Integer.parseInt(request.getParameter("courseId"));
//            writeToResponseBody(realizedCourseDao.get(courseId), response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ControllerException("cant get all courses");
//        }
//    }


}
