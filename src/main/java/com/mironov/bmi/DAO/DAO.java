package com.mironov.bmi.DAO;

import com.mironov.bmi.Model.BmiRecord;

import java.sql.SQLException;
import java.util.List;


public interface DAO {

     List<BmiRecord> getBmiList() throws SQLException;

     void addBmiRecord(float bmi, String name, int height, int weight,long dateTimeStep);
}
