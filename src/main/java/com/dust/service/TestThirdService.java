package com.dust.service;

import com.dust.anno.JSRMThridService;

@JSRMThridService(name = "getTestThird")
public class TestThirdService implements IService {

    @Override
    public String handle(String param) {
        System.out.println("================================getTestThird==========================");
        return null;
    }
}
