package com.mironov.bmi;

import com.google.inject.AbstractModule;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceImpl;

public class MyModule  extends AbstractModule {
        @Override
        protected void configure() {
            bind(Service.class).to(ServiceImpl.class);
        }

}
