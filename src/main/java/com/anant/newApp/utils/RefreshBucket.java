package com.anant.newApp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Just clear the hashmap that stores the json data for news articles
 */
@Component
public class RefreshBucket {

    private static final Logger logger = LoggerFactory.getLogger(RefreshBucket.class);

    static{
        logger.info("Init Response Bucket Refresh");
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
                logger.info("Clearing Response Bucket");
                ResponseLayer.ClearBucket();
                logger.info("Clearing Bucket Done");
        }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
