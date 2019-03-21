package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;

import java.util.concurrent.CopyOnWriteArrayList;


public class MockDAOImpl implements MockDAO {


    private CopyOnWriteArrayList<BmiRecord> bmiList;

    public MockDAOImpl() {
        bmiList = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<BmiRecord> getBmiList() {
        return bmiList;
    }

    public void addBmiRecord(String name, int height, int weight,long dateTimeStep,float bmi){
            bmiList.add(new BmiRecord(name, height,weight, dateTimeStep, bmi));
    }



}
