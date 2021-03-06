/*****************************************************************************
 * WeeklySchedule
 * <p>
 * An entity used to reperesent what days of the week a section meets
 * Created by Zack Drescher
 * Last edited on 11.6.15 ZD
 * *****************************************************************************
 * Todo:
 * Throw exception
 ******************************************************************************/

public class WeeklySchedule {

    boolean[] days;

    public WeeklySchedule(String w) {

        days = convertString(w);
    }

    /* Converts a string into a boolean array reperesenting each day of the school
    * week. The string should contain "MTWHF" for every day of the week or for
    * example "MWF" for monday wednesday friday. If any other characters besides
    * M T W H or F are in the string or if the string is longer than 5 this method
    * will throw an exception. */
    private boolean[] convertString(String s) {

        boolean[] result = new boolean[5];
        char[] chars = s.toLowerCase().toCharArray();

        if (chars.length <= 5) {

            for (int i = 0; i < chars.length; i++)
                if (chars[i] == 'm')
                    result[0] = true;
                else if (chars[i] == 't')
                    result[1] = true;
                else if (chars[i] == 'w')
                    result[2] = true;
                else if (chars[i] == 'r')
                    result[3] = true;
                else if (chars[i] == 'f')
                    result[4] = true;
            // TODO throw exception
        }
        // TODO throw exception

        return result;
    }

    // this method will return true if this weekly schedule contains any of the
    // same days of weekly schedule t
    public boolean checkConflict(WeeklySchedule t) {

        boolean[] temp = t.getDays();
        for (int i = 0; i < days.length; i++)
            if (temp[i] && days[i])
                return true;

        return false;
    }

    // returns true if the schedule meets on that day, where 0 is monday and 4 is friday and false if otu of that
    // range
    public boolean day(int d) {
        if (d < 0 || d > 4)
            return false;
        return days[d];
    }

    public boolean[] getDays() {
        return days;
    }

    public String toString() {

        String s = "";

        for (int i = 0; i < days.length; i++) {
            if(days[i]) {
                switch (i) {
                    case 0:
                        s += "M";
                        break;
                    case 1:
                        s += "T";
                        break;
                    case 2:
                        s += "W";
                        break;
                    case 3:
                        s += "R";
                        break;
                    case 4:
                        s += "F";
                }
            }
        }

        return s;
    }
}
