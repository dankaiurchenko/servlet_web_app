package com.danarossa.database;

import com.danarossa.database.daointerfaces.ICourseDao;
import com.danarossa.database.daointerfaces.IRealizedCourseDao;
import com.danarossa.database.daointerfaces.IStudentMarkDao;
import com.danarossa.database.daointerfaces.IUserDao;


public interface AbstractDaoFactory {
    ICourseDao getCourseDao();
    IUserDao getUserDao();
    IRealizedCourseDao getRealizedCourseDao();
    IStudentMarkDao getStudentMarkDao();

    static AbstractDaoFactory getDaoFactory(){
        return new PostgresDabFactory();
    }
}
