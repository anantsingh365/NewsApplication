package com.anant.newApp.utils;

import org.springframework.stereotype.Component;

@Component
public class RefreshBucket {

    static{
        System.out.println("Refresh Bucket Initializing");
        initRefresh();
    }
    public static void initRefresh(){
        Runnable runnable = ()->{
            while(true){
                try {
                   Thread.sleep(1000 * 60 * 60 * 5);
                  // Thread.sleep(1000 * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // System.out.println("Refreshing every 30 seconds");
                ResponseLayer.ClearBucket();
        }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
