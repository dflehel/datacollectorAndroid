package hu.obuda.university.mibanddatacolector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Log_ing extends AppCompatActivity {

    private FirebaseAuth mAuth;
    BluetoothDevice bluetoothDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DFL régi kódjából Neudiab-os kódjából másolva, az átalakítások kommentelve
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BODY_SENSORS,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.BLUETOOTH_PRIVILEGED,Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_COARSE_LOCATION},255);
        final String TAG = "nope";

        //  mBluetoothManager = ((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE));
        //  mBluetoothAdapter = mBluetoothManager.getAdapter();
        // mBluetoothMi = mBluetoothAdapter.getRemoteDevice("E5:3B:9C:41:37:0B");
        // Kibővítve egy BL scannerrel, hogy ne csak egyetlen Mi Band-re működjön.
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
                MiBandDevice miband = null;
                try {
                    miband = new MiBandDevice(this, device);
                    MiBandDevice finalMiband = miband;
                    miband.setNotify(new DataCollector() {

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

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }}

                setContentView(R.layout.log_in);
        EditText email = (EditText) findViewById(R.id.log_in_activity_email_text);
        EditText pas = (EditText) findViewById(R.id.log_ing_activity_password_text);
        Button loginbutton = (Button) findViewById(R.id.log_in_activity_log_in_button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email.getText().toString(), pas.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //  Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Log_ing.this, "sikeresbe",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }
        });
    }
}