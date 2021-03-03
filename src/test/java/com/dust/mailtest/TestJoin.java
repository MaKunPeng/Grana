package com.dust.mailtest;

import java.util.concurrent.TimeUnit;

public class TestJoin {
    public static void main(String[] args) throws Exception {
        System.out.println("start");
        
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        thread.start();
        thread.join();
        
        System.out.println("end");
    }
}
