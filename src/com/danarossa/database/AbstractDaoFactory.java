package com.danarossa.database;

import com.danarossa.database.daointerfaces.*;


public interface AbstractDaoFactory {
    IAccountDao getAccountDao();
    ICourseDao getCourseDao();
    ILecturerDao getLecturerDao();
    IRealizedCourseDao getRealizedCourseDao();
    IStudentDao getStudentDao();
    IStudentMarkDao getStudentMarkDao();
    default ITransaction getTransaction(){
        throw  new PersistException("Transaction is not supported");
    }
    default void closeAllConnections(){throw  new PersistException("Closing all connections is not supported");}

    static AbstractDaoFactory getDaoFactory(){
        return new OracleDaoFactory();
    }
}
