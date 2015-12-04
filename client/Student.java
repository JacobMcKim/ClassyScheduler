/**
 * Created by zack on 04/12/15.
 */
public class Student {

    private int stuID, credits;
    private String fName, lName;
    private Standing standing;

    public Student() {}

    public Student(int id, String f, String l, String s, int cr) {
        stuID = id;
        fName = f;
        lName = l;
        standing = parseStanding(s);
        credits =cr;
    }

    public int getStuID() {return stuID;}
    public String getfName() {return fName;}
    public String getlName() {return lName;}
    public Standing getStanding() {return standing;}

    private Standing parseStanding(String s) {

        switch (s) {
            case "FRESHMAN":
                return Standing.FRESHMAN;
            case "SOPHMORE":
                return Standing.SOPHOMORE;
            case "JUNIOR":
                return Standing.JUNIOR;
            case "SENIOR":
                return Standing.SENIOR;
            case "GRADUATE":
                return Standing.GRADUATE;
            default:
                return Standing.FRESHMAN;
        }
    }

    private enum Standing {

        FRESHMAN,
        SOPHOMORE,
        JUNIOR,
        SENIOR,
        GRADUATE
    }
}
