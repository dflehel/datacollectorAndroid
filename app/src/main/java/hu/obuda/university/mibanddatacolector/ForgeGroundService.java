package hu.obuda.university.mibanddatacolector;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.util.List;
import java.util.Set;

public class ForgeGroundService extends Service {
    private BroadcastReceiver mNetworkReceiver;
    private BroadcastReceiver mibanchek;
    private BroadcastReceiver bluthotchek;
    private static ForgeGroundService service;
    private hrgather hrgather;

    public static String CHANNEL_ID = "adtgyujtoszolgaltatas";
    public static NotificationChannel CHANNEL = new NotificationChannel("proba","A neve", NotificationManager.IMPORTANCE_NONE);
    private HrBroadcastReciver hrBroadcastReciver;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Settings.miband = null;
        Settings.mainActivity.imageView.setImageResource(R.mipmap.ic_off_round);
       try{ unregisterReceiver(mibanchek);
        unregisterReceiver(mNetworkReceiver);
        unregisterReceiver(bluthotchek);
        unregisterReceiver(hrBroadcastReciver);}
       catch (Exception e){

       }
    }


    @Override
    public int onStartCommand(Intent intent,int flags,int stratId){
        BluetoothDevice bluetoothDevice;
//        Settings.mainActivity.imageView
        final BluetoothManager bluetooothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        // Erre ezért volt szükség, mert ha nem volt bekapcsolva akkor egyszerűen tovább lépett rajta az app.
        if(!bluetooothManager.getAdapter().isEnabled()){
            bluetooothManager.getAdapter().enable();
        }
        // Ez az implementáció csak Mi Band-eket keres ezért ennek az ellenörzését lent kivettem.

        List<BluetoothDevice> gattServerConnectedDevices = bluetooothManager.getConnectedDevices(BluetoothProfile.GATT_SERVER);
        for (BluetoothDevice device : gattServerConnectedDevices) {
            if(device.getName().contains("Mi Smart Band"))
                bluetoothDevice = device;
            // bluetoothGatt = device.connectGatt(this,true,bluetoothGattCallback);
            // A MiBandServices felügyeli.
            //HashMap<UUID,String> MIBAND_DEBUG = new HashMap<>();

            //MIBAND_DEBUG.put(UUID.fromString("00002a39-0000-1000-8000-00805f9b34fb"), "Heart Rate Control Point");
            //MIBAND_DEBUG.put(UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb"), "Heart Rate Measurement");
            Settings.miband = null;
            try {
                Settings.miband = new MiBandDevice(this, device);
                MiBandDevice finalMiband = Settings.miband;
                System.out.println("mibandmegvan");
                Settings.mibandonline = true;
                Settings.mibandd = true;
                Settings.mainActivity.statuschange();
                Settings.miband.setNotify(new DataCollector(getApplicationContext()) {

                    @Override
                    public void onCommSuccess(Object data) {
                        Log.d("login", "connect success");

                        long hi = device.getUuids()[0].getUuid().getMostSignificantBits();
                        long lo = device.getUuids()[0].getUuid().getLeastSignificantBits();
                        byte[] bytes = ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();
                        BigInteger big = new BigInteger(bytes);
                        String numericUuid = big.toString().replace('-', '1'); // just in case
                        UserInfo userInfo = new UserInfo(big.intValue(), 1, 32, 180, 55, "胖梁", 0);
                        finalMiband.fakeUserInfo = userInfo;
                        finalMiband.startHeartRateScan();
                        Settings.mainActivity.textViewbat.setText(finalMiband.GetBatteryLevel()+"%");
                        System.out.println(finalMiband.GetBatteryLevel());

                    }
                });
                Settings.dataCollector = (DataCollector) Settings.miband.getNotify();
            } catch (Exception e) {
                e.printStackTrace();
            }}
        Settings.mainActivity.textViewmibanddevice.setText("Mi band csatlakozva");
        CHANNEL.setLightColor(Color.BLUE);
        CHANNEL.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(CHANNEL);
        Intent notiintent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notiintent, 0);

        Notification notification =
                new Notification.Builder(this, "proba")
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.appback))
                        .setSmallIcon(R.drawable.ic_android_black_24dp)
                        .setContentIntent(pendingIntent)
                        .setTicker("proba")
                        .build();

        startForeground(101,notification);
        mNetworkReceiver = new InternetCheking();
       if ( mNetworkReceiver.isOrderedBroadcast()==false) {
           registerNetworkBroadcastForNougat();
       }
        mibanchek = new BluethotMibanCheking();
       if (mibanchek.isOrderedBroadcast() ==false) {
           registerMibandCheckReciver();
       }
        bluthotchek = new BlueThoothIsOn();
       if (bluthotchek.isOrderedBroadcast()==false) {
           registerBluethootCheckReciver();
       }
        hrgather =  new hrgather();
//        Settings.mainActivity.imageView.setImageResource(R.mipmap.ic_on_round);
        new Repeterwork(getApplicationContext());
         IntentFilter filter = new IntentFilter("hu.obuda.university.mibanddatacolector.intent.action.HR");
         filter.addCategory(Intent.CATEGORY_DEFAULT);
        IntentFilter filter1 = new IntentFilter("hu.obuda.university.mibanddatacolector.intent.action.HR_AGG");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        IntentFilter filter2 = new IntentFilter("hu.obuda.university.mibanddatacolector.intent.action.ACT");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        IntentFilter filter3 = new IntentFilter("hu.obuda.university.mibanddatacolector.intent.action.BATTERY");
        filter.addCategory(Intent.CATEGORY_DEFAULT);
         hrBroadcastReciver = new HrBroadcastReciver();
         if (hrBroadcastReciver.isOrderedBroadcast() ==false) {
             registerReceiver(hrBroadcastReciver, filter);
             registerReceiver(hrBroadcastReciver, filter1);
             registerReceiver(hrBroadcastReciver, filter2);
             registerReceiver(hrBroadcastReciver, filter3);
         }
         Settings.mainActivity.statuschange();
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
