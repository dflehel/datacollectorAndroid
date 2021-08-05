package hu.obuda.university.mibanddatacolector;

public interface MiBandNotificationInterface {

    void OnHeartRateReceived(int value);
    void OnMovingActivity(int stepValue, int calories, int distance);
    void OnSleepStartedOrBraceletWear();
    void OnWakeUp();
    void OnGoalReached();
    void OnBraceletRemove();
    void OnBatteryLevelChanged(int newValue);
    void OnBatteryStatusChanged();
    void OnTimeChanged();
    void OnOutdoorRunningSelected();
    void OnTreadmillSelected();
    void OnCyclingSelected();
    void OnIndoorCyclingSelected();
    void OnWalkingSelected();
    void OnFreestyleSelected();
    void OnYogaSelected();
    void OnJumpRopeSelected();
    void OnRowingMachineSelected();
    void OnEllipticalSelected();
    void OnPoolSwimmingSelected();
    void OnSelectedActivityStarted();
    void OnSelectedActivityPaused();
    void OnSelectOrFinishedActivity();
}
