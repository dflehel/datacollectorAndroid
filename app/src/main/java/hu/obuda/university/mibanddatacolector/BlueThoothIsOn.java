package hu.obuda.university.mibanddatacolector;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

public class BlueThoothIsOn extends BroadcastReceiver {
    public static String CHANNEL_ID = "internetszolgaltatas";
    public static NotificationChannel CHANNEL1 = new NotificationChannel("ablurthotnemmegy", "kivankapcsolvaabluthot", NotificationManager.IMPORTANCE_NONE);
    public static NotificationChannel CHANNEL2 = new NotificationChannel("abluthotmegy", "abluthotbevankapcsolva", NotificationManager.IMPORTANCE_NONE);





    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int state;
        BluetoothDevice bluetoothDevice;

        if (action == BluetoothAdapter.ACTION_STATE_CHANGED){
            state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
            if (state == BluetoothAdapter.STATE_OFF){
                showNotificationdisconected(context,intent,CHANNEL1);

            }
            if( state ==BluetoothAdapter.STATE_ON){
                showNotificationdisconected(context,intent,CHANNEL2);

            }
        }
    }


    private void showNotificationdisconected(Context context, Intent intent,NotificationChannel channel) {
        Intent resultIntent = new Intent(context , MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("A Nluethotthja ki van kapcsolva")
                .setContentText("Kerem hogy kapcsolja be a Bluethot az eszkozen")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);
        NotificationManager  mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("11131", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId("11131");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(20 /* Request Code */, mBuilder.build());




    }

    private void showNotificationconected(Context context, Intent intent,NotificationChannel channel) {
        Intent resultIntent = new Intent(context , MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("A Bluethoth be van kapcsolva a telefonjan")
                .setContentText("Kerem hogy ne kapcsolja ki a Bluethoothoot a telefonjarol")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);
        NotificationManager  mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("11121", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId("11121");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(21 /* Request Code */, mBuilder.build());



    }
}
