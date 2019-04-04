package com.danarossa.database.concretedao;

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


    private final String account_id = "ACCOUNT_ID";
    private final String email = "EMAIL";
    private final String password = "PASSWORD";
    private final String account_type = "ACCOUNT_TYPE";
    private final String user_id = "USER_ID";
    private final String last_time_active = "LAST_TIME_ACTIVE";
    private final String table = "ACCOUNTS";
    private String account_next_primary_key = "account_next_primary_key";

    public AccountDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getSelectByIdQuery() {
        return "select " + account_id + ", " + email + ", " + password + ", " + account_type + ", " + user_id + ", " + last_time_active + "\n" +
                "from " + table + " where " + account_id + " = ?;";
    }

    @Override
    protected String getSelectQuery() {
        return "select " + account_id + ", " + email + ", " + password + ", " + account_type + ", " + user_id + ", " + last_time_active + "\n" +
                "from " + table + ";";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into accounts(" + account_id + ", " + email + ", " + password + ", " + account_type + ", " + user_id + ")\n" +
                "values (?, ?, ?, ?, ?);";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from " + table + " where " + account_id + " = ?;";
    }

    @Override
    protected String getUpdateQuery() {
        return "update " + table + " set " + email + " = ?, " + password + " = ?, " + account_type + " = ?, " + user_id + " = ? where " + account_id + " = ?;";
    }

    @Override
    protected String getSelectNextPrimaryKeyQuery() {
        return "select " + table + "_SQ.nextval as " + account_next_primary_key + " from dual;";
    }

    @Override
    protected void setId(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Account entity) throws SQLException {
//        account_id, email, password, account_type, user_id
        statement.setLong(1, entity.getId());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getPassword());
        statement.setString(4, entity.getAccountType());
        statement.setLong(5, entity.getUserId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Account entity) throws SQLException {
        statement.setString(1, entity.getEmail());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getAccountType());
        statement.setLong(4, entity.getUserId());
        statement.setLong(5, entity.getId());
    }

    @Override
    protected Long parseResultSetForPrimaryKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong(account_next_primary_key);
        } else throw new PersistException("No value returned!");
    }

    @Override
    protected List<Account> parseResultSet(ResultSet rs) throws SQLException {
        List<Account> list = new ArrayList<>();
        while (rs.next()) {
            Long accountId = rs.getLong(this.account_id);
            String email = rs.getString(this.email);
            String password = rs.getString(this.password);
            String accountType = rs.getString(this.account_type);
            User user = new User(rs.getLong(this.user_id));
            Date lastTimeActive = rs.getTimestamp(this.last_time_active);
            Account account = new Account(accountId, email, password, accountType, user, lastTimeActive);
            list.add(account);
        }
        return list;
    }
}
