package company.cryptoconvertor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConvertActivity extends AppCompatActivity {
    TextView result;
    Button convertButton;
    EditText amount;
    EditText coin;
    private final String COUNTRY_CURRENCY = MainActivity.getCurrency();
    public String CRYPTO_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        result = findViewById(R.id.convertResult);
        convertButton = findViewById(R.id.convertButton);
        amount = findViewById(R.id.amount);
        coin = findViewById(R.id.coin);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Amount can't be empty",Toast.LENGTH_SHORT).show();
                }
                else if(coin.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Coin can't be empty",Toast.LENGTH_SHORT).show();
                } else {
                    CRYPTO_URL  = "https://min-api.cryptocompare.com/data/price?fsym="+coin.getText().toString().toUpperCase()+"&tsyms="+COUNTRY_CURRENCY;
                    String selectedCoin = coin.getText().toString().toUpperCase();
                    makeRequest(selectedCoin);

                }
            }
        });
    }

    public void makeRequest(final String selectedCoin){
        OkHttpClient client = new OkHttpClient();
        String url = CRYPTO_URL;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    final String parseResponse;
                    if (myResponse.contains("There is no data for the symbol")) {
                        result.setText("Invalid coin name! Please try again!");
                    } else{
                        parseResponse = parseResponse(myResponse);
                    double convertedAmount = Double.parseDouble(parseResponse);
                    String amountDeclared = amount.getText().toString();
                    double amountDouble = Double.parseDouble(amountDeclared);
                    final double finalAmount = convertedAmount * amountDouble;
                    System.out.println("\n\n" + myResponse + "\n\n");
                    ConvertActivity.this.runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            result.setText(amount.getText().toString() + " " + selectedCoin + " is " + finalAmount + " " + COUNTRY_CURRENCY);
                        }
                    });
                }
                }
            }
        });
    }

    private String parseResponse(String response){
        String[] helper = response.split(":",2);
        return helper[1].substring(0, helper[1].length() - 1);
    }
}
