package hu.obuda.university.mibanddatacolector;

import android.app.Service;
import android.icu.util.Calendar;
import android.os.Handler;
import android.os.PowerManager;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Repeterwork {

    private DataCollector dataCollector;

    public Repeterwork() {



        ScheduledExecutorService executor =Executors.newScheduledThreadPool(1);
        Executors.newScheduledThreadPool(5);
        this.dataCollector = new DataCollector();

        Runnable periodicTask = new Runnable(){
            @Override
            public void run() {
                try{
                    Repeterwork.this.dataCollector.Testdatagenerator();
                }catch(Exception e){

                }
            }
        };
        ScheduledFuture<?> periodicFuture = executor.scheduleAtFixedRate(periodicTask, 5, 5, TimeUnit.SECONDS);
    }
}


