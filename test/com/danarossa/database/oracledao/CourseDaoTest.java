package com.danarossa.database.oracledao;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.ICourseDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.Lecturer;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class CourseDaoTest {

    static private ICourseDao courseDao;
    private static Long id;
    private static Lecturer lecturer;
    private static Course course1;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        courseDao = oracleDaoFactory.getCourseDao();
        System.out.println("instantiated courseDao");
        id = courseDao.getNextPrimaryKey();
        lecturer = new Lecturer(1L,"newLecturer", "NewOne", new Date(526645), "position", new Date());
        course1 = new Course(id, "newObject", 10, 100, 30, 50, 20, lecturer);
    }

    @AfterClass
    public static void after() {
        try {
            courseDao.rollback();
            courseDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        courseDao.insert(course1);
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
        Course courseNew = new Course(id, "updated", 2,
                1, 1, 1, 1, lecturer);
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
        Course newCourse = new Course("newCourse", 2,3,4,5,6,lecturer);
        courseDao.insert(newCourse);
        assertNotNull(newCourse.getId());
        Long newID = newCourse.getId();
        assertTrue(newCourse.getId() != 0);
        assertEquals(before+1, courseDao.getAll().size());
        Course someNewCourse = courseDao.getEntityById(newID);
        assertNotNull(someNewCourse);
        assertEquals(newCourse, someNewCourse);
    }

    @Test
    public void getNextPrimaryKey() {
        Long firstOne = courseDao.getNextPrimaryKey();
        Long secondOne = courseDao.getNextPrimaryKey();
        assertTrue(secondOne > firstOne);
    }

    @Test
    public void getAllCoursesOfLecturer() {
        List<Course> courses = courseDao.getAllCoursesOfLecturer(1L);
        assertTrue(courses.size() > 0);
        //TODO
    }

    @Test
    public void getAllCoursesOfStudent() {
        List<Course> courses = courseDao.getAllCoursesOfStudent(4L);
        assertTrue(courses.size() > 0);
        //TODO
    }
}