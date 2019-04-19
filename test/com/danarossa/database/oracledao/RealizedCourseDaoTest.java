package com.danarossa.database.oracledao;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.IRealizedCourseDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.RealizedCourse;
import com.danarossa.entities.User;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class RealizedCourseDaoTest {
    static private IRealizedCourseDao realizedCourseDao;
    private static Long id;
    private static RealizedCourse realizedCourse;
    private static Course course;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        realizedCourseDao = oracleDaoFactory.getRealizedCourseDao();
        System.out.println("instantiated realizedCourseDao");
        id = realizedCourseDao.getNextPrimaryKey();

        User lecturer = new User(1L,"newLecturer", "NewOne", "email", "pass", new Date(526645), "lecturer");
        course = new Course(1L, "newObject", 10, 100, 30, 50, 20, lecturer);
        realizedCourse = new RealizedCourse(id, course, new Date(35465), new Date(8788), new Date(), "closed");
    }

    @AfterClass
    public static void after() {
        try {
            realizedCourseDao.rollback();
            realizedCourseDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        realizedCourseDao.insert(realizedCourse);
    }

    @After
    public void tearDown() {
        if (realizedCourseDao.getEntityById(id) != null) {
            realizedCourseDao.delete(id);
        }
    }

    @Test
    public void getAll() {
        List<RealizedCourse> courses = realizedCourseDao.getAll();
        assertTrue(courses.size() >= 1);
    }

    @Test
    public void getEntityById() {
        RealizedCourse course = realizedCourseDao.getEntityById(id);
        assertEquals(realizedCourse, course);
    }

    @Test
    public void update() {
        RealizedCourse realizedCourse = new RealizedCourse(id, course, new Date(666), new Date(555), new Date(888), "announced");
        realizedCourseDao.update(realizedCourse);
        RealizedCourse course = realizedCourseDao.getEntityById(id);
        assertEquals(realizedCourse, course);
    }

    @Test
    public void delete() {
        List<RealizedCourse> courses = realizedCourseDao.getAll();
        assertTrue(courses.size() > 0);
        int before = courses.size();
        realizedCourseDao.delete(id);
        assertEquals(before - 1, realizedCourseDao.getAll().size());
        assertNull(realizedCourseDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<RealizedCourse> courses = realizedCourseDao.getAll();
        int before = courses.size();
        RealizedCourse newCourse = new RealizedCourse(course, new Date(666), new Date(555), new Date(888), "announced");
        realizedCourseDao.insert(newCourse);
        assertNotNull(newCourse.getId());
        Long newID = newCourse.getId();
        assertTrue(newCourse.getId() != 0);
        assertEquals(before + 1, realizedCourseDao.getAll().size());
        RealizedCourse someNewCourse = realizedCourseDao.getEntityById(newID);
        assertNotNull(someNewCourse);
        assertEquals(newCourse, someNewCourse);
    }

    @Test
    public void getNextPrimaryKey() {
        Long firstOne = realizedCourseDao.getNextPrimaryKey();
        Long secondOne = realizedCourseDao.getNextPrimaryKey();
        assertTrue(secondOne > firstOne);
    }

    @Test
    public void getAllRealizedCoursesOfLecturer() {
        assertTrue(realizedCourseDao.getAllRealizedCoursesOfLecturer(2L).size() > 0);
        //TODO
    }

    @Test
    public void getAllRealizedCoursesOfStudent() {
        assertTrue(realizedCourseDao.getAllRealizedCoursesOfStudent(4L).size() > 0);
        //TODO
    }

    @Test
    public void getAllRealizedCoursesOfCourse() {
        assertTrue(realizedCourseDao.getAllRealizedCoursesOfCourse(2L).size() > 0);
        //TODO
    }
}