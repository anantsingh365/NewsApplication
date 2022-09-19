package com.anant.newApp.utils;

import org.springframework.stereotype.Component;

import static com.anant.newApp.utils.CheckSavedResponseLayer.savedBucket;

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
                   // Thread.sleep(1000 * 60 * 60 * 5);
                    Thread.sleep(1000 * 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Refreshing every 20 seconds!!");
                refresh();
        }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static void refresh(){
        if(!savedBucket.isEmpty()){
            savedBucket.clear();
        }
    }
}
