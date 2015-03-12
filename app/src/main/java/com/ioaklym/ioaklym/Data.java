package com.ioaklym.ioaklym;

import java.util.Calendar;
import java.util.UUID;

public class Data {
    private String id;
    private String user;
    private String angle;
    private String __v;
    private String timestamp;

    // Getters and Setters....
    public String getId(){
        id = UUID.randomUUID().toString();
        return id;
    }

    public String getUser(){
        user="Maria";
        return user;
    }

    public String getAngle(){
        angle="123";
        return angle;
    }

    public String get__v(){
        __v="0";
        return __v;
    }

    public String getTimestamp(){
        Calendar calendar = Calendar.getInstance();
        timestamp = new java.sql.Timestamp(calendar.getTime().getTime()).toString();
        return timestamp;
    }
}
