package com.mironov.bmi.Model;

import java.time.LocalDateTime;

public class BmiRecord {

    private float bmi;

    private int weight;

    //private LocalDateTime dateTime;

    private String dateTime;


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
        this.dateTime=LocalDateTime.now().toString();
        this.bmi=weight/height/height/1000;
    }

    public BmiRecord() { }

    @Override
    public String toString() {
        return "BmiRecord{" +
                "bmi=" + bmi +
                ", weight=" + weight +
                ", dateTime=" + dateTime +
                '}';
    }
}
