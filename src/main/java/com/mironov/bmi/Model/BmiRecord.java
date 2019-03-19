package com.mironov.bmi.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BmiRecord {

    private float bmi;

    private int weight;

    private String name;

    private int height;

    private String dateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    DateTimeFormatter formatter;

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /*
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    //public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    */
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
