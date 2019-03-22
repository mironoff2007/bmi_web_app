package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;

import java.util.List;


public interface DAO {

     List<BmiRecord> getBmiList() ;

     void addBmiRecord(String name, int height, int weight,long dateTimeStep,float bmi);
}
