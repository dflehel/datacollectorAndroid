package hu.obuda.university.mibanddatacolector;
import java.util.UUID;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothGattCharacteristic;

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

    MiBandServices(BluetoothGatt gatt)
    {
        this.gatt = gatt;
        genericAccessService = new GenericAccessService();
        deviceInformationService = new DeviceInformationService();
        firmwareService = new FirmwareService();
        notificationService = new NotificationService();
        heartRateService = new HeartRateService();
        authenticationService = new AuthenticationService();
        miBandService = new MiBandService();
        batteryService = new BatteryService();
    }
    final class GenericAccessService {
        BluetoothGattService service = gatt.getService(UUID.fromString("00001800-0000-1000-8000-00805f9b34fb"));
        final BluetoothGattCharacteristic deviceNameChar = service.getCharacteristic(UUID.fromString(("00002a00-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic deviceTypeChar = service.getCharacteristic(UUID.fromString(("00002a01-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic connectionDataChar = service.getCharacteristic(UUID.fromString(("00002a04-0000-1000-8000-00805f9b34fb")));
        }
    final class DeviceInformationService {
        final BluetoothGattService service = gatt.getService(UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb"));
        final BluetoothGattCharacteristic serialNumberChar = service.getCharacteristic(UUID.fromString(("00002a25-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic hardwareVersionChar = service.getCharacteristic(UUID.fromString(("00002a27-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic softwareVersionChar = service.getCharacteristic(UUID.fromString(("00002a28-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic macAddressChar = service.getCharacteristic(UUID.fromString(("00002a23-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic pnpIdChar = service.getCharacteristic(UUID.fromString(("00002a50-0000-1000-8000-00805f9b34fb")));
        }
    final class FirmwareService {
        final BluetoothGattService service = gatt.getService(UUID.fromString("00001530-0000-3512-2118-0009af100700"));
        final BluetoothGattCharacteristic firmwareConfigChar = service.getCharacteristic(UUID.fromString(("00001531-0000-3512-2118-0009af100700")));
        final BluetoothGattCharacteristic firmwareDataChar = service.getCharacteristic(UUID.fromString(("00001532-0000-3512-2118-0009af100700")));
        }
    class NotificationService {
        final BluetoothGattService service = gatt.getService(UUID.fromString("00001811-0000-1000-8000-00805f9b34fb"));
        final BluetoothGattCharacteristic sendNotifyChar = service.getCharacteristic(UUID.fromString(("00002a46-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic notifyCPChar= service.getCharacteristic(UUID.fromString(("00002a44-0000-1000-8000-00805f9b34fb")));
        }
    final class HeartRateService {
        final BluetoothGattService service = gatt.getService(UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb"));
        final BluetoothGattCharacteristic measurementNotifyChar= service.getCharacteristic(UUID.fromString(("00002a37-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic sensorCPChar= service.getCharacteristic(UUID.fromString(("00002a39-0000-1000-8000-00805f9b34fb")));
        }
    final class AuthenticationService {
        final BluetoothGattService service = gatt.getService(UUID.fromString("0000fee1-0000-1000-8000-00805f9b34fb"));
        final BluetoothGattCharacteristic customAuthChar = service.getCharacteristic(UUID.fromString(("00000009-0000-3512-2118-0009af100700")));
        }
    final class MiBandService {
        final BluetoothGattService service = gatt.getService(UUID.fromString("0000fee0-0000-1000-8000-00805f9b34fb"));
        final BluetoothGattCharacteristic TimeChar= service.getCharacteristic(UUID.fromString(("00002a2b-0000-1000-8000-00805f9b34fb")));
        final BluetoothGattCharacteristic batteryInfoChar= service.getCharacteristic(UUID.fromString(("00000006-0000-3512-2118-0009af100700")));
        final BluetoothGattCharacteristic activityChar= service.getCharacteristic(UUID.fromString(("00000007-0000-3512-2118-0009af100700")));
        final BluetoothGattCharacteristic statusNotifyChar= service.getCharacteristic(UUID.fromString(("00000010-0000-3512-2118-0009af100700")));
        final BluetoothGattCharacteristic selectedActivityNotifyChar= service.getCharacteristic(UUID.fromString(("0000000f-0000-3512-2118-0009af100700")));
        }
    final class BatteryService {
        final BluetoothGattService service = gatt.getService(UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb"));
        final BluetoothGattCharacteristic BatteryLevelChar= service.getCharacteristic(UUID.fromString(("00002a19-0000-1000-8000-00805f9b34fb")));
        }
    }
