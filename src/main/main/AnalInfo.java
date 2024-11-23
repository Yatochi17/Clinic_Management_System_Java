package main;

import java.time.LocalDate;
import java.util.Date;


public class AnalInfo {
    
    private int nationalid;
    private LocalDate date;
    private String TypeTest;
    private String result;

    public AnalInfo(int nationalid, LocalDate date, String TypeTest, String result) {
        this.nationalid = nationalid;
        this.date = date;
        this.TypeTest = TypeTest;
        this.result = result;
    }

    

    public int getNationalid() {
        return nationalid;
    }

    public void setNationalid(int nationalid) {
        this.nationalid = nationalid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTypeTest() {
        return TypeTest;
    }

    public void setTypeTest(String TypeTest) {
        this.TypeTest = TypeTest;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    
    
    
}
