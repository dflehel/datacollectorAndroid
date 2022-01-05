package hu.obuda.university.mibanddatacolector;

import java.util.HashMap;

public class Mibanddatabaseelement {

    private String sensor;
    private HashMap<String,Object> data;

    public Mibanddatabaseelement(String sensor) {
        this.sensor = sensor;
    }

    public Mibanddatabaseelement(String sensor, HashMap<String, Object> data) {
        this.sensor = sensor;
        this.data = data;
    }

    public Mibanddatabaseelement() {
        this.data = new HashMap<>();
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public void addelement(String timestamp,String value){
        this.data.put(timestamp,value);
    }
}
