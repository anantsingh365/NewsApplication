package com.anant.newApp.utils;

import org.springframework.stereotype.Component;

/**
 * Just clear the hashmap that stores the json data for news articles
 * , this being a separate class is pretty stupid but i need
 * to make my source code look big.
 */
@Component
public class RefreshBucket {

    static{
        System.out.println("Refresh Bucket Initializing");
        initRefresh();
    }

    /**
     * Actual method that refreshes the bucket.
     */
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
