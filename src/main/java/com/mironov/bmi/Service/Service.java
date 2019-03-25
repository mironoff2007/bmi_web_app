package com.mironov.bmi.Service;

import com.mironov.bmi.Model.BmiRecord;


import java.sql.SQLException;
import java.util.List;


public interface Service {
     List<BmiRecord> getBmiList();

     void saveBmi(String name, int height, int weight) throws IllegalArgumentException, SQLException;
}