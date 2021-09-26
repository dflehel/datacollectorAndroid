package hu.obuda.university.mibanddatacolector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HrBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("hu.obuda.university.mibanddatacolector.intent.action.HR")) {
            Settings.mainActivity.textView.setText(intent.getExtras().getString("value"));

        }
    }
}
