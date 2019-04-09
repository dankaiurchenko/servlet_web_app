package com.danarossa.database.oracledao;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.ILecturerDao;
import com.danarossa.entities.Lecturer;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class LecturerDaoTest {

    static private ILecturerDao lecturerDao;
    private static Lecturer newLecturer;
    private static Long id;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        lecturerDao = oracleDaoFactory.getLecturerDao();
        System.out.println("instantiated accountDao");
        id = lecturerDao.getNextPrimaryKey();
        newLecturer = new Lecturer(id, "name", "surname", new Date(5454545),"great lecturer", new Date());
    }

    @AfterClass
    public static void after() {
        try {
            lecturerDao.rollback();
            lecturerDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        lecturerDao.insert(newLecturer);
    }

    @After
    public void tearDown() {
        if (lecturerDao.getEntityById(id) != null){
            lecturerDao.delete(id);
        }
    }


    @Test
    public void getAll() {
        assertTrue(lecturerDao.getAll().size() > 0);
    }

    @Test
    public void getEntityById() {
        Lecturer lecturer = lecturerDao.getEntityById(id);
        assertNotNull(lecturer);
        assertEquals(newLecturer, lecturer);
    }

    @Test
    public void update() {
        Lecturer lecturer = lecturerDao.getEntityById(id);
        assertNotNull(lecturer);
        assertEquals(newLecturer, lecturer);

        Lecturer lecturer2 = new Lecturer(id, "updated1", "updated2",new Date(54),"updated3", new Date(54545));
        lecturerDao.update(lecturer2);

        Lecturer lecturer20 = lecturerDao.getEntityById(id);
        assertNotNull(lecturer20);
        assertEquals(lecturer2, lecturer20);
    }

    @Test
    public void delete() {
        List<Lecturer> lecturers = lecturerDao.getAll();
        assertTrue(lecturers.size() > 0);
        int before = lecturers.size();
        lecturerDao.delete(id);
        assertEquals(before - 1, lecturerDao.getAll().size());
        assertNull(lecturerDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<Lecturer> lecturers = lecturerDao.getAll();
        assertTrue(lecturers.size() > 0);
        int before = lecturers.size();
        Lecturer lecturer = new Lecturer("newOne", "new", new Date(2334), "position", new Date());
        lecturerDao.insert(lecturer);
        assertNotNull(lecturer.getId());
        Long newID = lecturer.getId();
        assertTrue(newID != 0);
        assertEquals(before + 1, lecturerDao.getAll().size());
        Lecturer someLecturer = lecturerDao.getEntityById(newID);
        assertNotNull(someLecturer);
        assertEquals(lecturer, someLecturer);
    }

    @Test
    public void getNextPrimaryKey() {
        Long firstOne = lecturerDao.getNextPrimaryKey();
        Long secondOne = lecturerDao.getNextPrimaryKey();
        assertTrue(secondOne > firstOne);
    }

    @Test
    public void getAllLecturersForStudent() {
        assertTrue(lecturerDao.getAllLecturersForStudent(4L).size() > 0);
        //TODO
    }
}