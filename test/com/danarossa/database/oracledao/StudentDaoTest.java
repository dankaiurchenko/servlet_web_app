package com.danarossa.database.oracledao;

import com.danarossa.database.AbstractDaoFactory;
import com.danarossa.database.daointerfaces.IStudentDao;
import com.danarossa.entities.Student;
import org.junit.*;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

public class StudentDaoTest {
    static private IStudentDao studentDao;
    private static Student newStudent;
    private static Long id;

    @BeforeClass
    public static void before() {
        AbstractDaoFactory oracleDaoFactory = AbstractDaoFactory.getDaoFactory();
        studentDao = oracleDaoFactory.getStudentDao();
        System.out.println("instantiated accountDao");
        id = studentDao.getNextPrimaryKey();
        newStudent = new Student(id, "name", "surname", new Date(5454545), new Date());
    }

    @AfterClass
    public static void after() {
        try {
            studentDao.rollback();
            studentDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        studentDao.insert(newStudent);
    }

    @After
    public void tearDown() {
        if (studentDao.getEntityById(id) != null){
            studentDao.delete(id);
        }
    }


    @Test
    public void getAll() {
        assertTrue(studentDao.getAll().size() > 0);
    }

    @Test
    public void getEntityById() {
        Student student = studentDao.getEntityById(id);
        assertNotNull(student);
        assertEquals(newStudent, student);
    }

    @Test
    public void update() {
        Student student = studentDao.getEntityById(id);
        assertNotNull(student);
        assertEquals(newStudent, student);

        Student student2 = new Student(id, "updated1", "updated2",new Date(54), new Date(54545));
        studentDao.update(student2);

        Student student20 = studentDao.getEntityById(id);
        assertNotNull(student20);
        assertEquals(student2, student20);
    }

    @Test
    public void delete() {
        List<Student> students = studentDao.getAll();
        assertTrue(students.size() > 0);
        int before = students.size();
        studentDao.delete(id);
        assertEquals(before - 1, studentDao.getAll().size());
        assertNull(studentDao.getEntityById(id));
    }

    @Test
    public void insert() {
        List<Student> Students = studentDao.getAll();
        assertTrue(Students.size() > 0);
        int before = Students.size();
        Student student = new Student("newOne", "new", new Date(2334), new Date());
        studentDao.insert(student);
        assertNotNull(student.getId());
        Long newID = student.getId();
        assertTrue(newID != 0);
        assertEquals(before + 1, studentDao.getAll().size());
        Student someStudent = studentDao.getEntityById(newID);
        assertNotNull(someStudent);
        assertEquals(student, someStudent);
    }

    @Test
    public void getNextPrimaryKey() {
        Long firstOne = studentDao.getNextPrimaryKey();
        Long secondOne = studentDao.getNextPrimaryKey();
        assertTrue(secondOne > firstOne);
    }
}