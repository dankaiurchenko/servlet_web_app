package com.danarossa.database;

import com.danarossa.database.daointerfaces.CourseDao;
import com.danarossa.database.daointerfaces.RealizedCourseDao;
import com.danarossa.database.daointerfaces.StudentMarkDao;
import com.danarossa.database.daointerfaces.UserDao;


public interface AbstractDaoFactory {
    CourseDao getCourseDao();
    UserDao getUserDao();
    RealizedCourseDao getRealizedCourseDao();
    StudentMarkDao getStudentMarkDao();

    static AbstractDaoFactory getDaoFactory(){
        return new PostgresDabFactory();
    }
}
