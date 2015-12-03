import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zack on 15/11/15.
 */
public class SectionTest {

    @Test
    public void testCheckTimeConflict() throws Exception {

        Course cis350 = new Course(1, 350, "Software Engineering", "This is a class.");

        Semester s = new Semester(SemEnum.FALL, 15);

        Section s1 = new Section(cis350, 1, 1000, 1050, "MWF", s);
        Section s2 = new Section(cis350, 2, 1100, 1150, "MWF",s);
        Section s3 = new Section(cis350, 3, 1000, 1050, "th",s);
        Section tc1 = new Section(cis350, 4, 1000, 1050, "MWF",s);

        assertFalse(s1.checkTimeConflict(s2));
        assertFalse(s1.checkTimeConflict(s3));
        assertFalse(s3.checkTimeConflict(s2));
        assertTrue(s1.checkTimeConflict(tc1));

    }
}