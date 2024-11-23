
package main;

import java.time.LocalDate;




public class MedHisinfo {
    
    private int nationalid;
    private LocalDate date;
    private String time;
    private String ward;
    private String tresult;
    private String observation;
    private String major;
    private String Attend;
    

    public MedHisinfo(int nationalid, LocalDate date, String time, String ward, String tresult, String observation, String major, String Attend) {
        this.nationalid = nationalid;
        this.date = date;
        this.time = time;
        this.ward = ward;
        this.tresult = tresult;
        this.observation = observation;
        this.major = major;
        this.Attend = Attend;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getTresult() {
        return tresult;
    }

    public void setTresult(String tresult) {
        this.tresult = tresult;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAttend() {
        return Attend;
    }

    public void setAttend(String Attend) {
        this.Attend = Attend;
    }

    

   
    
}
