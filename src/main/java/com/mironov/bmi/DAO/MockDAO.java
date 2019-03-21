package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;

import java.util.concurrent.CopyOnWriteArrayList;

public interface MockDAO {

    public CopyOnWriteArrayList<BmiRecord> getBmiList() ;

    public void addBmiRecord(String name, int height, int weight);
}
