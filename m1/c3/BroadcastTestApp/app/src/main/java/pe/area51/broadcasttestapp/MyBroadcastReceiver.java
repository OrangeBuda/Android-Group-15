package pe.area51.broadcasttestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case MainActivity.MENU_BUTTON_PRESSED_ACTION:
                Log.i(TAG, intent.getAction());
                Log.i(TAG, intent.getStringExtra(MainActivity.MENU_BUTTON_PRESSED_EXTRA));
                break;
        }

    }
}
