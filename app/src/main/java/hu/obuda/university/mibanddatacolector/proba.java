package hu.obuda.university.mibanddatacolector;

import java.util.HashMap;

public class proba {

    public HashMap<String,Object> data = new HashMap<>();

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;

    }

    public proba(HashMap<String, Object> data) {
        this.data = data;
    }

    public proba() {
        this.data = new HashMap<>();
    }

    public void addelement(String s,String s1){
        this.data.put(s,s1);
    }
}
