package pe.area51.alarmmanagerapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public final static String TAG = "AlarmBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case MainActivity.ACTION_ALARM_FIRED:
                Log.d(TAG, "Alarm fired!");
                PendingIntent.getBroadcast(context, 0, new Intent(MainActivity.ACTION_ALARM_FIRED), 0).cancel();
                Toast.makeText(context, R.string.alarm_fired, Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.d(TAG, "Intent with no action!");
                break;
        }
    }
}
