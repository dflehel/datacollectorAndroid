package hu.obuda.university.mibanddatacolector;

import android.app.Service;
import android.content.Context;
import android.icu.util.Calendar;
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

    public Repeterwork(Context context) {
        this.context =context;


        ScheduledExecutorService executor =Executors.newScheduledThreadPool(1);
        Executors.newScheduledThreadPool(5);
        this.dataCollector = new DataCollector();

        Runnable periodicTask = new Runnable(){
            @Override
            public void run() {
                try{
                    Repeterwork.this.dataCollector.Testdatagenerator();
                    File path = Repeterwork.this.context.getFilesDir();
                    File file = new File(path, "my-file-name.txt");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    System.out.println(formatter.format(date));
                    System.out.println(path);
                    FileOutputStream stream = new FileOutputStream(file,true);
                    try {
                        stream.write(formatter.format(date).getBytes());
                    } finally {
                        stream.close();
                    }
                }catch(Exception e){

                }
            }
        };
        ScheduledFuture<?> periodicFuture = executor.scheduleAtFixedRate(periodicTask, 10, 10, TimeUnit.MINUTES);
    }
}


