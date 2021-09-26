package hu.obuda.university.mibanddatacolector;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DataCollector implements MiBandNotificationInterface, DatabeseSave {

    public ArrayList<MibandData> mibandData = new ArrayList<MibandData>();
    public static final String CUSTOM_INTENT = "hu.obuda.university.mibanddatacolector.intent.action.HR";
    public Context context;

    public DataCollector() {
        Dataset.getInstance();
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
        MibandData data = new MibandData();
        data.setValue(Integer.toString((int)sum/count));
        data.setSensor("Heart Rate");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        //this.mibandData.add(data);
         if (Dataset.datasave==0){
           this.saveData(data);
         }
         else{
             Dataset.addData(data);
         }
         this.mibandData.clear();
    }

    public DataCollector(Context context) {
            Dataset.getInstance();
            this.context = context;
    }

    @Override
    public void Testdatagenerator() {
        MibandData data = new MibandData();
        data.setSensor("generalt");
        data.setTimestamp(System.currentTimeMillis());
        data.setValue("89");
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
            System.out.println("netenvagyok");
            System.out.println(data);
        }
        else{
            Dataset.addData(data);
            System.out.println("neten nem vagyok");
            System.out.println(data);
        }

    }

    @Override
    public void OnHeartRateReceived(int value) {

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


        MibandData data = new MibandData();
        data.setValue("Step value:" + Integer.toString(stepValue) + "; Calories:" + Integer.toString(calories) + "; Distance:" + Integer.toString(distance) + ";");
        data.setSensor("Activity");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }
    }

    @Override
    public void OnSleepStartedOrBraceletWear() {


        MibandData data = new MibandData();
        data.setValue("Sleep started.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }
    }

    @Override
    public void OnWakeUp() {
        MibandData data = new MibandData();
        data.setValue("Waked up.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }
    }

    @Override
    public void OnGoalReached() {
        MibandData data = new MibandData();
        data.setValue("The daily goal was reached.");
        data.setSensor("Activity");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }
    }

    @Override
    public void OnBraceletRemove() {

        MibandData data = new MibandData();
        data.setValue("The user was removed the bracelet.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }
    }

    @Override
    public void OnBatteryLevelChanged(int newValue) {


        MibandData data = new MibandData();
        data.setValue("The batter level is: " + Integer.toString(newValue));
        data.setSensor("Battery");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }
    }

    @Override
    public void OnBatteryStatusChanged() {

        MibandData data = new MibandData();
        data.setValue("The charge status has changed.");
        data.setSensor("Battery");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnTimeChanged() {

        MibandData data = new MibandData();
        data.setValue("New time set.");
        data.setSensor("Mi Band");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnOutdoorRunningSelected() {

        MibandData data = new MibandData();
        data.setValue("Outdoor Running selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnTreadmillSelected() {

        MibandData data = new MibandData();
        data.setValue("Treadmill selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnCyclingSelected() {

        MibandData data = new MibandData();
        data.setValue("Cycling selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnIndoorCyclingSelected() {

        MibandData data = new MibandData();
        data.setValue("Indoor Cycling selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnWalkingSelected() {

        MibandData data = new MibandData();
        data.setValue("Walking selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnFreestyleSelected() {

        MibandData data = new MibandData();
        data.setValue("Freestyle selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnYogaSelected() {

        MibandData data = new MibandData();
        data.setValue("Yoga selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnJumpRopeSelected() {

        MibandData data = new MibandData();
        data.setValue("Jump Rope selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnRowingMachineSelected() {

        MibandData data = new MibandData();
        data.setValue("Rowing Machine selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnEllipticalSelected() {

        MibandData data = new MibandData();
        data.setValue("Elliptical selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnPoolSwimmingSelected() {

        MibandData data = new MibandData();
        data.setValue("Swimming selected by user.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnSelectedActivityStarted() {

        MibandData data = new MibandData();
        data.setValue("The selected activity has started.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }

    }

    @Override
    public void OnSelectedActivityPaused() {

        MibandData data = new MibandData();
        data.setValue("The selected activity has paused.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }
    }

    @Override
    public void OnSelectOrFinishedActivity() {


        MibandData data = new MibandData();
        data.setValue("The selected activity has finished.");
        data.setSensor("??");
        data.setTimestamp(System.currentTimeMillis());
        data.setUserid(DatabeseSave.mauth.getUid());
        if (Dataset.datasave==0){
            this.saveData(data);
        }
        else{
            Dataset.addData(data);
        }
    }

    @Override
    public void saveData(MibandData data) {
        Map savedata = new HashMap<String,Object>();
        long time = System.currentTimeMillis();
        savedata.put(Long.toString(time),data);
        db.collection(mauth.getUid().toString()).document(data.getSensor()).set(savedata, SetOptions.mergeFields(Long.toString(time)));
     //   db.collection(mauth.getUid().toString()).document(data.getSensor()).
    }

    @Override
    public void onCommSuccess(Object data) {
        ;
    }
}
