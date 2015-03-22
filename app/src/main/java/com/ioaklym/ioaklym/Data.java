package com.ioaklym.ioaklym;

import java.util.Calendar;
import java.util.UUID;

public class Data {
    protected String id;
    protected String user;
    protected String angle;
    protected String __v;
    protected String timestamp;

    private String[] parsedData = new String[4];

    int ANGLE_NAME=0;
    int ANGLE=1;
    int PRESSURE_NAME=2;
    int PRESSURE=3;

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
        angle=parsedData[ANGLE];
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

    public void receiveData(String bluetoothData){
        bluetoothData = bluetoothData.replaceAll("\\s","");
        String delims = "[,:]";
        parsedData = bluetoothData.split(delims);
    }
}
