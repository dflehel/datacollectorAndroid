package hu.obuda.university.mibanddatacolector;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class hrgather {

    private DataCollector dataCollector;

    public hrgather() {



        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Executors.newScheduledThreadPool(5);
        this.dataCollector = new DataCollector();

        Runnable periodicTask = new Runnable(){
            @Override
            public void run() {
                try{
                    Settings.dataCollector.getaverage();
                }catch(Exception e){

                }
            }
        };
        ScheduledFuture<?> periodicFuture = executor.scheduleAtFixedRate(periodicTask, 1, 1, TimeUnit.MINUTES);
    }
}
