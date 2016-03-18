package com.example.batendi.cattletraq;

/**
 * Created by batendi on 3/18/16.
 */
public class Cow {
    String rfid;
    String color;
    String dob;
    String mother;

    public Cow(String rfid,String color,String dob,String mother){
        this.rfid = rfid;
        this.color = color;
        this.dob = dob;
        this.mother = mother;
    }

    public String getRfid() {
        return rfid;
    }

    public String getColor() {
        return color;
    }

    public String getDob() {
        return dob;
    }

    public String getMother() {
        return mother;
    }
}
