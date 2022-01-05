package hu.obuda.university.mibanddatacolector;

import android.app.Service;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Repeterwork {

    private DataCollector dataCollector;
    private Context context;
    private DatabaseSaver databaseSaver;
    public static boolean work = false;

    public Repeterwork(Context context) {
        this.context =context;


        ScheduledExecutorService executor =  Executors.newScheduledThreadPool(1);
       // Executors.newScheduledThreadPool(0);
        this.dataCollector = new DataCollector();
        this.databaseSaver = new DatabaseSaver(context);
        Runnable periodicTask = new Runnable(){
            @Override
            public void run() {
                try{
                  //  System.out.println("adatfeltolto");
                    if (Repeterwork.work ==false){
                    Repeterwork.work = true;
                    //Repeterwork.this.dataCollector.Testdatagenerator();
                //    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                ///    File file = new File(path, "my-file-name.txt");
                 //   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                 //   Date date = new Date();
                   // System.out.println(formatter.format(date));
                  //  System.out.println(path);
                  //  System.out.println("megyeket");
                    databaseSaver.dataSave();
                  //  System.out.println("elvegeztem");
                    Repeterwork.work = false;}
                    else{

                    }
                }catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        };
        ScheduledFuture<?> periodicFuture = executor.scheduleAtFixedRate(periodicTask, Settings.savetimeperiod, Settings.savetimeperiod, TimeUnit.HOURS);
    }
}


