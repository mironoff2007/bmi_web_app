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

    public ArrayList<BmiRecord> getUserBmiList(String name) {
        return mockDAO.getUserBmiList(name);
    }

    public void saveBmi(String name,int height, int weight){
        mockDAO.addBmiRecord(name , height, weight);
    }

    public void saveUser( String name,int height){
        mockDAO.addUser(new User( name,height));
    }


}
