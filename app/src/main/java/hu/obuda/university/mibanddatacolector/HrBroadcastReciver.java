package hu.obuda.university.mibanddatacolector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HrBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("hu.obuda.university.mibanddatacolector.intent.action.HR")) {
            if  (Settings.mainActivity.textView != null) {
                Settings.mainActivity.textView.setText(intent.getExtras().getString("value"));
            }

        }
        if (intent.getAction().equals("hu.obuda.university.mibanddatacolector.intent.action.BATTERY")) {
            if  (Settings.mainActivity.textViewbat != null) {
                Settings.mainActivity.textViewbat.setText(intent.getExtras().getString("value"));
            }

        }
        if (intent.getAction().equals("hu.obuda.university.mibanddatacolector.intent.action.HR_AGG")) {
            if  (Settings.mainActivity.textViewagghr != null) {
                Settings.mainActivity.textViewagghr.setText(intent.getExtras().getString("value"));
            }

        }
        if (intent.getAction().equals("hu.obuda.university.mibanddatacolector.intent.action.ACT")) {
            if  (Settings.mainActivity.textViewact != null) {
                Settings.mainActivity.textViewact.setText(intent.getExtras().getString("value"));
            }

        }
    }
}
