package hu.obuda.university.mibanddatacolector;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

public class internetcheckservice  extends JobService {

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.i("MyService", "myService created");
    }

    // This method is called when the service instance
    // is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i("MyService", "myService destroyed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags,
                              int startId) {


        return START_NOT_STICKY;
    }



    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public void scheduleJob(JobInfo build) {

    }
}
