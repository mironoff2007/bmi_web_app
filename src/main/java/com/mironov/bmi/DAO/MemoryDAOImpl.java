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

    public void addBmiRecord(float bmi,String name, int height, int weight,long dateTimeStep){
            bmiList.add(new BmiRecord(bmi,name, height,weight, dateTimeStep));
    }



}
