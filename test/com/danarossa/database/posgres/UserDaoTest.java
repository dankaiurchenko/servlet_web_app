package com.danarossa.database.posgres;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.IUserDao;
import com.danarossa.entities.User;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class UserDaoTest {

    static private IUserDao iUserDao;
    private static User testInsertedUser;
    private static Integer id;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        iUserDao = oracleDaoFactory.getUserDao();
        System.out.println("instantiated accountDao");
        testInsertedUser = new User("name", "surname", "email", "password", new Date(5454545), "student");
    }

    @AfterClass
    public static void after() {
        try {
            iUserDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        testInsertedUser.setId(null);
        iUserDao.insert(testInsertedUser);
        id = testInsertedUser.getId();
    }

    @After
    public void tearDown() {
        if (iUserDao.getEntityById(id) != null){
            iUserDao.delete(id);
        }
    }


    @Test
    public void getAll() {
        assertTrue(iUserDao.getAll().size() > 0);
    }

    @Test
    public void getEntityById() {
        User lecturer = iUserDao.getEntityById(id);
        assertNotNull(lecturer);
        assertEquals(testInsertedUser, lecturer);
    }

    @Test
    public void update() {
        User preInsertedUser = iUserDao.getEntityById(id);
        assertNotNull(preInsertedUser);
        assertEquals(testInsertedUser, preInsertedUser);

        User updatedUser = new User(id, "name", "updated1", "updated2","Matt", new Date(54),"admin");
        iUserDao.update(updatedUser);

        User retrievedUpdatedUser = iUserDao.getEntityById(id);
        assertNotNull(retrievedUpdatedUser);
        assertEquals(updatedUser, retrievedUpdatedUser);
    }

    @Test
    public void delete() {
        List<User> users = iUserDao.getAll();
        assertTrue(users.size() > 0);
        int before = users.size();
        iUserDao.delete(id);
        assertEquals(before - 1, iUserDao.getAll().size());
        assertNull(iUserDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<User> lecturers = iUserDao.getAll();
        assertTrue(lecturers.size() > 0);
        int before = lecturers.size();
        User user = new User("new", "and Damon", "newOne", "new", new Date(2334), "student");
        iUserDao.insert(user);
        assertNotNull(user.getId());
        Integer newID = user.getId();
        assertTrue(newID != 0);
        assertEquals(before + 1, iUserDao.getAll().size());
        User retrievedNewUser = iUserDao.getEntityById(newID);
        assertNotNull(retrievedNewUser);
        assertEquals(user, retrievedNewUser);
    }


    @Test
    public void getAllLecturersForStudent() {
        assertTrue(iUserDao.getAllLecturersForStudent(4).size() > 0);
        //TODO
    }
}