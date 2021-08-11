package hu.obuda.university.mibanddatacolector;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.nio.channels.Channel;

public class ForgeGroundService extends Service {

    public static String CHANNEL_ID = "adtgyujtoszolgaltatas";
    public static NotificationChannel CHANNEL = new NotificationChannel("proba","A neve", NotificationManager.IMPORTANCE_NONE);

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int stratId){
        CHANNEL.setLightColor(Color.BLUE);
        CHANNEL.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(CHANNEL);
        Intent notiintent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notiintent, 0);

        Notification notification =
                new Notification.Builder(this, "proba")
                        .setContentTitle("megy ugye")
                        .setContentText("fut az alkalmazas")
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setContentIntent(pendingIntent)
                        .setTicker("proba")
                        .build();

        startForeground(1,notification);
        return START_REDELIVER_INTENT;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
