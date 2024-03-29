package hu.obuda.university.mibanddatacolector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dkharrat.nexusdata.core.ObjectContext;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {
    Kafka c;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    public ImageView imageView;
    public TextView textView;
    public TextView textViewbat;
    public  TextView textViewagghr;
    public  TextView textViewact;
    public  TextView textViewstatus;
    public  TextView textViewmibanddevice;
    private HrBroadcastReciver hrBroadcastReciver;
    DatabaseHelper databaseHelper;


    public class Kafka {
        private final String topic;
        private final HashMap<String, Object> props;


        public Kafka(String brokers, String username, String password) {
            this.topic = "yxgb6gct-coinnok";

            String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
            String jaasCfg = String.format(jaasTemplate, username, password);

            String serializer = StringSerializer.class.getName();
            String deserializer = StringDeserializer.class.getName();
            props =new HashMap<String, Object>();
            props.put("bootstrap.servers", brokers);
            props.put("group.id", "consumerr");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("auto.offset.reset", "earliest");
            props.put("session.timeout.ms", "30000");
            props.put("buffer.memory", 33554432);
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("security.protocol", "SASL_SSL");
            props.put("sasl.mechanism", "SCRAM-SHA-256");
            props.put("sasl.jaas.config", jaasCfg);
//            Producer<String, String> producer = new KafkaProducer<>(props);
        }

        public void consume() {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("%s [%d] offset=%d, key=%s, value=\"%s\"\n",
                            record.topic(), record.partition(),
                            record.offset(), record.key(), record.value());
                }
            }
        }

        public void produce() {
            Thread one = new Thread() {
                public void run() {
                    try {
                        Producer<String, String> producer = new KafkaProducer<String,String>(props);
                        int i = 0;
                        while(true) {
                            Date d = new Date();
                            producer.send(new ProducerRecord<>(topic, Integer.toString(i), d.toString()));
                            Thread.sleep(1000);
                            i++;
                        }
                    } catch (Exception v) {
                        System.out.println(v);
                    }
                }
            };
            one.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // this.databaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("rendszeruzenet")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "sikerultfeliratkozni";
                        if (!task.isSuccessful()) {
                            msg = "nem sikerult feliratkozni";
                        }
                        Log.d(" Baj van", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        // DBSession session = new AndroidDBSession(getApplicationContext());
        this.c = new Kafka("glider-01.srvs.cloudkafka.com:9094,glider-02.srvs.cloudkafka.com:9094,glider-03.srvs.cloudkafka.com:9094", "yxgb6gct", "1Oz1lEzdDFJpLN_OOUWhhrY4NC_CQakl");
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10*3600)
                .build();
        Settings.context = getApplicationContext();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    boolean updated = task.getResult();
                    Log.d(" megjottek", "Config params updated: " + updated);
                    Toast.makeText(MainActivity.this, "Fetch and activate succeeded",
                            Toast.LENGTH_SHORT).show();
                    //System.out.println(mFirebaseRemoteConfig.getDouble("munitesforinternetcheck"));
                   // System.out.println(mFirebaseRemoteConfig.getDouble("timeforwaittoreques"));
                    Settings.internetcheck = new Double(mFirebaseRemoteConfig.getDouble("munitesforinternetcheck")).intValue();
                    Settings.bluethotconnection = new Double(mFirebaseRemoteConfig.getDouble("timeforwaittoreques")).intValue();
                    Settings.savetimeperiod = new Double(mFirebaseRemoteConfig.getDouble("hoursofsave")).intValue();
                } else {
                    Toast.makeText(MainActivity.this, "Fetch failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button logingbutton = (Button) findViewById(R.id.main_activity_log_in);
        logingbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Log_ing.class);
                startActivityForResult(myIntent, 0);
            }

        });
        Button signup = (Button) findViewById(R.id.main_activity_sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Signup.class);
                startActivityForResult(myIntent, 0);
            }

        });
        Dataset.getInstance();
       // this.imageView = (ImageView) findViewById(R.id.imageView_on_off);
        this.textViewstatus = (TextView) findViewById(R.id.status_label);
        this.textViewmibanddevice = (TextView) findViewById(R.id.miband_device_label);
        this.textView = (TextView) findViewById(R.id.hr_textview);
        this.textViewact = (TextView) findViewById(R.id.activity_label);
        this.textViewagghr = (TextView) findViewById(R.id.agg_hr_label);
        this.textViewbat = (TextView) findViewById(R.id.battery_label);
        Settings.mainActivity = this;
       // IntentFilter filter = new IntentFilter("hu.obuda.university.mibanddatacolector.intent.action.HR");
       // filter.addCategory(Intent.CATEGORY_DEFAULT);
      // hrBroadcastReciver = new HrBroadcastReciver();
       // registerReceiver(hrBroadcastReciver, filter);
//        this.imageView.setImageResource(R.mipmap.ic_off_round);
        Button logoutbutton = (Button) findViewById(R.id.logout);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Settings.user = false;
               // MainActivity.this.textView.setText("jelenleg nincs bejelentkezve");
             //   MainActivity.this.imageView.setImageResource(R.mipmap.ic_off);
                MainActivity.this.textViewstatus.setText("Az alkalmazás nem működik: \n kérjük regisztráljon be, vagy jelentkezzen be");
                MainActivity.this.textViewstatus.setTextColor(Color.WHITE);
                MainActivity.this.textViewstatus.setBackgroundColor(Color.RED);
               // Settings.mainActivity.textView.setText("jelenleg nincs bejelentkezve");
            }
        });
        Settings.mainActivity = this;
        FirebaseAuth  mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Settings.user = true;
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.enable() == false){
                Settings.bluethott = false;
                this.textViewstatus.setText("Az alkalmazás nem működik: \n kérjük kapcsolja be a bluetooth-t");
                this.textViewstatus.setTextColor(Color.WHITE);
                this.textViewstatus.setBackgroundColor(Color.RED);

            }
            else {
                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                int count = 0;
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                    if ("hu.obuda.university.mibanddatacolector.ForgeGroundService".equals(service.service.getClassName()) == false) {
                        count = count + 1;
                        // return true;
                        System.out.println(service.service.getClassName());
                    } else {
                        //System.out.println("sfhudsivdpufeqhpfeiphh");
                        break;
                    }
                }
                if (count == manager.getRunningServices(Integer.MAX_VALUE).size()) {
                    Intent services = new Intent(MainActivity.this, ForgeGroundService.class);
                    startForegroundService(services);
                    //new Repeterwork(getApplicationContext());
                    //System.out.println("dhisdgvvjldsl");
                }
                //return false;
                // Intent services = new Intent(MainActivity.this,ForgeGroundService.class);
                // startService(services);
                //Settings.mainActivity.imageView.setImageDrawable(R.drawable.on);
               // Settings.mainActivity.textView.setText("mukodik");
                Settings.bluethott = true;
                this.textViewstatus.setText("Az alkalmazás rendeltetésszerűen működik");
                this.textViewstatus.setTextColor(Color.BLACK);
                this.textViewstatus.setBackgroundColor(Color.GREEN);
            }
        }
        else {
            Settings.user = false;
            this.textViewstatus.setText("Az alkalmazás nem működik: \n kérjük regisztráljon be, vagy jelentkezzen be");
            this.textViewstatus.setTextColor(Color.WHITE);
            this.textViewstatus.setBackgroundColor(Color.RED);
        }
        // c.consume();

     //   Integer toroldadatok = databaseHelper.deletelAll();
     //   System.out.println("trolt adatok= "+toroldadatok.toString());

    }

    public void statuschange(){
        if (Settings.user == false){
            this.textViewstatus.setText("Az alkalmazás nem működik: \n kérjük regisztráljon be, vagy jelentkezzen be");
            this.textViewstatus.setTextColor(Color.WHITE);
            this.textViewstatus.setBackgroundColor(Color.RED);
            this.textViewmibanddevice.setText("Mi band nincs csatlakozva");
            return;
        }
        if(Settings.bluethott==false){
            this.textViewstatus.setText("Az alkalmazás nem működik: \n kérjük kapcsolja be a bluetooth-t");
            this.textViewstatus.setTextColor(Color.WHITE);
            this.textViewstatus.setBackgroundColor(Color.RED);
            this.textViewmibanddevice.setText("Mi band nincs csatlakozva");
            return;
        }
        if (Settings.mibandd==false){
           this.textViewmibanddevice.setText("Mi band nincs csatlakozva");
            this.textViewstatus.setText(" Az alkalmazás nem működik: kérjük csatlakoztassa a Miband szenzort, vagy ellenőrizze a Mifit alkalmazás működését");
            this.textViewstatus.setTextColor(Color.WHITE);
            this.textViewstatus.setBackgroundColor(Color.RED);
            return;
        }
        this.textViewmibanddevice.setText("Mi band csatlakozva");
        this.textViewstatus.setText("Az alkalmazás rendeltetésszerűen működik");
        this.textViewstatus.setTextColor(Color.BLACK);
        this.textViewstatus.setBackgroundColor(Color.GREEN);
        return;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.statuschange();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseAuth  mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Settings.user = true;
//            this.imageView.setImageResource(R.mipmap.ic_on_round);
           // this.textView.setText(" Mukodik ");
        }
        else{
            Settings.user = false;
     //       this.imageView.setImageResource(R.mipmap.ic_off_round);
          //  this.textView.setText("nincs bejelentkezve a felhasznalo");
        }
        if (Settings.online) {
            this.textViewmibanddevice.setText("Mi band csatlakozva");
            Settings.mibandd = true;
        }
        else{
            this.textViewmibanddevice.setText("Mi band nincs csatlakozva");
            Settings.mibandd = false;
        }
        this.statuschange();
       // Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
      //  this.c.produce();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     //   unregisterReceiver(hrBroadcastReciver);
    }
}