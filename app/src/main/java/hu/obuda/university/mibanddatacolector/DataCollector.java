package hu.obuda.university.mibanddatacolector;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.SetOptions;
import com.google.protobuf.StringValue;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DataCollector implements MiBandNotificationInterface, DatabeseSave {

    public ArrayList<MibandData> mibandData = new ArrayList<MibandData>();
    public MibandData mibandDataact = new MibandData();
    public MibandData mibandDatahr = new MibandData();
    public static final String CUSTOM_INTENT = "hu.obuda.university.mibanddatacolector.intent.action.HR";
    public static final String CUSTOM_INTENT1 = "hu.obuda.university.mibanddatacolector.intent.action.HR_AGG";
    public static final String CUSTOM_INTENT2 = "hu.obuda.university.mibanddatacolector.intent.action.ACT";
    public static final String CUSTOM_INTENT3 = "hu.obuda.university.mibanddatacolector.intent.action.BATTERY";
    public Context context;
    public DatabaseHelper databaseHelper;
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");


    public DataCollector() {
        Dataset.getInstance();
        databaseHelper = new DatabaseHelper(context);
    }



    public void getaverage(){
        int count=0;
        int sum=0;
        if (mibandData !=null && mibandData.size()>0) {
            for (MibandData mibanddata: mibandData) {
                count = count+1;
                sum = Integer.parseInt(mibanddata.getValue()) + sum;
            }

        }
        if (count==0){
            this.databaseHelper.insertdata(mibandDatahr.getUserid(),mibandDatahr.getSensor(),  String.valueOf(mibandDatahr.getTimestamp()),mibandDatahr.getValue());
            this.databaseHelper.insertdata(mibandDataact.getUserid(),mibandDataact.getSensor(),  String.valueOf(mibandDataact.getTimestamp()),mibandDataact.getValue());
            Intent i = new Intent();
            i.setAction(CUSTOM_INTENT1);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            i.putExtra("value",sdf1.format(timestamp)+"\n"+mibandDatahr.getValue());
            this.context.sendBroadcast(i);

        }
        else{
        MibandData data = new MibandData();
        data.setValue(Integer.toString((int)sum/count));
        data.setSensor("Heart Rate");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        //this.mibandData.add(data);
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());
        this.databaseHelper.insertdata(mibandDataact.getUserid(),mibandDataact.getSensor(),  String.valueOf(mibandDataact.getTimestamp()),mibandDataact.getValue());
        Intent i = new Intent();
        i.setAction(CUSTOM_INTENT1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        i.putExtra("value",sdf1.format(timestamp)+"\n"+data.getValue());
        this.context.sendBroadcast(i);
        mibandDatahr.setUserid(data.getUserid());
        mibandDatahr.setValue(data.getValue());
        mibandDatahr.setTimestamp(data.getTimestamp());
        mibandDatahr.setSensor(data.getSensor());
         this.mibandData.clear();
        }
    }

    public DataCollector(Context context) {
            Dataset.getInstance();
            this.context = context;
            databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public void Testdatagenerator() {
        MibandData data = new MibandData();
        data.setSensor("generalt");
        data.setTimestamp(System.currentTimeMillis());
        data.setValue("89");
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(mibandDataact.getUserid(),mibandDataact.getSensor(),  String.valueOf(mibandDataact.getTimestamp()),mibandDataact.getValue());


    }

    @Override
    public void OnHeartRateReceived(int value) {
       // System.out.println("datacollector haretrate"+value);
        MibandData data = new MibandData();
        data.setValue(Integer.toString(value));
        data.setSensor("Heart Rate");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.mibandData.add(data);
        Intent i = new Intent();
        i.setAction(CUSTOM_INTENT);
        i.putExtra("value",data.getValue());
        this.context.sendBroadcast(i);
       // if (Dataset.datasave==0){
         //   this.saveData(data);
       // }
       // else{
       //     Dataset.addData(data);
       // }
    }

    @Override
    public void OnMovingActivity(int stepValue, int calories, int distance) {

       // System.out.println("datacollector activity"+stepValue+","+calories+","+distance);
        MibandData data = new MibandData();
        data.setValue("Step value:" + Integer.toString(stepValue) + "; Calories:" + Integer.toString(calories) + "; Distance:" + Integer.toString(distance) + ";");
        data.setSensor("Activity");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.mibandDataact.setValue("Step value:\n" + Integer.toString(stepValue) + "\n Calories:\n" + Integer.toString(calories) + "\n Distance:\n" + Integer.toString(distance));
        this.mibandDataact.setSensor("Activity");
        this.mibandDataact.setTimestamp(System.currentTimeMillis());
        this.mibandDataact.setUserid(DatabeseSave.mauth.getUid());
        Intent i = new Intent();
        i.setAction(CUSTOM_INTENT2);
        i.putExtra("value","Step value:\n" + Integer.toString(stepValue) + "\n Calories:\n" + Integer.toString(calories) + "\n Distance:\n" + Integer.toString(distance));
        this.context.sendBroadcast(i);
    }

    @Override
    public void OnSleepStartedOrBraceletWear() {


        MibandData data = new MibandData();
        data.setValue("Sleep started.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnWakeUp() {
        MibandData data = new MibandData();
        data.setValue("Waked up.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());
    }

    @Override
    public void OnGoalReached() {
        MibandData data = new MibandData();
        data.setValue("The daily goal was reached.");
        data.setSensor("Activity");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());
    }

    @Override
    public void OnBraceletRemove() {

        MibandData data = new MibandData();
        data.setValue("The user was removed the bracelet.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());
    }

    @Override
    public void OnBatteryLevelChanged(int newValue) {


        MibandData data = new MibandData();
        data.setValue("The batter level is: " + Integer.toString(newValue));
        data.setSensor("Battery");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());
        Intent i = new Intent();
        i.setAction(CUSTOM_INTENT3);
        i.putExtra("value",data.getValue());
        this.context.sendBroadcast(i);
    }

    @Override
    public void OnBatteryStatusChanged() {

        MibandData data = new MibandData();
        data.setValue("The charge status has changed.");
        data.setSensor("Battery");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnTimeChanged() {

        MibandData data = new MibandData();
        data.setValue("New time set.");
        data.setSensor("Mi Band");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnOutdoorRunningSelected() {

        MibandData data = new MibandData();
        data.setValue("Outdoor Running selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnTreadmillSelected() {

        MibandData data = new MibandData();
        data.setValue("Treadmill selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnCyclingSelected() {

        MibandData data = new MibandData();
        data.setValue("Cycling selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnIndoorCyclingSelected() {

        MibandData data = new MibandData();
        data.setValue("Indoor Cycling selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnWalkingSelected() {

        MibandData data = new MibandData();
        data.setValue("Walking selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnFreestyleSelected() {

        MibandData data = new MibandData();
        data.setValue("Freestyle selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnYogaSelected() {

        MibandData data = new MibandData();
        data.setValue("Yoga selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnJumpRopeSelected() {

        MibandData data = new MibandData();
        data.setValue("Jump Rope selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnRowingMachineSelected() {

        MibandData data = new MibandData();
        data.setValue("Rowing Machine selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnEllipticalSelected() {

        MibandData data = new MibandData();
        data.setValue("Elliptical selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnPoolSwimmingSelected() {

        MibandData data = new MibandData();
        data.setValue("Swimming selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnSelectedActivityStarted() {

        MibandData data = new MibandData();
        data.setValue("The selected activity has started.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());

    }

    @Override
    public void OnSelectedActivityPaused() {

        MibandData data = new MibandData();
        data.setValue("The selected activity has paused.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());
    }

    @Override
    public void OnSelectOrFinishedActivity() {


        MibandData data = new MibandData();
        data.setValue("The selected activity has finished.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        this.databaseHelper.insertdata(data.getUserid(),data.getSensor(),  String.valueOf(data.getTimestamp()),data.getValue());
    }

    @Override
    public void saveData(MibandData data) {
        Map savedata = new HashMap<String,Object>();
        long time = System.currentTimeMillis();
        savedata.put(Long.toString(time),data);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> adatokk = new HashMap<>();
        adatokk.put("baj","baj");
        databaseReference.child("cica").setValue(adatokk);
     //   db.collection(mauth.getUid().toString()).document(data.getSensor()).set(savedata, SetOptions.mergeFields(Long.toString(time)));
     //   db.collection(mauth.getUid().toString()).document(data.getSensor()).
    }

    @Override
    public void onCommSuccess(Object data) {
        ;
    }
}
