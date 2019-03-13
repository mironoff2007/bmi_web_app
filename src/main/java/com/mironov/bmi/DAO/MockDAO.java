package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;
import com.mironov.bmi.Model.User;

import java.util.ArrayList;
import java.util.HashMap;


public class MockDAO {

    private HashMap<String,User> usersMap;

    private ArrayList <ArrayList<BmiRecord>> bmiList;



    public MockDAO() {
        usersMap = new HashMap<>();
        bmiList = new ArrayList<>();
        bmiList.add(new ArrayList<>());
    }

    public void addUser(User user){
        usersMap.put(user.getName(),user);
        System.out.println("User added-"+user.toString());
        bmiList.add(new ArrayList<>());
    }

    public User getUserById(int userId) {
        return usersMap.get(userId);
    }

    public ArrayList<BmiRecord> getUserBmiList(String name) {
        User user=usersMap.get(name);
        if(user!=null) {
            return bmiList.get(user.getId());
        }
        else {
            return new ArrayList<>();
        }
    }

    public void addBmiRecord(String name, int height, int weight){
        if(usersMap.containsKey(name)){
            ArrayList<BmiRecord> userBmiList = getUserBmiList(name);
            userBmiList.add(new BmiRecord(height,weight));}
        else {
            addUser(new User(name,height));
            ArrayList<BmiRecord> userBmiList = getUserBmiList(name);
            userBmiList.add(new BmiRecord(height,weight));
        }
    }

    public BmiRecord getBmiRecord(String name, int recordId){
        return getUserBmiList(name).get(recordId);
    }


}
