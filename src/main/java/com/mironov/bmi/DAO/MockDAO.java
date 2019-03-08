package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;
import com.mironov.bmi.Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class MockDAO {

    private HashMap<Integer,User> usersMap;

    private ArrayList <ArrayList<BmiRecord>> bmiList;

    public MockDAO() {
        usersMap = new HashMap<>();
        bmiList = new ArrayList<>();
        bmiList.add(new ArrayList<>());
    }

    public void addUser(User user){
            usersMap.put(user.getId(),user);
            bmiList.add(new ArrayList<>());
    }

    public User getUserById(int userId) {
        return usersMap.get(userId);
    }

    public ArrayList<BmiRecord> getUserBmiList(int userId) {
        return bmiList.get(userId);
    }

    public void addBmiRecord(int userId, int height, int weight){
        ArrayList<BmiRecord> userBmiList = getUserBmiList(userId);
        userBmiList.add(new BmiRecord(height,weight));
    }

    public BmiRecord getBmiRecord(int userId, int recordId){
        return getUserBmiList(userId).get(recordId);
    }


}
