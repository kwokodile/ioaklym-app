package com.ioaklym.ioaklym;

import java.util.Calendar;

public class Data {
    protected String id;
    protected String user;
    protected String angle;
    protected String stride;
    protected String swing;
    protected String stance;
    protected String timestamp;

    private String[] parsedData = new String[7];

    int ANGLE=1;
    int STRIDE_TIME = 3;
    int SWING_TIME = 5;
    int STANCE_TIME = 7;

    // Getters and Setters....
    public String getUser(){
        user="Maria";
        return user;
    }

    public String getAngle(){
        angle=parsedData[ANGLE];
        return angle;
    }

    public String getStrideTime(){
        stride = parsedData[STRIDE_TIME];
        return stride;
    }

    public String getSwingTime(){
        swing = parsedData[SWING_TIME];
        return swing;
    }

    public String getStanceTime(){
        stance = parsedData[STANCE_TIME];
        return stance;
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
