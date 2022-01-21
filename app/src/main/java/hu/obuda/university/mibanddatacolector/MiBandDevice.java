package hu.obuda.university.mibanddatacolector;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;

import androidx.annotation.RequiresApi;

import java.nio.ByteBuffer;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MiBandDevice extends BluetoothGattCallback {
    private final MiBandServices services;
    private MiBandNotificationInterface notify;
    public BluetoothGatt gat;
    UserInfo fakeUserInfo;
    public MiBandDevice(BluetoothGatt gatt, MiBandNotificationInterface notificationImplementation) throws Exception {
        services = new MiBandServices(gatt);
        this.gat = gatt;
        notify = notificationImplementation;
        if(!(services.genericAccessService.deviceNameChar.getStringValue(0).equalsIgnoreCase("Mi Smart Band 5"))||(services.authenticationService.customAuthChar.getValue()[0] != 3))
        {
            throw new Exception("The Mi Band isn't authenticated.");
        }
        EnableNotifications();

    }

    public MiBandDevice(Context context, BluetoothDevice device) throws Exception {
        //gat = device.connectGatt(context,false,this);
         services = new MiBandServices(device.connectGatt(context,false,this));
        //services = new MiBandServices(gat);
         byte[] name = services.genericAccessService.deviceNameChar.getValue();
         EnableNotifications();
    }

    public void setNotify(final MiBandNotificationInterface notify) {
        this.notify = notify;
    }


    public int GetSteps(){
        byte[] raw = services.miBandService.activityChar.getValue();
        byte[] bytes = Arrays.copyOfRange(raw,1,5);
        return  java.nio.ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public int GetCalories(){
        byte[] raw = services.miBandService.activityChar.getValue();
        byte[] bytes = Arrays.copyOfRange(raw,9,13);
        return  java.nio.ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
    }
    public int GetDistance(){
        byte[] raw = services.miBandService.activityChar.getValue();
        byte[] bytes = Arrays.copyOfRange(raw,5,9);
        return  java.nio.ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public int GetBatteryLevel(){
        byte[] result = services.batteryService.BatteryLevelChar.getValue();
        return result[0];
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime GetLastChargeDate(){
        byte[] result = services.miBandService.batteryInfoChar.getValue();
        byte[] years = new byte[2];
        years[0] = result[12];
        years[1] = result[11];
        return LocalDateTime.of(ByteBuffer.wrap(years).getInt(), result[13], result[14], result[15], result[16], result[17]);
    }

    public int GetLastChargePercentage(){
        byte[] result = services.miBandService.batteryInfoChar.getValue();
        return result[19];
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime GetPenultimateChargeDate(){
        byte[] result = services.miBandService.batteryInfoChar.getValue();
        byte[] years = new byte[2];
        years[0] = result[4];
        years[1] = result[3];
        return LocalDateTime.of(ByteBuffer.wrap(years).getInt(), result[5], result[6], result[7], result[8], result[9]);
    }

    public boolean IsCharging(){
        byte[] result = services.miBandService.batteryInfoChar.getValue();
        return (result[2] == 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime GetMiBandTime(){
        byte[] result = services.miBandService.TimeChar.getValue();
        byte[] years = new byte[2];
        years[0]=result[1];
        years[1]=result[0];
        return LocalDateTime.of(ByteBuffer.wrap(years).getInt(),result[3],result[4],result[5],result[6],result[7]);
    }

    private void EnableNotifications() throws Exception {
        boolean tmp;
        List<BluetoothGattDescriptor> descriptors;
        //HR notification
        services.gatt.setCharacteristicNotification(services.heartRateService.measurementNotifyChar, true);
        descriptors = services.heartRateService.measurementNotifyChar.getDescriptors();
        descriptors.get(0).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        tmp=services.gatt.writeDescriptor(descriptors.get(0));
        if(!tmp)
            throw new Exception("The HR notification wasn't enabled.");
        //Step data
        services.gatt.setCharacteristicNotification(services.miBandService.activityChar, true);
        SystemClock.sleep(500);
        descriptors = services.miBandService.activityChar.getDescriptors();
        SystemClock.sleep(500);
        descriptors.get(0).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        //SystemClock.sleep(500);
        tmp=services.gatt.writeDescriptor(descriptors.get(0));
        //SystemClock.sleep(500);
        if(!tmp)
            throw new Exception("The activity notification wasn't enabled.");
        //Notification data
        services.gatt.setCharacteristicNotification(services.miBandService.statusNotifyChar, true);
        SystemClock.sleep(500);
        descriptors = services.miBandService.activityChar.getDescriptors();
        SystemClock.sleep(500);
        descriptors.get(0).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        tmp=services.gatt.writeDescriptor(descriptors.get(0));
        if(!tmp)
            throw new Exception("The status notification wasn't enabled.");
        //Battery level
        services.gatt.setCharacteristicNotification(services.batteryService.BatteryLevelChar, true);
        SystemClock.sleep(500);
        descriptors = services.miBandService.activityChar.getDescriptors();
        SystemClock.sleep(500);
        descriptors.get(0).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        tmp = services.gatt.writeDescriptor(descriptors.get(0));
        if(!tmp)
            throw new Exception("The battery level changed notification wasn't enabled.");
        //Battery status
        services.gatt.setCharacteristicNotification(services.batteryService.BatteryLevelChar, true);
        SystemClock.sleep(500);
        descriptors = services.miBandService.activityChar.getDescriptors();
        SystemClock.sleep(500);
        descriptors.get(0).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        tmp = services.gatt.writeDescriptor(descriptors.get(0));
        if(!tmp)
            throw new Exception("The battery status changed notification wasn't enabled.");
        //Time changed
        services.gatt.setCharacteristicNotification(services.miBandService.TimeChar, true);
        SystemClock.sleep(500);
        descriptors = services.miBandService.TimeChar.getDescriptors();
        SystemClock.sleep(500);
        descriptors.get(0).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        tmp = services.gatt.writeDescriptor(descriptors.get(0));
        if(!tmp)
            throw new Exception("The time changed notification wasn't enabled.");
        //Select activity
        services.gatt.setCharacteristicNotification(services.miBandService.selectedActivityNotifyChar, true);
        SystemClock.sleep(500);
        descriptors = services.miBandService.selectedActivityNotifyChar.getDescriptors();
        SystemClock.sleep(500);
        descriptors.get(0).setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        tmp = services.gatt.writeDescriptor(descriptors.get(0));
        if(!tmp)
            throw new Exception("The activity select notification wasn't enabled.");
    }
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        if (characteristic.getUuid().toString().equalsIgnoreCase(services.heartRateService.measurementNotifyChar.getUuid().toString())){
            // HR data received
          //  System.out.println("mibanddevice heartratre");
            notify.OnHeartRateReceived(services.heartRateService.measurementNotifyChar.getValue()[1]);
        }
        else if (characteristic.getUuid().toString().equalsIgnoreCase(services.miBandService.activityChar.getUuid().toString())){
            // Step data received
          //  System.out.println("step data jott");
            notify.OnMovingActivity(GetSteps(), GetCalories(), GetDistance());
        }
        else if (characteristic.getUuid().toString().equalsIgnoreCase(services.miBandService.statusNotifyChar.getUuid().toString())){
            // Band notification received
            byte[] data = services.miBandService.statusNotifyChar.getValue();
            if(data.length == 1){
                switch (data[0]) {
                    case 1:{notify.OnSleepStartedOrBraceletWear(); break;}
                    case 2:{notify.OnWakeUp(); break;}
                    case 3:{notify.OnGoalReached(); break;}
                    case 6:{notify.OnBraceletRemove(); break;}
                }
            }
            else {
                // Activity selected on band
                if(data[0] == 14) {
                    // Indoor activity selected
                    if(data[2] == 0){
                        // Treadmill
                        if(data[3] == 2){
                        notify.OnTreadmillSelected();
                        }
                        //Pool swimming
                        if(data[3] == 5){
                            notify.OnPoolSwimmingSelected();
                        }
                        // Elliptical
                        if(data[3] == 6){
                            notify.OnEllipticalSelected();
                        }
                        // Rowing machine
                        if(data[3] == 7){
                            notify.OnRowingMachineSelected();
                        }
                        // Jump rope
                        if(data[3] == 8){
                            notify.OnJumpRopeSelected();
                        }
                        // Indoor cycling
                        if(data[3] == 9){
                            notify.OnIndoorCyclingSelected();
                        }
                        // Yoga
                        if(data[3] == 10){
                            notify.OnYogaSelected();
                        }
                        //Freestyle
                        if(data[3] == 11){
                            notify.OnFreestyleSelected();
                        }
                    }
                    // Outdoor activity selected
                    if(data[2] == 1){
                        // Outdoor running
                        if(data[3] == 1){
                            notify.OnOutdoorRunningSelected();
                        }
                        // Cycling
                        if(data[3] == 3){
                            notify.OnCyclingSelected();
                        }
                        // Walking
                        if(data[3] == 4){
                            notify.OnWalkingSelected();
                        }
                    }
                }
            }
        }
        else if (characteristic.getUuid().toString().equalsIgnoreCase(services.batteryService.BatteryLevelChar.getUuid().toString())){
            notify.OnBatteryLevelChanged(GetBatteryLevel());
        }
        else if (characteristic.getUuid().toString().equalsIgnoreCase(services.miBandService.TimeChar.getUuid().toString())){
            notify.OnTimeChanged();
        }
        else if (characteristic.getUuid().toString().equalsIgnoreCase(services.miBandService.batteryInfoChar.getUuid().toString())){
            byte[] data = services.miBandService.statusNotifyChar.getValue();
            if(data.length == 3)
                notify.OnBatteryLevelChanged(data[1]);
            else
                notify.OnBatteryStatusChanged();
        }
        else if(characteristic.getUuid().toString().equalsIgnoreCase(services.miBandService.selectedActivityNotifyChar.getUuid().toString())){
            byte[] data = services.miBandService.selectedActivityNotifyChar.getValue();
            if(data.length == 2){
                if(data[0] == 11){
                    if(data[1] == 2){
                        notify.OnSelectedActivityStarted();
                    }
                    else if(data[1] == 3){
                        notify.OnSelectedActivityPaused();
                    }
                    else if(data[1] == 5){
                        notify.OnSelectOrFinishedActivity();
                    }
                }
            }
        }

    }

    public MiBandNotificationInterface getNotify() {
        return notify;
    }

    public void startHeartRateScan() {
        services.heartRateService.sensorCPChar.setValue(new byte[] {21, 2, 1});
    }
}
