package com.mironov.bmi.Service;

import com.mironov.bmi.DAO.MockDAO;
import com.mironov.bmi.Model.BmiRecord;
import com.mironov.bmi.WrongNumberException;

import javax.inject.Inject;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceImpl implements Service {

    @Inject
    MockDAO mockDAO;

    public ServiceImpl() {}

    public CopyOnWriteArrayList<BmiRecord> getBmiList() {
        return mockDAO.getBmiList();
    }

    public void saveBmi(String name,int height, int weight) throws WrongNumberException {
        if(weight<=0){
            throw new WrongNumberException("Wrong number");
        }
        else if(height<=0){
            throw new WrongNumberException("Wrong number");
        }
        else {
            mockDAO.addBmiRecord(name, height, weight);
        }
    }


}
