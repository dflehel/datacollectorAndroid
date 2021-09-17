package hu.obuda.university.mibanddatacolector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Dataset implements DatabeseSave {
    public static ArrayList<MibandData> mibandDataSet = new ArrayList<MibandData>();
    public static int datasave = 0;
    private static Dataset dataset = null;

    private Dataset(){
        mibandDataSet = new ArrayList<MibandData>();
        datasave = 0;

    }
    public static Dataset getInstance(){
        if (dataset == null){
            dataset = new Dataset();
        }
        return dataset;
    }

    public static void addData(MibandData mibandData){
        mibandDataSet.add(mibandData);
    }

    public static void clreaData(){
        mibandDataSet.clear();
    }

    public static void saveDataset(){
        if (dataset != null && mibandDataSet != null)
            for (MibandData data : mibandDataSet){
                dataset.saveData(data);
        }
    }

    @Override
    public void saveData(MibandData data) {
        Map savedata = new HashMap<String,Object>();
        long time = System.currentTimeMillis();
        savedata.put(Long.toString(time),data);
        db.collection(mauth.getUid().toString()).add(savedata);
    }
}