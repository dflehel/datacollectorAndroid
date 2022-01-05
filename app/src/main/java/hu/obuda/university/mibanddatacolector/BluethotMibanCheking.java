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
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BluethotMibanCheking extends BroadcastReceiver {

    public static String CHANNEL_ID = "internetszolgaltatas";
    public static NotificationChannel CHANNEL1 = new NotificationChannel("mibandkapcsolaodot", "amibanfelkapcsolodot", NotificationManager.IMPORTANCE_NONE);
    public static NotificationChannel CHANNEL2 = new NotificationChannel("mibandlekapcsolodot", "amibendlekapcsolodot", NotificationManager.IMPORTANCE_NONE);


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {

        } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            if (device.getName().contains("Mi Smart Band")) {
                showNotificationconected(context, intent, CHANNEL1);
//                hu.obuda.university.mibanddatacolector.Settings.mainActivity.imageView.setImageResource(R.mipmap.ic_on);
                Intent services = new Intent(context,ForgeGroundService.class);
                hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setText("Az alkalmazás nem működik: \n kérjük regisztráljon be, vagy jelentkezzen be");
              //  hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setTextColor(Color.WHITE);
              //  hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setBackgroundColor(Color.RED);
                hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewmibanddevice.setText("Mi band csatlakozva");
            //    hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setText("Az alkalmazás rendeltetésszerűen működik");
                hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setTextColor(Color.BLACK);
                hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setBackgroundColor(Color.GREEN);
              //  context.startForegroundService(services);
            }
        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

        } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {

        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            if (device.getName().contains("Mi Smart Band")) {
                showNotificationdisconected(context, intent, CHANNEL2);
                hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setText("Az alkalmazás nem működik: \n kérjük regisztráljon be, vagy jelentkezzen be");
                hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setTextColor(Color.WHITE);
                hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewstatus.setBackgroundColor(Color.RED);
                hu.obuda.university.mibanddatacolector.Settings.mainActivity.textViewmibanddevice.setText("Ninncsen Miband eszköz csatlakozva");

                //   context.stopService(services);
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
        mBuilder.setContentTitle("A miband")
                .setContentText(context.getText(R.string.sno))
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);
        NotificationManager  mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("11101", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId("11101");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(10 /* Request Code */, mBuilder.build());



    }

    private void showNotificationconected(Context context, Intent intent, NotificationChannel channel) {
        Intent resultIntent = new Intent(context , MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("A miband")
                .setContentText(context.getString(R.string.syes))
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);
        NotificationManager  mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("11111", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId("11111");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(11 /* Request Code */, mBuilder.build());


    }

}
