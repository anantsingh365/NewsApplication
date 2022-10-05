package com.anant.newApp.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class scopeTesting {

    public String jsonUrlString;

    @Override
    public String toString(){
        return jsonUrlString;
    }
}
