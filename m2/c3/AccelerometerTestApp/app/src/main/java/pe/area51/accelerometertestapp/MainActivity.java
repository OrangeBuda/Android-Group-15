package pe.area51.accelerometertestapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private boolean isAccelerometerEnabled;
    private SensorManager sensorManager;
    private TextView textViewX;
    private TextView textViewY;
    private TextView textViewZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isAccelerometerEnabled = false;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        textViewX = (TextView) findViewById(R.id.textview_x_axis);
        textViewY = (TextView) findViewById(R.id.textview_y_axis);
        textViewZ = (TextView) findViewById(R.id.textview_z_axis);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_accelerometer:
                if (isAccelerometerEnabled) {
                    item.setTitle(R.string.start_accelerometer);
                    isAccelerometerEnabled = false;
                    stopAccelerometer();
                } else {
                    item.setTitle(R.string.stop_accelerometer);
                    isAccelerometerEnabled = true;
                    startAccelerometer();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateTextViews(final float x, final float y, final float z) {
        textViewX.setText(getString(R.string.accelerometer_x, x));
        textViewY.setText(getString(R.string.accelerometer_y, y));
        textViewZ.setText(getString(R.string.accelerometer_z, z));
    }

    private void startAccelerometer() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void stopAccelerometer() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateTextViews(event.values[0], event.values[1], event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
