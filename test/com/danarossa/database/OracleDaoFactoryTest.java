package com.danarossa.database;

import org.junit.Test;

public class OracleDaoFactoryTest {

    @Test
    public void getAccountDao() {
        AbstractDaoFactory.getDaoFactory().getAccountDao();
    }

    @Test
    public void getCourseDao() {
        AbstractDaoFactory.getDaoFactory().getCourseDao();
    }

    @Test
    public void getLecturerDao() {
        AbstractDaoFactory.getDaoFactory().getLecturerDao();
    }

    @Test
    public void getRealizedCourseDao() {
        AbstractDaoFactory.getDaoFactory().getRealizedCourseDao();
    }

    @Test
    public void getStudentDao() {
        AbstractDaoFactory.getDaoFactory().getStudentDao();
    }

    @Test
    public void getStudentMarkDao() {
        AbstractDaoFactory.getDaoFactory().getStudentMarkDao();
    }
}