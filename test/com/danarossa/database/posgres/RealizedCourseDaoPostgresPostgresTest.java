package com.danarossa.database.posgres;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.RealizedCourseDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.RealizedCourse;
import com.danarossa.entities.User;
import com.danarossa.router.Role;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class RealizedCourseDaoPostgresPostgresTest {
    static private RealizedCourseDao realizedCourseDao;
    private static Integer id;
    private static RealizedCourse realizedCourse;
    private static Course course;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        realizedCourseDao = oracleDaoFactory.getRealizedCourseDao();
        System.out.println("instantiated realizedCourseDao");
        User lecturer = new User(1,"newLecturer", "NewOne", "email", "pass", new Date(526645), Role.TRAINER);
        course = new Course(1, "newObject", 10, 100, 30,  lecturer.getId());
        realizedCourse = new RealizedCourse( course.getId(), new Date(35465), new Date(8788), new Date(), "closed");
    }

    @AfterClass
    public static void after() {
        try {
            realizedCourseDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        realizedCourse.setId(null);
        realizedCourseDao.insert(realizedCourse);
        id = realizedCourse.getId();
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
        RealizedCourse realizedCourse = new RealizedCourse(id, course.getId(), new Date(666), new Date(555), new Date(888), "announced");
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
        RealizedCourse newCourse = new RealizedCourse(course.getId(), new Date(666), new Date(555), new Date(888), "announced");
        realizedCourseDao.insert(newCourse);
        assertNotNull(newCourse.getId());
        Integer newID = newCourse.getId();
        assertTrue(newCourse.getId() != 0);
        assertEquals(before + 1, realizedCourseDao.getAll().size());
        RealizedCourse someNewCourse = realizedCourseDao.getEntityById(newID);
        assertNotNull(someNewCourse);
        assertEquals(newCourse, someNewCourse);
    }


    @Test
    public void getAllRealizedCoursesOfLecturer() {
        assertTrue(realizedCourseDao.getAllRealizedCoursesOfLecturer(2).size() > 0);
        //TODO
    }

    @Test
    public void getAllRealizedCoursesOfStudent() {
        assertTrue(realizedCourseDao.getAllRealizedCoursesOfStudent(4).size() > 0);
        //TODO
    }

    @Test
    public void getAllRealizedCoursesOfCourse() {
        assertTrue(realizedCourseDao.getAllRealizedCoursesOfCourse(2).size() > 0);
        //TODO
    }
}