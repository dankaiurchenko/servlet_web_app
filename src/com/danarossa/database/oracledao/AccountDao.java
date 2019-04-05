package com.danarossa.database.oracledao;

import com.danarossa.database.PersistException;
import com.danarossa.database.daointerfaces.IAccountDao;
import com.danarossa.entities.Account;
import com.danarossa.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountDao extends AbstractGenericDao<Account, Long> implements IAccountDao {
    private static final String ACCOUNT_ID = "ACCOUNT_ID";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    private static final String USER_ID = "USER_ID";
    private static final String LAST_TIME_ACTIVE = "LAST_TIME_ACTIVE";
    private static final String TABLE = "ACCOUNTS";
    private String ACCOUNT_NEXT_PRIMARY_KEY = "ACCOUNT_NEXT_PRIMARY_KEY";

    public AccountDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectByIdQuery() {
        return getBasicSelectQuery() + " where " + ACCOUNT_ID + " = ?";
    }

    private String getBasicSelectQuery() {
        return "select " + getFieldsNames() + ", " + LAST_TIME_ACTIVE + "\n" +
                "from " + TABLE;
    }

    private String getFieldsNames() {
        return ACCOUNT_ID + ", " + EMAIL + ", " + PASSWORD + ", " + ACCOUNT_TYPE + ", " + USER_ID;
    }

    @Override
    protected String getSelectQuery() {
        return getBasicSelectQuery();
    }

    @Override
    protected String getInsertQuery() {
        return "insert into accounts(" + getFieldsNames() + ")\n" +
                "values (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from " + TABLE + " where " + ACCOUNT_ID + " = ?";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + TABLE + " set " + EMAIL + " = ?, " + PASSWORD + " = ?, " +
                ACCOUNT_TYPE + " = ?, " + USER_ID + " = ? where " + ACCOUNT_ID + " = ?";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + TABLE + "_SQ.nextval as " + ACCOUNT_NEXT_PRIMARY_KEY + " from dual";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Account entity) throws SQLException {
//        ACCOUNT_ID, EMAIL, PASSWORD, ACCOUNT_TYPE, USER_ID
        statement.setLong(1, entity.getId());
        setFields(statement, entity, 2);
    }

    private void setFields(PreparedStatement statement, Account entity, int startIndex) throws SQLException {
        statement.setString(startIndex, entity.getEmail());
        statement.setString(startIndex + 1, entity.getPassword());
        statement.setString(startIndex + 2, entity.getAccountType());
        statement.setLong(startIndex + 3, entity.getUserId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Account entity) throws SQLException {
        setFields(statement, entity, 1);
        statement.setLong(5, entity.getId());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(ACCOUNT_NEXT_PRIMARY_KEY);
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<Account> parseResultSet(ResultSet rs) throws SQLException {
        List<Account> list = new ArrayList<>();
        while (rs.next()) {
            Long accountId = rs.getLong(ACCOUNT_ID);
            String email = rs.getString(EMAIL);
            String password = rs.getString(PASSWORD);
            String accountType = rs.getString(ACCOUNT_TYPE);
            User user = new User(rs.getLong(USER_ID));
            Date lastTimeActive = rs.getTimestamp(LAST_TIME_ACTIVE);
            Account account = new Account(accountId, email, password,
                    accountType, user, lastTimeActive);
            list.add(account);
        }
        return list;
    }
}
