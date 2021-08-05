package hu.obuda.university.mibanddatacolector;

public class MibandData {

    private long timestamp;
    private String userid;
    private String sensor;
    private String value;

    public MibandData(long timestamp, String userid, String sensor, String value) {
        this.timestamp = timestamp;
        this.userid = userid;
        this.sensor = sensor;
        this.value = value;
    }

    public MibandData() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public String getSensor() {
        return sensor;
    }

    public String getValue() {
        return value;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
