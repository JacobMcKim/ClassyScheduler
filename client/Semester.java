/**
 * Created by zack on 24/11/15.
 */
public class Semester {
    private SemEnum sem;
    private int year;

    public Semester(){}

    public Semester (SemEnum s, int y) {
        this.sem = s;
        this.year = y;
    }

    public int getYear() {return year;}
    public SemEnum getSem() {return sem;}

    public void setYear(int year) {
        this.year = year;
    }

    public void setSem(SemEnum sem) {
        this.sem = sem;
    }

    public boolean equals(Semester s) {
        return (s.year == this.year && s.sem == this.sem);
    }
}
