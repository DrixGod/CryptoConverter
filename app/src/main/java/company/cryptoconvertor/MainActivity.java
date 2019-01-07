package company.cryptoconvertor;

import android.Manifest;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    double LAT;
    double LNG;
    TextView country;
    private static String countryName;
    private static String countryCode;
    private static Currency currency;
    private static String symbol;
    Button coins;
    Button convert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        country = findViewById(R.id.country);
        coins = findViewById(R.id.List);
        convert = findViewById(R.id.Convert);

        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 225);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 225);
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

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, ConvertActivity.class));
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 500);
            }
        });

        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, CoinList.class));
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 500);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void onLocationChanged(Location location){
        LAT = location.getLatitude();
        LNG = location.getLongitude();
    }

    @SuppressLint("SetTextI18n")
    private void getLocationDetails(){
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addressList;
            addressList = geocoder.getFromLocation(LAT,LNG,1);
            String countryName = addressList.get(0).getCountryName();
            String countryCode = addressList.get(0).getCountryCode();
            Locale countryLocale = new Locale("", countryCode);
            currency=Currency.getInstance(countryLocale);
            symbol = currency.getSymbol(countryLocale);
            setCountryName(countryName);
            setCountryCode(countryCode);
            country.setText(countryName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public static String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        MainActivity.countryName = countryName;
    }
    public static String getCountryCode() {
        return countryCode;
    }
    public static String getCurrency() {
        return currency.toString();
    }
    public static String getCurrencySymbol() {
        return symbol;
    }
    public void setCountryCode(String countryCode) {
        MainActivity.countryCode = countryCode;
    }

}
