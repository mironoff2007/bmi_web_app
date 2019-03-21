package com.mironov.bmi;

import com.google.inject.AbstractModule;
import com.mironov.bmi.DAO.MockDAO;
import com.mironov.bmi.DAO.MockDAOImpl;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceImpl;

public class MyModule  extends AbstractModule {
        @Override
        protected void configure() {
            bind(Service.class).to(ServiceImpl.class);
            bind(MockDAO.class).to(MockDAOImpl.class);
        }

}
