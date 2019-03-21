package com.mironov.bmi.Service;

import com.mironov.bmi.DAO.MockDAO;
import com.mironov.bmi.Model.BmiRecord;
import com.mironov.bmi.WrongNumberException;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
            long dateTimeStep=LocalDateTime.now().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();

            float bmi= (float) (1000.0*weight/height/height);

            mockDAO.addBmiRecord(name, height, weight, dateTimeStep, bmi);
        }
    }


}
