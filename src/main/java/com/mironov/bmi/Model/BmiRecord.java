package com.mironov.bmi.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BmiRecord {

    private float bmi;

    //dimension is kg
    private int weight;

    //dimention is centimeter
    private int height;

    private String name;

    private String dateTime;



    private DateTimeFormatter formatter;

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }


    public BmiRecord(String name, int height, int weight) {

        this.weight = weight;

        formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss");
        this.dateTime=LocalDateTime.now().format(formatter);

        this.bmi= (float) (1000.0*weight/height/height);

        this.name=name;

        this.height=height;
    }


    @Override
    public String toString() {
        return "BmiRecord{" +
                "bmi=" + bmi +
                ", weight=" + weight +
                ", dateTime=" + dateTime +
                '}';
    }
}
