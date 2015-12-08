import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zack on 15/11/15.
 */
public class WeeklyScheduleTest {

    @Test
    public void testConstructor() throws Exception {
        String m = "m";
        String mwf = "mwf";
        String th = "tr";
        String mtwhf = "MTWrf";

        WeeklySchedule m1 = new WeeklySchedule(m);
        WeeklySchedule mwf1 = new WeeklySchedule(mwf);
        WeeklySchedule th1 = new WeeklySchedule(th);
        WeeklySchedule mtwhf1 = new WeeklySchedule(mtwhf);

        for(int i = 0; i<5; i++) {
            if(i == 0)
                assertTrue(m1.day(i));
            else
                assertFalse(m1.day(i));
        }

        for(int i = 0; i<5; i++) {
            if(i % 2 == 0)
                assertTrue(mwf1.day(i));
            else
                assertFalse(mwf1.day(i));
        }

        for(int i = 0; i<5; i++) {
            if(i % 2 != 0)
                assertTrue(th1.day(i));
            else
                assertFalse(th1.day(i));
        }

        for(int i = 0; i<5; i++)
            assertTrue(mtwhf1.day(i));
    }

    @Test
    public void testCheckTimeConflict() {

        WeeklySchedule m = new WeeklySchedule("m");
        WeeklySchedule mwf = new WeeklySchedule("mwf");
        WeeklySchedule th = new WeeklySchedule("th");
        WeeklySchedule mtwhf = new WeeklySchedule("mtwhf");

        assertTrue(m.checkConflict(mwf));
        assertTrue(mtwhf.checkConflict(m));
        assertTrue(mwf.checkConflict(mtwhf));
        assertTrue(mtwhf.checkConflict(th));
        assertFalse(th.checkConflict(mwf));

    }
}