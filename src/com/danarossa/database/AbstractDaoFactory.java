package com.danarossa.database;

import com.danarossa.database.concretedao.*;


public interface AbstractDaoFactory {
    AccountDao getAccountDao();
    CourseDao getCourseDao();
    LecturerDao getLecturerDao();
    RealizedCourseDao getRealizedCourseDao();
    StudentDao getStudentDao();
    StudentMarkDao getStudentMarkDao();

    static AbstractDaoFactory getDaoFactory(){
        return new OracleDaoFactory();
    }

}
