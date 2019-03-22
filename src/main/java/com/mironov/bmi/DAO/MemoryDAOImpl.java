package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class MemoryDAOImpl implements DAO {


    private final List<BmiRecord> bmiList;

    public MemoryDAOImpl() {
        bmiList = new CopyOnWriteArrayList<>();
    }

    public List<BmiRecord> getBmiList() {
        return bmiList;
    }

    public void addBmiRecord(String name, int height, int weight,long dateTimeStep,float bmi){
            bmiList.add(new BmiRecord(name, height,weight, dateTimeStep, bmi));
    }



}
