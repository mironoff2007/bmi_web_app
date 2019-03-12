package com.mironov.bmi.Model;

import java.util.concurrent.atomic.AtomicInteger;

public class User {


    private static AtomicInteger counter=new AtomicInteger(0);

    private int id;

    private int height;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User( String name , int height ) {
        this.id = counter.intValue();
        counter.incrementAndGet();
        this.height = height;
        this.name = name;
    }

    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", height=" + height +
                ", name='" + name + '\'' +
                '}';
    }
}
