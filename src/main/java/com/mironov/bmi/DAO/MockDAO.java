package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;

import java.util.concurrent.CopyOnWriteArrayList;


public class MockDAO {


    private CopyOnWriteArrayList<BmiRecord> bmiList;



    public MockDAO() {
        bmiList = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<BmiRecord> getBmiList() {
        return bmiList;
    }

    public void addBmiRecord(String name, int height, int weight){
            bmiList.add(new BmiRecord(name, height,weight));
    }



}
