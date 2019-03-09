package com.mironov.bmi.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BmiRecord {

    private float bmi;

    private int weight;

    //private LocalDateTime dateTime;

    private String dateTime;

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
    public BmiRecord(float height, int weight) {

        this.weight = weight;

        formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss");
        this.dateTime=LocalDateTime.now().format(formatter);

        this.bmi=weight/height/height*1000;
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
