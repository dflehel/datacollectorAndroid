package hu.obuda.university.mibanddatacolector;

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
import java.util.Properties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Kafka c;

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
        setContentView(R.layout.activity_main);
       // DBSession session = new AndroidDBSession(getApplicationContext());
        this.c = new Kafka("glider-01.srvs.cloudkafka.com:9094,glider-02.srvs.cloudkafka.com:9094,glider-03.srvs.cloudkafka.com:9094", "yxgb6gct", "1Oz1lEzdDFJpLN_OOUWhhrY4NC_CQakl");
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
        // c.consume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
      //  this.c.produce();
    }
}