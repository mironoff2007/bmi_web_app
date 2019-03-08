package com.mironov.bmi.Service;

import com.mironov.bmi.DAO.MockDAO;
import com.mironov.bmi.Model.BmiRecord;
import com.mironov.bmi.Model.User;

import javax.inject.Inject;
import java.util.ArrayList;

public class Service {

    @Inject
    MockDAO mockDAO;

    public Service() {
    }

    public ArrayList<BmiRecord> getUserBmiList(int userId) {
        return mockDAO.getUserBmiList(userId);
    }

    public void saveBmi(int userId, int height, int weight){
        mockDAO.addBmiRecord(userId , height, weight);
    }

    public void saveUser(int userId, int height, String name){
        mockDAO.addUser(new User(userId, height,name));
    }


}
