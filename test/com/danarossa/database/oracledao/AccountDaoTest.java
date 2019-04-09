package com.danarossa.database.oracledao;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.IAccountDao;
import com.danarossa.entities.Account;
import com.danarossa.entities.User;
import org.junit.*;

import java.util.List;

import static junit.framework.TestCase.*;

public class AccountDaoTest {
    static private IAccountDao accountDao;
    private static Account newAccount;
    private static Long id;
    private static final AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();

    @BeforeClass
    public static void before() {
        accountDao = oracleDaoFactory.getAccountDao();
        System.out.println("instantiated accountDao");
        id = accountDao.getNextPrimaryKey();
        newAccount = new Account(id, "email", "pass", "student", new User(5L));
    }

    @AfterClass
    public static void after() {
        try {
            accountDao.rollback();
//            accountDao.close();
            oracleDaoFactory.closeAllConnections();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        accountDao.insert(newAccount);
    }

    @After
    public void tearDown() {
        if (accountDao.getEntityById(id) != null){
            accountDao.delete(id);
        }
    }

    @Test
    public void getAll() {
        List<Account> accounts = accountDao.getAll();
        assertTrue(accounts.size() > 0);
    }

    @Test
    public void getEntityById() {
        Account account = accountDao.getEntityById(id);
        assertNotNull(account);
        assertEquals(newAccount.getEmail(), account.getEmail());
        assertEquals(newAccount.getPassword(), account.getPassword());
        assertEquals(newAccount.getAccountType(), account.getAccountType());
    }

    @Test
    public void update() {
        Account account = accountDao.getEntityById(id);
        assertNotNull(account);
        assertEquals(newAccount.getEmail(), account.getEmail());
        assertEquals(newAccount.getPassword(), account.getPassword());
        assertEquals(newAccount.getAccountType(), account.getAccountType());

        Account newAccount2 = new Account(id, "someMail", "pass", "admin", new User(0L));
        accountDao.update(newAccount2);

        Account account20 = accountDao.getEntityById(id);
        assertNotNull(account);
        assertEquals(newAccount2.getEmail(), account20.getEmail());
        assertEquals(newAccount2.getPassword(), account20.getPassword());
        assertEquals(newAccount2.getAccountType(), account20.getAccountType());
    }

    @Test
    public void delete() {
        List<Account> accounts = accountDao.getAll();
        assertTrue(accounts.size() > 0);
        int before = accounts.size();
        accountDao.delete(id);
        assertEquals(before - 1, accountDao.getAll().size());
        assertNull(accountDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<Account> accounts = accountDao.getAll();
        assertTrue(accounts.size() > 0);
        int before = accounts.size();
        Account newAccount = new Account("email", "pass", "student", new User(6L));
        accountDao.insert(newAccount);
        assertNotNull(newAccount.getId());
        Long newID = newAccount.getId();
        assertTrue(newAccount.getId() != 0);
        assertEquals(before + 1, accountDao.getAll().size());
        Account someNewAccount20 = accountDao.getEntityById(newID);
        assertNotNull(someNewAccount20);
        assertEquals(newAccount, someNewAccount20);
    }

    @Test
    public void getNextPrimaryKey() {
        Long firstOne = accountDao.getNextPrimaryKey();
        Long secondOne = accountDao.getNextPrimaryKey();
        assertTrue(secondOne > firstOne);
    }
}