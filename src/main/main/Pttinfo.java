package main;


public class Pttinfo {
    
    private String name;
    private int nationalid;
    private int age;
    private String gender;
    private String address;
    private String contact;

    public Pttinfo(String name, int nationalid, int age, String gender, String address, String contact) {
        this.name = name;
        this.nationalid = nationalid;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
    }

    
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNationalid() {
        return nationalid;
    }

    public void setNationalid(int nationalid) {
        this.nationalid = nationalid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    
    
    
}
