package hu.obuda.university.mibanddatacolector;
import java.util.List;
import java.util.UUID;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.SystemClock;

final class MiBandServices {
    BluetoothGatt gatt;
    GenericAccessService genericAccessService;
    DeviceInformationService deviceInformationService;
    FirmwareService firmwareService;
    NotificationService notificationService;
    HeartRateService heartRateService;
    AuthenticationService authenticationService;
    MiBandService miBandService;
    BatteryService batteryService;

    MiBandServices(BluetoothGatt gatt) throws InterruptedException {
        this.gatt = gatt;
        SystemClock.sleep(100);
        this.gatt.discoverServices();
        SystemClock.sleep(100);
        genericAccessService = new GenericAccessService();
        SystemClock.sleep(100);
        deviceInformationService = new DeviceInformationService();
        firmwareService = new FirmwareService();
        notificationService = new NotificationService();
        heartRateService = new HeartRateService();
        authenticationService = new AuthenticationService();
        miBandService = new MiBandService();
        batteryService = new BatteryService();
    }
    final class GenericAccessService {
        final BluetoothGattService service;
        final BluetoothGattCharacteristic deviceNameChar;
        final BluetoothGattCharacteristic deviceTypeChar;
        final BluetoothGattCharacteristic connectionDataChar;
        GenericAccessService(){
            service = gatt.getService(UUID.fromString("00001800-0000-1000-8000-00805f9b34fb"));
            deviceNameChar = service.getCharacteristic(UUID.fromString(("00002a00-0000-1000-8000-00805f9b34fb")));
            deviceTypeChar = service.getCharacteristic(UUID.fromString(("00002a01-0000-1000-8000-00805f9b34fb")));
            connectionDataChar = service.getCharacteristic(UUID.fromString(("00002a04-0000-1000-8000-00805f9b34fb")));
        }
    }
    final class DeviceInformationService {
        final BluetoothGattService service;
        final BluetoothGattCharacteristic serialNumberChar;
        final BluetoothGattCharacteristic hardwareVersionChar;
        final BluetoothGattCharacteristic softwareVersionChar;
        final BluetoothGattCharacteristic macAddressChar;
        final BluetoothGattCharacteristic pnpIdChar;
        DeviceInformationService(){
            service = gatt.getService(UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb"));
            serialNumberChar = service.getCharacteristic(UUID.fromString(("00002a25-0000-1000-8000-00805f9b34fb")));
            hardwareVersionChar = service.getCharacteristic(UUID.fromString(("00002a27-0000-1000-8000-00805f9b34fb")));
            softwareVersionChar = service.getCharacteristic(UUID.fromString(("00002a28-0000-1000-8000-00805f9b34fb")));
            macAddressChar = service.getCharacteristic(UUID.fromString(("00002a23-0000-1000-8000-00805f9b34fb")));
            pnpIdChar = service.getCharacteristic(UUID.fromString(("00002a50-0000-1000-8000-00805f9b34fb")));
        }
    }
    final class FirmwareService {
        final BluetoothGattService service;
        final BluetoothGattCharacteristic firmwareConfigChar;
        final BluetoothGattCharacteristic firmwareDataChar;
        FirmwareService() {
            service = gatt.getService(UUID.fromString("00001530-0000-3512-2118-0009af100700"));
            firmwareConfigChar = service.getCharacteristic(UUID.fromString(("00001531-0000-3512-2118-0009af100700")));
            firmwareDataChar = service.getCharacteristic(UUID.fromString(("00001532-0000-3512-2118-0009af100700")));
        }
    }
    class NotificationService {
        final BluetoothGattService service;
        final BluetoothGattCharacteristic sendNotifyChar;
        final BluetoothGattCharacteristic notifyCPChar;
        NotificationService(){
            service = gatt.getService(UUID.fromString("00001811-0000-1000-8000-00805f9b34fb"));
            sendNotifyChar = service.getCharacteristic(UUID.fromString(("00002a46-0000-1000-8000-00805f9b34fb")));
            notifyCPChar= service.getCharacteristic(UUID.fromString(("00002a44-0000-1000-8000-00805f9b34fb")));
        }
    }
    final class HeartRateService {
        final BluetoothGattService service;
        final BluetoothGattCharacteristic measurementNotifyChar;
        final BluetoothGattCharacteristic sensorCPChar;
        HeartRateService(){
            service = gatt.getService(UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb"));
            measurementNotifyChar = service.getCharacteristic(UUID.fromString(("00002a37-0000-1000-8000-00805f9b34fb")));
            sensorCPChar = service.getCharacteristic(UUID.fromString(("00002a39-0000-1000-8000-00805f9b34fb")));
        }
    }
    final class AuthenticationService {
        final BluetoothGattService service;
        final BluetoothGattCharacteristic customAuthChar;
        AuthenticationService(){
            service = gatt.getService(UUID.fromString("0000fee1-0000-1000-8000-00805f9b34fb"));
            customAuthChar = service.getCharacteristic(UUID.fromString(("00000009-0000-3512-2118-0009af100700")));
        }
    }
    final class MiBandService {
        final BluetoothGattService service;
        final BluetoothGattCharacteristic TimeChar;
        final BluetoothGattCharacteristic batteryInfoChar;
        final BluetoothGattCharacteristic activityChar;
        final BluetoothGattCharacteristic statusNotifyChar;
        final BluetoothGattCharacteristic selectedActivityNotifyChar;
        MiBandService(){
            service = gatt.getService(UUID.fromString("0000fee0-0000-1000-8000-00805f9b34fb"));
            TimeChar= service.getCharacteristic(UUID.fromString(("00002a2b-0000-1000-8000-00805f9b34fb")));
            batteryInfoChar= service.getCharacteristic(UUID.fromString(("00000006-0000-3512-2118-0009af100700")));
            activityChar= service.getCharacteristic(UUID.fromString(("00000007-0000-3512-2118-0009af100700")));
            statusNotifyChar= service.getCharacteristic(UUID.fromString(("00000010-0000-3512-2118-0009af100700")));
            selectedActivityNotifyChar= service.getCharacteristic(UUID.fromString(("0000000f-0000-3512-2118-0009af100700")));
        }
    }
    final class BatteryService {
        final BluetoothGattService service;
        final BluetoothGattCharacteristic BatteryLevelChar;
        BatteryService(){
            service = gatt.getService(UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb"));
            BatteryLevelChar= service.getCharacteristic(UUID.fromString(("00002a19-0000-1000-8000-00805f9b34fb")));
        }
    }
    }
