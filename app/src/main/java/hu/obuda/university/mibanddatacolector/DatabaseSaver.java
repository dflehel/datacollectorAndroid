package hu.obuda.university.mibanddatacolector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DatabaseSaver implements DatabeseSave {

    DatabaseHelper databaseHelper;
    private DatabaseReference mDatabase;

    public DatabaseSaver(Context context){
        databaseHelper = new DatabaseHelper(context);
        mDatabase = FirebaseDatabase.getInstance().getReference();
      //  System.out.println("megjottem");
    }

    public void dataSave(){
        if (Settings.online) {
            Map<String, Object> adatok = new HashMap<>();
            DatabaseReference databaseReference = mDatabase.child("proba");

            Cursor res = databaseHelper.getAllData();
            Map<String, Object> adatokk = new HashMap<>();
            // adatokk.put("baj",new HashMap<>());

          //  System.out.println("megyek");
            if (res.getCount() == 0) {
                // System.out.println("nem jott adat");
            } else {
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    if (adatok.keySet().contains(res.getString(2)) == false) {
                        Map<String, String> reszadat = new HashMap<>();
                        adatok.put(res.getString(2), reszadat);
                    }
                    ((Map<String, String>) (adatok.get(res.getString(2)))).put(res.getString(3), res.getString(4));
                    //System.out.println(res.getShort(1)+ " "+res.getString(2)+" "+res.getString(3)+ " "+res.getString(4));

                }
                //System.out.println("elso");

                // System.out.println("harmadik");
                Integer inti = databaseHelper.deletelAll();
                // System.out.println(inti);
             //   System.out.println("masodik");
                adatok.remove(null);
                for (String i : adatok.keySet()) {
                    mDatabase.child(mauth.getUid()).child(i).updateChildren((Map<String, Object>) (adatok.get(i)));
                }
                //  mDatabase.child(mauth.getUid()).updateChildren(adatok);
             //  System.out.println("vegleg");
            }
        }
    }

    @Override
    public void saveData(MibandData data){
    }
}
