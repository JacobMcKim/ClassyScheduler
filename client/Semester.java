/**
 * Created by zack on 24/11/15.
 */
public class Semester {
    private SemEnum sem;
    private int year, semID;

    public Semester() {
    }

    public Semester(SemEnum s, int y, int id) {
        this.sem = s;
        this.year = y;
    }

    public Semester(String s, int y, int id){

        sem = parseString(s);
        year = y;
        semID = id;
    }

    public int getYear() {
        return year;
    }

    public SemEnum getSem() {
        return sem;
    }

    public int getID() {
        return semID;
    }

    private SemEnum parseString(String s) {

        switch(s) {

            case "FALL":
                return SemEnum.FALL;
            case "WINTER":
                return SemEnum.WINTER;
            case "SUMMER_A":
                return  SemEnum.SUMMER_A;
            case "SUMMER_B":
                return SemEnum.SUMMER_B;
            default:
                return SemEnum.FALL;
        }
    }

    public boolean equals(Semester s) {
        return (s.year == this.year && s.sem == this.sem);
    }
}
