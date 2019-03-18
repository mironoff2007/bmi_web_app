package com.mironov.bmi.Service;

import com.mironov.bmi.Model.BmiRecord;

import java.util.concurrent.CopyOnWriteArrayList;

public interface Service {
    public CopyOnWriteArrayList<BmiRecord> getBmiList();

    public void saveBmi(String name, int height, int weight);
}