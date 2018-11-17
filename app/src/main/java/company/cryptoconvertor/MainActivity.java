package company.cryptoconvertor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    double LAT;
    double LNG;
    TextView locationTextView;
    TextView details;
    private String countryName;
    private String countryCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationTextView = findViewById(R.id.location);
        details = findViewById(R.id.details);

        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        assert locationManager != null;
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
        getLocationDetails();

        //Change activiy -> pentru debug comenteaza asta
        Runnable r = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, CoinList.class));
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 2500);

    }

    @SuppressLint("SetTextI18n")
    public void onLocationChanged(Location location){
        LAT = location.getLatitude();
        LNG = location.getLongitude();
        locationTextView.setText("Latitude: " + LAT + "\nLongitude: " + LNG);
    }

    @SuppressLint("SetTextI18n")
    private void getLocationDetails(){
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList;
            addressList = geocoder.getFromLocation(LAT,LNG,1);
            String countryName = addressList.get(0).getCountryName();
            String countryCode = addressList.get(0).getCountryCode();
            setCountryName(countryName);
            setCountryCode(countryCode);
            details.setText("Country: " + countryName + "\n" + "Code: " + countryCode);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
