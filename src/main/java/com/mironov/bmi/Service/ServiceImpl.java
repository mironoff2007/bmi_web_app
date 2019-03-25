package com.mironov.bmi.Service;

import com.mironov.bmi.DAO.DAO;
import com.mironov.bmi.Model.BmiRecord;

import javax.inject.Inject;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class ServiceImpl implements Service {

    @Inject
    DAO dao;

    public ServiceImpl() {}

    public List<BmiRecord> getBmiList() {
        try {
            return dao.getBmiList();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveBmi(String name,int height, int weight) throws IllegalArgumentException, SQLException {
        if(weight<=0){
            throw new IllegalArgumentException("Wrong number");
        }
        else if(height<=0){
            throw new IllegalArgumentException("Wrong number");
        }
        else {
            long dateTimeStep=LocalDateTime.now().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();

            float bmi= (float) (1000.0*weight/height/height);

            dao.addBmiRecord(bmi, name, weight, height, dateTimeStep);
        }
    }


}
