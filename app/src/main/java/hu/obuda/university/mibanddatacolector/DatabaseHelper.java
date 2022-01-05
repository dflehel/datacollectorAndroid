package hu.obuda.university.mibanddatacolector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "miband.db";
    public static final String TABLE_NAME= "miband_table";
    public static final String COL_1= "ID";
    public static final String COL_2= "UID";
    public static final String COL_3= "SENSOR";
    public static final String COL_4= "TIMESTAMP";
    public static final String COL_5= "VALUE";

    public DatabaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 1);
       // SQLiteDatabase db = this.getWritableDatabase();
       // db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, UID TEXT, SENSOR TEXT,TIMESTAMP TEXT, VALUE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROPTABLE TABLE IF EXIST "+TABLE_NAME);
            onCreate(sqLiteDatabase);
    }

    public boolean insertdata(String ui,String sensor,String timestamp,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UID",ui);
        contentValues.put("SENSOR",sensor);
        contentValues.put("TIMESTAMP",timestamp);
        contentValues.put("VALUE",value);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result ==-1){
            System.out.println("nemsikerult a mentes");
            return false;
        }
        else{
            return true;
        }



    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor qery = db.rawQuery("select * from "+TABLE_NAME,null);
        return qery;
    }

    public Integer deletelAll(){
        SQLiteDatabase sqLiteDatabase  =  this.getWritableDatabase();
       return sqLiteDatabase.delete(TABLE_NAME,null,null);


    }
}
