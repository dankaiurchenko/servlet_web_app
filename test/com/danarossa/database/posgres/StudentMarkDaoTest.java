package com.danarossa.database.posgres;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.IStudentMarkDao;
import com.danarossa.entities.Course;
import com.danarossa.entities.RealizedCourse;
import com.danarossa.entities.StudentMark;
import com.danarossa.entities.User;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class StudentMarkDaoTest {
    static private IStudentMarkDao studentMarkDao;
    private static Integer id;
    private static StudentMark studentMark;
    private static Course course;
    private static RealizedCourse realizedCourse;
    private static User student;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        studentMarkDao = oracleDaoFactory.getStudentMarkDao();
        System.out.println("instantiated studentMarkDao");
        course = oracleDaoFactory.getCourseDao().getEntityById(1);
        realizedCourse = oracleDaoFactory.getRealizedCourseDao().getEntityById(2);
        student = oracleDaoFactory.getUserDao().getEntityById(5);
        studentMark = new StudentMark(student, realizedCourse.getId(), 5.0);
    }

    @AfterClass
    public static void after() {
        try {
            studentMarkDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        studentMark.setId(null);
        System.out.println(studentMark);
        System.out.println("before insertion   ");
        studentMarkDao.insert(studentMark);
        id = studentMark.getId();
    }

    @After
    public void tearDown() {
        if(studentMarkDao.getEntityById(id) != null){
            studentMarkDao.delete(id);
        }
    }

    @Test
    public void getAll() {
        List<StudentMark> courses = studentMarkDao.getAll();
        assertTrue(courses.size() >= 1);
    }

    @Test
    public void getEntityById() {
        StudentMark studentMark1 = studentMarkDao.getEntityById(id);
        assertEquals(studentMark, studentMark1);
    }

    @Test
    public void update() {
        RealizedCourse newRealizedCourse = new RealizedCourse(3, course.getId(), new Date(35465), new Date(8788), new Date(), "closed");
        User newStudent = AbstractDaoFactory.getDaoFactory().getUserDao().getEntityById(4);
        StudentMark studentMark = new StudentMark(id, newStudent, newRealizedCourse.getId(), 2.0);
        studentMarkDao.update(studentMark);
        StudentMark studentMark1 = studentMarkDao.getEntityById(id);
        assertEquals(studentMark, studentMark1);
    }

    @Test
    public void delete() {
        List<StudentMark> studentMarks = studentMarkDao.getAll();
        assertTrue(studentMarks.size() > 0);
        int before = studentMarks.size();
        studentMarkDao.delete(id);
        assertEquals(before-1, studentMarkDao.getAll().size());
        assertNull(studentMarkDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<StudentMark> courses = studentMarkDao.getAll();
        int before = courses.size();
        StudentMark studentMark1 = new StudentMark(student, realizedCourse.getId(), 3.0);
        studentMarkDao.insert(studentMark1);
        assertNotNull(studentMark1.getId());
        Integer newID = studentMark1.getId();
        assertTrue(studentMark1.getId() != 0);
        assertEquals(before+1, studentMarkDao.getAll().size());
        StudentMark someNewMark = studentMarkDao.getEntityById(newID);
        assertNotNull(someNewMark);
        assertEquals(studentMark1, someNewMark);
    }

    @Test
    public void getStudentMarksForRealizedCourse() {
        List<StudentMark> studentMarks = studentMarkDao.getStudentMarksForRealizedCourse(1);
        System.out.println(studentMarks);
        assertTrue(studentMarks.size() > 0);
        //TODO
    }

    @Test
    public void getStudentMarksForStudent() {
        //TODO
        List<StudentMark> studentMarks = studentMarkDao.getStudentMarksForStudent(4);
        assertTrue(studentMarks.size() > 0);
    }
}