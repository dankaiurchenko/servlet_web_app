package com.danarossa.database.oracledao;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.IAccountDao;
import com.danarossa.entities.Account;
import com.danarossa.entities.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.*;

public class AccountDaoTest {
    static private IAccountDao accountDao;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        accountDao = oracleDaoFactory.getAccountDao();
        System.out.println("instantiated accountDao");
    }

    @AfterClass
    public static void after() {
        try {
            accountDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAll() {
        List<Account> accounts = accountDao.getAll();
        assertTrue(accounts.size() > 0);
//        assertEquals(10, accounts.size());
    }

    @Test
    public void getEntityById() {
        Account account = accountDao.getEntityById(1L);
        assertNotNull(account);
        assertEquals("admin", account.getEmail());
        assertEquals("admin", account.getPassword());
        assertEquals("admin", account.getAccountType());
    }

    @Test
    public void update() {
//        7	student_6@email.com	01121984	student
        Account account = accountDao.getEntityById(7L);
        assertNotNull(account);
        assertEquals("student_6@email.com", account.getEmail());
        assertEquals("01121984", account.getPassword());
        assertEquals("student", account.getAccountType());

        Account newAccount = new Account(7L, "someMail", "pass", "admin", new User(0L));
        accountDao.update(newAccount);

        account = accountDao.getEntityById(7L);
        assertNotNull(account);
        assertEquals("someMail", account.getEmail());
        assertEquals("pass", account.getPassword());
        assertEquals("admin", account.getAccountType());
    }

    @Test
    public void delete() {
        List<Account> accounts = accountDao.getAll();
        assertTrue(accounts.size() > 0);
        int before = accounts.size();
        long id = accounts.get(before-1).getUserId();
        accountDao.delete(id);
        accounts = accountDao.getAll();
        assertEquals(before-1, accounts.size());
        assertNull(accountDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<Account> accounts = accountDao.getAll();
        assertTrue(accounts.size() > 0);
        int before = accounts.size();
        Account newAccount = new Account("email", "pass", "student", new User(0L));
        accountDao.insert(newAccount);
        assertNotNull(newAccount.getId());
        Long newID = newAccount.getId();
        assertTrue(newAccount.getId() != 0);
        accounts = accountDao.getAll();
        assertEquals(before + 1, accounts.size());
        newAccount = accountDao.getEntityById(newID);
        assertNotNull(newAccount);
        assertEquals("email", newAccount.getEmail());
        assertEquals("pass", newAccount.getPassword());
        assertEquals("student", newAccount.getAccountType());
        assertEquals(Long.valueOf(0L), newAccount.getUserId());
    }

    @Test
    public void getNextPrimaryKey() {
        Long firstOne = accountDao.getNextPrimaryKey();
        Long secondOne = accountDao.getNextPrimaryKey();
        assertTrue(secondOne > firstOne);
    }
}