package hu.obuda.university.mibanddatacolector;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.nio.channels.Channel;

public class ForgeGroundService extends Service {
    private BroadcastReceiver mNetworkReceiver;
    private BroadcastReceiver mibanchek;
    private BroadcastReceiver bluthotchek;


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
        mNetworkReceiver = new InternetCheking();
        registerNetworkBroadcastForNougat();
        mibanchek = new BluethotMibanCheking();
        registerMibandCheckReciver();
        bluthotchek = new BlueThoothIsOn();
        registerBluethootCheckReciver();
        return START_REDELIVER_INTENT;
    }
    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    private void registerMibandCheckReciver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            registerReceiver(mibanchek, filter);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            registerReceiver(mibanchek, filter);
        }
    }

    private void registerBluethootCheckReciver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            registerReceiver(bluthotchek, filter);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            registerReceiver(bluthotchek, filter);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
