package com.mironov.bmi.Service;

import com.mironov.bmi.DAO.MockDAO;
import com.mironov.bmi.Model.BmiRecord;

import javax.inject.Inject;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceImpl implements Service {

    @Inject
    MockDAO mockDAO;

    public ServiceImpl() {}

    public CopyOnWriteArrayList<BmiRecord> getBmiList() {
        return mockDAO.getBmiList();
    }

    public void saveBmi(String name,int height, int weight){
        mockDAO.addBmiRecord(name, height, weight);
    }


}
