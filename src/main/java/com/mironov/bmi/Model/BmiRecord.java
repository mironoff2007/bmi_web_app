package com.mironov.bmi.Model;

import java.time.LocalDateTime;

public class BmiRecord {

    private float bmi;

    private int weight;

    private LocalDateTime dateTime;

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BmiRecord(float bmi, int weight) {
        this.bmi = bmi;
        this.weight = weight;
        this.dateTime=LocalDateTime.now();
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
