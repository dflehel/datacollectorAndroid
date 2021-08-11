package hu.obuda.university.mibanddatacolector;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;

import java.util.ArrayList;
import java.util.List;

public class ScanCallbackImp extends ScanCallback {
    public static List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();

    @Override
    public void onScanResult (int callbackType, ScanResult result){
        if(result.getDevice().getName().contains("Mi Smart Band")){
            super.onScanResult(callbackType,result);
            devices.add(result.getDevice());
        }
    }
}
