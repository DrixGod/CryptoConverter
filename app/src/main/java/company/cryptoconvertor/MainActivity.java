package company.cryptoconvertor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button b;
    private TextView t;
    private TextView countryTextView;
    private LocationManager locationManager;
    double LAT;
    double LNG;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        t = (TextView) findViewById(R.id.textView);
        b = (Button) findViewById(R.id.button);
        countryTextView = findViewById(R.id.text10);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            return;
        }

        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);
        loc_func(location);
    }

    @SuppressLint("SetTextI18n")
    public void onLocationChanged(Location location){
        LAT = location.getLongitude();
        LNG = location.getLatitude();
    }

    private void loc_func(Location location){
        try{
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList = null;
            addressList = geocoder.getFromLocation(LAT,LNG,1);
            String country = addressList.get(0).getCountryName();
            countryTextView.setText(country);
        }catch (Exception ignored){
        }
    }
}
