package pe.area51.simplelocationapp;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private boolean isRetrievingLocation;
    private boolean locationRetrieved;
    private TextView dataTextView;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRetrievingLocation = false;
        locationRetrieved = false;
        dataTextView = (TextView) findViewById(R.id.textview_data);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_location_retrieving:
                if (isRetrievingLocation) {
                    item.setTitle(R.string.start_location_retrieving);
                    if (!locationRetrieved) {
                        dataTextView.setText("");
                    }
                    isRetrievingLocation = false;
                    stopLocationRetrieving();
                } else {
                    item.setTitle(R.string.stop_location_retrieving);
                    dataTextView.setText(R.string.retrieving_location);
                    locationRetrieved = false;
                    isRetrievingLocation = true;
                    startLocationRetrieving();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showLocation(final Location location) {
        final String locationInfo =
                getString(R.string.provider, location.getProvider()) + "\n" +
                        getString(R.string.latitude, location.getLatitude()) + "\n" +
                        getString(R.string.longitude, location.getLongitude()) + "\n" +
                        getString(R.string.altitude, location.getAltitude()) + "\n" +
                        getString(R.string.accuracy, location.getAccuracy());
        dataTextView.setText(locationInfo);
    }

    private void startLocationRetrieving() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
    }

    private void stopLocationRetrieving() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationRetrieved = true;
        showLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
