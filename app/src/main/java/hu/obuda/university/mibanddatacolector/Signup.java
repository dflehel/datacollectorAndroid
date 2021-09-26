package hu.obuda.university.mibanddatacolector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    BluetoothDevice bluetoothDevice;
    private BroadcastReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        EditText email = (EditText) findViewById(R.id.sing_up_activity_email_text);
        EditText pas1 = (EditText) findViewById(R.id.sign_up_activity_password1_text);
        EditText pas2 = (EditText) findViewById(R.id.editTextTextPassword2);
        Button signup = (Button) findViewById(R.id.sign_activity_sign_up_button);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pas1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Signup.this, "regsik",
                                    Toast.LENGTH_SHORT).show();
                            Map<String, Object> docData = new HashMap<>();
                            long time= System.currentTimeMillis();
                            MibandData data = new MibandData(time,user.getUid(),"hr","98");
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            docData.put(user.getUid(),user.getEmail());
                            db.collection("users").add(docData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Signup.this, "sikeres failed.",
                                                Toast.LENGTH_SHORT).show();
                                        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                                        int count = 0;
                                        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                                            if ("hu.obuda.university.mibanddatacolector.ForgeGroundService".equals(service.service.getClassName()) ==false ) {
                                                count = count+1;
                                                // return true;
                                                System.out.println(service.service.getClassName());
                                            }
                                            else{
                                                //System.out.println("sfhudsivdpufeqhpfeiphh");
                                                break;
                                            }
                                        }
                                        if (count == manager.getRunningServices(Integer.MAX_VALUE).size()){
                                            Intent services = new Intent(Signup.this,ForgeGroundService.class);
                                            startService(services);
                                            new Repeterwork(getApplicationContext());
                                            //System.out.println("dhisdgvvjldsl");
                                        }
                                        //return false;
                                        // Intent services = new Intent(MainActivity.this,ForgeGroundService.class);
                                        // startService(services);
                                        //Settings.mainActivity.imageView.setImageDrawable(R.drawable.on);
                                        Settings.mainActivity.textView.setText("mukodik");
                                        Signup.this.finish();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
            }

        });
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
}