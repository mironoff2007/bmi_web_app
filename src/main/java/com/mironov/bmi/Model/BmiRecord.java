package com.mironov.bmi.Model;


public class BmiRecord {

    private float bmi;

    private String name;

    //dimension is kg
    private int weight;

    //dimension is centimeter
    private int height;


    //time that represents millisecond from the UNIX Epoch
    private long dateTimeStep;

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public BmiRecord(float bmi, String name, int weight, int height, long dateTimeStep) {
        this.bmi = bmi;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.dateTimeStep = dateTimeStep;
    }

    @Override
    public String toString() {
        return "BmiRecord{" +
                "bmi=" + bmi +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", dateTimeStep=" + dateTimeStep +
                '}';
    }
}
