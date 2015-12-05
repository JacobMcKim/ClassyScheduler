import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by zack on 15/11/15.
 */
public class ClassyHandlerTest {

    @Test
    public void testGetArraySearchBuf() throws Exception {

        ClassyHandler classy = new ClassyHandler(1);

        Course[] courses = classy.getArraySearchBuf();
        ArrayList<Course> al = classy.getSearchBuffer();

        for(int i = 0; i < courses.length; i++) {
            assertTrue(courses[i].compare(al.get(i)));
        }

    }

    @Test
    public void testGetArrayStuSched() throws Exception {

        ClassyHandler classy = new ClassyHandler(1);

        Section[] sections = classy.getArrayStuSched();
        ArrayList<Section> al = classy.getStudentSched();

        for(int i = 0; i < sections.length; i++) {
            assertEquals(sections[i].getSID(), al.get(i).getSID());
            assertTrue(sections[i].getCourse().compare(al.get(i).getCourse()));
        }
    }

    @Test
    public void testAddSectionToSched() throws Exception {

        ClassyHandler classy = new ClassyHandler(1);
        assertEquals(classy.addSectionToSched(classy.getSearchBuffer().get(0).getSection(1)), -1);
        assertEquals(classy.addSectionToSched(classy.getSearchBuffer().get(1).getSection(0)), -1);
        assertEquals(classy.addSectionToSched(classy.getSearchBuffer().get(1).getSection(1)), 1);


    }

    @Test
    public void testLoadDefaultSearchBuff() throws Exception {

        ClassyHandler classy = new ClassyHandler();
        classy.loadDefaultSearchBuff();

        ArrayList<Course> searchBuf = classy.getSearchBuffer();
        assertEquals(searchBuf.size(), 5);

        assertEquals(searchBuf.get(0).getSectionList().size(), 4);
        assertEquals(searchBuf.get(1).getSectionList().size(), 2);
        assertEquals(searchBuf.get(2).getSectionList().size(), 1);
        assertEquals(searchBuf.get(3).getSectionList().size(), 1);
        assertEquals(searchBuf.get(4).getSectionList().size(), 1);
    }

    @Test
    public void testLoadSearchBuffer() {

        ClassyHandler classy = new ClassyHandler();
        classy.loadDefaultSearchBuff();

        classy.loadSearchBuffer("CIS 350");
        assertEquals(classy.getSearchBuffer().size(),1);

        classy.loadSearchBuffer("CIS");
        assertEquals(classy.getSearchBuffer().size(),4);
    }

    @Test
    public void testLoadSemester() {

        ClassyHandler classy = new ClassyHandler();
        ArrayList<Semester> sems = classy.getSemseters();

        assertEquals(sems.size(), 8);
    }

    @Test
    public void testLoginLogout() {

        ClassyHandler classy = new ClassyHandler();

        classy.login("mckims@mail.gvsu.edu", "samm");
        assertNotEquals(classy.getStudent(), null);

        classy.logout();
        assertTrue(classy.getStudent() == null);
        assertTrue(classy.getStudentSched().size() == 0);
    }
}