package hu.obuda.university.mibanddatacolector;

import java.util.HashMap;
import java.util.Map;

public class DataCollector implements MiBandNotificationInterface, DatabeseSave {

    public DataCollector() {


    }

    @Override
    public void OnHeartRateReceived(int value) {

        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void OnMovingActivity(int stepValue, int calories, int distance) {


        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void OnSleepStartedOrBraceletWear() {


        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void OnWakeUp() {


        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void OnGoalReached() {


        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void OnBraceletRemove() {


        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void OnBatteryLevelChanged(int newValue) {


        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void OnBatteryStatusChanged() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnTimeChanged() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnOutdoorRunningSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnTreadmillSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnCyclingSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnIndoorCyclingSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnWalkingSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnFreestyleSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnYogaSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnJumpRopeSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnRowingMachineSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnEllipticalSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnPoolSwimmingSelected() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnSelectedActivityStarted() {

        MibandData data = new MibandData();
        this.saveData(data);

    }

    @Override
    public void OnSelectedActivityPaused() {


        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void OnSelectOrFinishedActivity() {


        MibandData data = new MibandData();
        this.saveData(data);
    }

    @Override
    public void saveData(MibandData data) {
        Map savedata = new HashMap<String,Object>();
        long time = System.currentTimeMillis();
        savedata.put(Long.toString(time),data);
        db.collection(mauth.getUid().toString()).add(savedata);
    }

    @Override
    public void onCommSuccess(Object data) {
        ;
    }
}
