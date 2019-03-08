package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;
import com.mironov.bmi.Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class mockDAO {

    private HashMap<Integer,User> usersMap = new HashMap();

    private ArrayList <ArrayList<BmiRecord>> bmiList = new ArrayList<>();

    public void addUser(User user){
        if(user!=null) {
            usersMap.put(user.getId(),user);
        }
    }

    public User getUserById(int userId)
    {
        return usersMap.get(userId);
    }

    public ArrayList<BmiRecord> getUsersBmiList(int userId)
    {
        return bmiList.get(userId);
    }

    public void addBmiRecord(int userId, int height, int weight){
        ArrayList<BmiRecord> userBmiList = getUsersBmiList(userId);
        userBmiList.add(new BmiRecord(height,weight));
    }

    public BmiRecord addBmiRecord(int userId, int recordId){
        return getUsersBmiList(userId).get(recordId);
    }

}
