package company.cryptoconvertor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;
import com.apptakk.http_request.HttpResponse;

import java.io.IOException;
import java.util.Currency;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static String COIN = "BTC";
    public static String CURRENCY = "USD";
    public static String CURRENCY_SYMBOL = "$";
    public String CRYPTO_URL = "https://min-api.cryptocompare.com/data/price?fsym=" + COIN + "&tsyms=" + CURRENCY;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Locale locale = getResources().getConfiguration().locale;
        CURRENCY_SYMBOL= Currency.getInstance(locale).getSymbol();
        final TextView countryTextView = findViewById(R.id.Country);
        doCall(countryTextView);
    }

    private void doCall(final TextView countryTextView){
        new HttpRequestTask(
                new HttpRequest(CRYPTO_URL, HttpRequest.POST, "{ \"currency\": \"value\" }"),
                new HttpRequest.Handler() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void response(HttpResponse response) {
                        if (response.code == 200) {
                            String result = response.body.replaceAll("\"", "")
                                    .replace("{", "").replace("}", "").split(":")[1];
                            countryTextView.setText(result + " " + CURRENCY_SYMBOL);
                        } else {
                            Toast.makeText(getApplicationContext(), "error, check your internet connection!", Toast.LENGTH_LONG).show();
                            Log.e(this.getClass().toString(), "Request unsuccessful: " + response);
                        }
                    }
                }).execute();
    }


}
