package com.mironov.bmi.Service;

import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Service.class).to(ServiceImpl.class);
    }
}
