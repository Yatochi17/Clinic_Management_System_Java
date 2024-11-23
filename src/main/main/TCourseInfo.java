
package main;

import java.time.LocalDate;



public class TCourseInfo {
    
    private int nationalid;
    private String Tname;
    private LocalDate Sdate;
    private LocalDate Edate;

    public TCourseInfo(int nationalid, String Tname, LocalDate Sdate, LocalDate Edate) {
        this.nationalid = nationalid;
        this.Tname = Tname;
        this.Sdate = Sdate;
        this.Edate = Edate;
    }

    

    public int getNationalid() {
        return nationalid;
    }

    public void setNationalid(int nationalid) {
        this.nationalid = nationalid;
    }

    

    public String getTname() {
        return Tname;
    }

    public void setTname(String Tname) {
        this.Tname = Tname;
    }

    public LocalDate getSdate() {
        return Sdate;
    }

    public void setSdate(LocalDate Sdate) {
        this.Sdate = Sdate;
    }

    public LocalDate getEdate() {
        return Edate;
    }

    public void setEdate(LocalDate Edate) {
        this.Edate = Edate;
    }

    
}
