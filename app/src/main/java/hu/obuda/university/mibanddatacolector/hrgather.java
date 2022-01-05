package hu.obuda.university.mibanddatacolector;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class hrgather {

    private DataCollector dataCollector;
    public static boolean working = false;

    public hrgather() {



        ScheduledExecutorService executor =  Executors.newScheduledThreadPool(1);
        //Executors.newScheduledThreadPool(0);
        this.dataCollector = new DataCollector();

        Runnable periodicTask = new Runnable(){
            @Override
            public void run() {
                try{
                   // System.out.println("herelindultam");
                    if (hrgather.working ==false) {
                        hrgather.working = true;
                        Settings.dataCollector.getaverage();
                     //   System.out.println("vegeztemhr");
                        hrgather.working = false;
                    }
                }catch(Exception e){

                }
            }
        };
        ScheduledFuture<?> periodicFuture = executor.scheduleAtFixedRate(periodicTask, 1, 1, TimeUnit.MINUTES);
    }
}
