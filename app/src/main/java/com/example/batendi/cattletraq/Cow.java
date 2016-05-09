package com.example.batendi.cattletraq;

/**
 * Created by batendi on 3/18/16.
 */
public class Cow
{
    public static String rfid ;
    public static String birthdate;
    private String color;

    public Cow()
    {}

    public Cow(String rfid, String birthdate, String color)
    {
        this.rfid = rfid;
        this.birthdate = birthdate;
        this.color = color;
    }

    public static void setRfid(String rfid) {
        Cow.rfid = rfid;
    }

    public static void setBirthdate(String birthdate) {
        Cow.birthdate = birthdate;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static String getRfid() {
        return rfid;
    }

    public static String getBirthdate() {
        return birthdate;
    }

    public String getColor() {
        return color;
    }
}
