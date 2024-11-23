package main;

import java.time.LocalDate;



public class DiagInfo {
    private int nationalid;
    private String DiagName;
    private LocalDate date;
    private String DiagSick;

    public DiagInfo(int nationalid, String DiagName, LocalDate date, String DiagSick) {
        this.nationalid = nationalid;
        this.DiagName = DiagName;
        this.date = date;
        this.DiagSick = DiagSick;
    }

    public int getNationalid() {
        return nationalid;
    }

    public void setNationalid(int nationalid) {
        this.nationalid = nationalid;
    }

    public String getDiagName() {
        return DiagName;
    }

    public void setDiagName(String DiagName) {
        this.DiagName = DiagName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDiagSick() {
        return DiagSick;
    }

    public void setDiagSick(String DiagSick) {
        this.DiagSick = DiagSick;
    }

    
    
    
}
