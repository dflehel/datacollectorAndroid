package hu.obuda.university.mibanddatacolector;


import java.util.Date;
import java.util.Set;
import com.github.dkharrat.nexusdata.core.ManagedObject;

public class Mibanddataobject extends ManagedObject {


    public interface Property {
        final static String timestamp =String.valueOf(new Date().getTime()) ;
        final static String userid = "";
        final static String sensor ="";
        final static String value ="";
    }


    public String getuserid() {
        return (String)getValue(Property.userid);
    }

    public void setuserid(String title) {
        setValue(Property.userid, title);
    }

    public String getsensor() {
        return (String)getValue(Property.sensor);
    }

    public void setsensor(String notes) {
        setValue(Property.sensor, notes);
    }

    public String vegvalue() {
        return (String)getValue(Property.value);
    }

    public void setvalue(String dueBy) {
        setValue(Property.value, dueBy);
    }

    public long gettimestamp() {
        return (Long) getValue(Property.timestamp);
    }

    public void settimestamp(Long completed) {
        setValue(Property.timestamp, completed);
    }





}
