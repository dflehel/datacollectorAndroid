package hu.obuda.university.mibanddatacolector;

public class datagenerator {

private MibandData mibandData;

    public datagenerator(MibandData mibandData) {
        this.mibandData = mibandData;
    }

    public MibandData getMibandData() {
        return mibandData;
    }

    public void setMibandData(MibandData mibandData) {
        this.mibandData = mibandData;
    }

}
