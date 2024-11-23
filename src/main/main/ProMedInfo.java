
package main;

import java.time.LocalDate;



public class ProMedInfo {
    
    private int nationalid;
    private LocalDate date;
    private String time;
    private String ProType;
    private String Medic;
    private String Amount;
    private String Frequency;

    public ProMedInfo(int nationalid, LocalDate date, String time, String ProType, String Medic, String Amount, String Frequency) {
        this.nationalid = nationalid;
        this.date = date;
        this.time = time;
        this.ProType = ProType;
        this.Medic = Medic;
        this.Amount = Amount;
        this.Frequency = Frequency;
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

    public String getProType() {
        return ProType;
    }

    public void setProType(String ProType) {
        this.ProType = ProType;
    }

    public String getMedic() {
        return Medic;
    }

    public void setMedic(String Medic) {
        this.Medic = Medic;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String Frequency) {
        this.Frequency = Frequency;
    }

    
}
