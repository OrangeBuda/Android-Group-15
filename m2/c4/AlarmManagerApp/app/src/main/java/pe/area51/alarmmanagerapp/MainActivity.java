package pe.area51.alarmmanagerapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView alarmStatusTextView;
    private AlarmManager alarmManager;
    private AlarmBroadcastReceiver alarmBroadcastReceiver;
    private MenuItem menuSetAlarmButton;

    private class AlarmBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            cancelPendingIntent();
            updateStatusInfo();
            updateMenuStrings();
        }
    }

    public static final String ACTION_ALARM_FIRED = "pe.area51.alarmmanagerapp.ALARM_FIRED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmStatusTextView = (TextView) findViewById(R.id.textview_alarm_status);
        updateStatusInfo();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmBroadcastReceiver = new AlarmBroadcastReceiver();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ALARM_FIRED);
        registerReceiver(alarmBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(alarmBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuSetAlarmButton = menu.findItem(R.id.action_set_alarm);
        updateMenuStrings();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set_alarm:
                if (isAlarmSet()) {
                    stopAlarm();
                    cancelPendingIntent();
                } else {
                    setAlarm(5);
                }
                updateStatusInfo();
                updateMenuStrings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setAlarm(final int timeInSeconds) {
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, getAlarmIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        final long realtimeTriggerAtMillis = SystemClock.elapsedRealtime() + timeInSeconds * 1000;
        /*
        Queremos que nuestra alarma se ejecute de forma exacta. Para esto tenemos
        dos alternativas:
        -Podemos poner al targetSdk a una versión menor al API 19 (KitKat), ya
        que en esa versión cambió el comportamiento del método set() del AlarmManager.
        -Preguntar qué versión estamos ejecutando ahora para seleccionar
        el método adecuado.
        Vamos a mantener el targetSdk a la última versión posible,
        esto con la finalidad de evitar que la aplicación se
        ejecute en modo compatibilidad en las versiones más nuevas.
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, realtimeTriggerAtMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, realtimeTriggerAtMillis, pendingIntent);
        }
    }

    private void stopAlarm() {
        alarmManager.cancel(PendingIntent.getBroadcast(this, 0, getAlarmIntent(), 0));
    }

    private boolean isAlarmSet() {
        return PendingIntent.getBroadcast(this, 0, getAlarmIntent(), PendingIntent.FLAG_NO_CREATE) != null;
    }

    private void cancelPendingIntent() {
        PendingIntent.getBroadcast(this, 0, getAlarmIntent(), 0).cancel();
    }

    private Intent getAlarmIntent() {
        return new Intent(ACTION_ALARM_FIRED);
    }

    private void updateStatusInfo() {
        alarmStatusTextView.setText(isAlarmSet() ? getString(R.string.alarm_status, getString(R.string.alarm_status_set)) : getString(R.string.alarm_status, getString(R.string.alarm_status_not_set)));
    }

    private void updateMenuStrings() {
        menuSetAlarmButton.setTitle(isAlarmSet() ? R.string.cancel_alarm : R.string.set_alarm);
    }
}
