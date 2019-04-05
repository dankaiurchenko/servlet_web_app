package com.danarossa.database.daointerfaces;

import com.danarossa.database.AbstractDaoFactory;

public interface ITransaction extends AbstractDaoFactory {
    void startTransaction();
    void commit();
    void rollback();
}
