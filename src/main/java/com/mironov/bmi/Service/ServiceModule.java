package com.mironov.bmi.Service;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        try {
            bind(Service.class).toConstructor(Service.class.getConstructor());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
