package com.mironov.bmi.Service;

import com.mironov.bmi.DAO.DAO;
import com.mironov.bmi.Model.BmiRecord;
import com.mironov.bmi.WrongNumberException;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class ServiceImpl implements Service {

    @Inject
    DAO dao;

    public ServiceImpl() {}

    public List<BmiRecord> getBmiList() {
        return dao.getBmiList();
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

            dao.addBmiRecord(name, height, weight, dateTimeStep, bmi);
        }
    }


}
