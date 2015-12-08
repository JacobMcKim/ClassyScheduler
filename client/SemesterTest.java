import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zack on 24/11/15.
 */
public class SemesterTest {

    @Test
    public void testEquals() throws Exception {

        Semester s1 = new Semester(SemEnum.FALL, 16,1);
        Semester s2 = new Semester(SemEnum.FALL, 17,4);
        Semester s3 = new Semester(SemEnum.SUMMER_A, 16,3);
        Semester s4 = new Semester(SemEnum.WINTER, 17,2);
        Semester s5 = new Semester(SemEnum.FALL, 16,1);

        assertTrue(s1.equals(s5));
        assertFalse(s1.equals(s2));
        assertFalse(s1.equals(s3));
        assertFalse(s1.equals(s4));
    }
}