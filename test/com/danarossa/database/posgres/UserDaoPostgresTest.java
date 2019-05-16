package com.danarossa.database.posgres;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.UserDao;
import com.danarossa.entities.User;
import com.danarossa.router.Role;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class UserDaoPostgresTest {

    static private UserDao userDao;
    private static User testInsertedUser;
    private static Integer id;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        userDao = oracleDaoFactory.getUserDao();
        System.out.println("instantiated accountDao");
        testInsertedUser = new User("name", "surname", "email", "password", new Date(5454545), Role.STUDENT);
    }

    @AfterClass
    public static void after() {
        try {
            userDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        testInsertedUser.setId(null);
        userDao.insert(testInsertedUser);
        id = testInsertedUser.getId();
    }

    @After
    public void tearDown() {
        if (userDao.getEntityById(id) != null){
            userDao.delete(id);
        }
    }


    @Test
    public void getAll() {
        assertTrue(userDao.getAll().size() > 0);
    }

    @Test
    public void getEntityById() {
        User lecturer = userDao.getEntityById(id);
        assertNotNull(lecturer);
        assertEquals(testInsertedUser, lecturer);
    }

    @Test
    public void update() {
        User preInsertedUser = userDao.getEntityById(id);
        assertNotNull(preInsertedUser);
        assertEquals(testInsertedUser, preInsertedUser);

        User updatedUser = new User(id, "name", "updated1", "updated2","Matt", new Date(54),Role.ADMIN);
        userDao.update(updatedUser);

        User retrievedUpdatedUser = userDao.getEntityById(id);
        assertNotNull(retrievedUpdatedUser);
        assertEquals(updatedUser, retrievedUpdatedUser);
    }

    @Test
    public void delete() {
        List<User> users = userDao.getAll();
        assertTrue(users.size() > 0);
        int before = users.size();
        userDao.delete(id);
        assertEquals(before - 1, userDao.getAll().size());
        assertNull(userDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<User> lecturers = userDao.getAll();
        assertTrue(lecturers.size() > 0);
        int before = lecturers.size();
        User user = new User("new", "and Damon", "newOne", "new", new Date(2334), Role.STUDENT);
        userDao.insert(user);
        assertNotNull(user.getId());
        Integer newID = user.getId();
        assertTrue(newID != 0);
        assertEquals(before + 1, userDao.getAll().size());
        User retrievedNewUser = userDao.getEntityById(newID);
        assertNotNull(retrievedNewUser);
        assertEquals(user, retrievedNewUser);
    }


    @Test
    public void getAllLecturersForStudent() {
        assertTrue(userDao.getAllLecturersForStudent(4).size() > 0);
        //TODO
    }
}