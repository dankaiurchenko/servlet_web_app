package com.danarossa.database.posgres;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.CourseDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.User;
import com.danarossa.router.Role;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class CourseDaoPostgresTest {

    static private CourseDao courseDao;
    private static Integer id;
    private static User lecturer;
    private static Course course1;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        courseDao = oracleDaoFactory.getCourseDao();
        System.out.println("instantiated courseDao");
        lecturer = new User(1,"newLecturer", "NewOne", "email", "pass", new Date(526645), Role.TRAINER);
        course1 = new Course("newObject", 10, 100, 30,  lecturer.getId());
    }

    @AfterClass
    public static void after() {
        try {
            courseDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        course1.setId(null);
        courseDao.insert(course1);
        id = course1.getId();
    }

    @After
    public void tearDown() {
        if(courseDao.getEntityById(id) != null){
            courseDao.delete(id);
        }
    }

    @Test
    public void getAll() {
        List<Course> courses = courseDao.getAll();
        assertTrue(courses.size() >= 1);
    }

    @Test
    public void getEntityById() {
        Course course = courseDao.getEntityById(id);
        assertEquals(course1, course);
    }

    @Test
    public void update() {
        Course courseNew = new Course(id, "updated", 2, 1, 1, lecturer.getId());
        courseDao.update(courseNew);
        Course course = courseDao.getEntityById(id);
        assertEquals(courseNew, course);
    }

    @Test
    public void delete() {
        List<Course> courses = courseDao.getAll();
        assertTrue(courses.size() > 0);
        int before = courses.size();
        courseDao.delete(id);
        assertEquals(before-1, courseDao.getAll().size());
        assertNull(courseDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<Course> courses = courseDao.getAll();
        int before = courses.size();
        Course newCourse = new Course("newCourse", 2,3,4,lecturer.getId());
        courseDao.insert(newCourse);
        assertNotNull(newCourse.getId());
        Integer newID = newCourse.getId();
        assertTrue(newCourse.getId() != 0);
        assertEquals(before+1, courseDao.getAll().size());
        Course someNewCourse = courseDao.getEntityById(newID);
        assertNotNull(someNewCourse);
        assertEquals(newCourse, someNewCourse);
    }

    @Test
    public void getAllCoursesOfLecturer() {
        List<Course> courses = courseDao.getAllCoursesOfLecturer(1);
        assertTrue(courses.size() > 0);
        //TODO
    }

    @Test
    public void getAllCoursesOfStudent() {
        List<Course> courses = courseDao.getAllCoursesOfStudent(4);
        assertTrue(courses.size() > 0);
        //TODO
    }
}