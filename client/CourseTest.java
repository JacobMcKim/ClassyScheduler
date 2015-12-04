import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zack on 15/11/15.
 */
public class CourseTest {

    @Test
    public void testAddSection() throws Exception {

        Course cis350 = new Course(1, 350, "Software Engineering", "This is a class.");
        Course cis263 = new Course(1, 263, "Data Structures", "This is a another class.");

        Semester sem = new Semester(SemEnum.FALL, 15,1);

        Section s1 = new Section(cis350, 1, 1000, 1050, "MWF");
        Section s2 = new Section(cis350, 2, 1100, 1150, "MWF");
        Section tc1 = new Section(cis350, 3, 1000, 1050, "MWF");
        Section s3 = new Section(cis263, 1, 1100, 1150, "MWF");

        assertEquals(cis350.addSection(s1), 0);
        assertEquals(cis350.addSection(s2), 1);
        assertEquals(cis350.addSection(s3), -1);
        assertEquals(cis350.addSection(tc1), 2);

    }

    @Test
    public void testCompare() throws Exception {

        Course cis350 = new Course(1, 350, "Software Engineering", "This is a class.");
        Course cis263 = new Course(1, 263, "Data Structures", "This is a another class.");

        assertTrue(cis350.compare(cis350));
        assertFalse(cis350.compare(cis263));
    }
}