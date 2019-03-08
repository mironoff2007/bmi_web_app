package com.mironov.bmi.Model;

public class User {

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

    public User(int id, int height, String name) {
        this.id = id;
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
