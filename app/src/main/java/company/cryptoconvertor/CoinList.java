package company.cryptoconvertor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoinList extends AppCompatActivity {

    private final String COUNTRY_CURRENCY = MainActivity.getCurrency();
    private String COIN = "BTC";
    public String CRYPTO_URL = "https://min-api.cryptocompare.com/data/price?fsym="+COIN+"&tsyms="+COUNTRY_CURRENCY;
    private TextView priceTextView;
    private TextView symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_layout);

        priceTextView = findViewById(R.id.priceUsd);
        symbol = findViewById(R.id.symbol);
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

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();
                    final String parseResponse = parseResponse(myResponse);
                    System.out.println("\n\n" + myResponse + "\n\n");
                    CoinList.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            priceTextView.setText(parseResponse);
                            symbol.setText(MainActivity.getCurrencySymbol());
                        }
                    });
                }
            }
        });
    }

    private String parseResponse(String response){
        String[] helper = response.split(":",2);
        return helper[1].substring(0, helper[1].length() - 1);
    }

}
