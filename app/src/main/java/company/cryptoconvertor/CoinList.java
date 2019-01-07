package company.cryptoconvertor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoinList extends AppCompatActivity {

    private final String COUNTRY_CURRENCY = MainActivity.getCurrency();
    private TextView priceTextView;
    private TextView symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_layout);
        setCoins();
        Button refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPrices();
            }
        });

    }

    private void setCoins(){
        ImageView coinIcon = findViewById(R.id.coinIcon);
        coinIcon.setImageResource(R.drawable.btc);
        priceTextView = findViewById(R.id.priceUsd);
        symbol = findViewById(R.id.symbol);
        requestPrice(priceTextView,symbol,"BTC");

        coinIcon = findViewById(R.id.coinIcon2);
        coinIcon.setImageResource(R.drawable.eth);
        priceTextView = findViewById(R.id.priceUsd2);
        symbol = findViewById(R.id.symbol2);
        requestPrice(priceTextView,symbol,"ETH");

        coinIcon = findViewById(R.id.coinIcon3);
        coinIcon.setImageResource(R.drawable.xrp);
        priceTextView = findViewById(R.id.priceUsd3);
        symbol = findViewById(R.id.symbol3);
        requestPrice(priceTextView,symbol,"XRP");

        coinIcon = findViewById(R.id.coinIcon5);
        coinIcon.setImageResource(R.drawable.bch);
        priceTextView = findViewById(R.id.priceUsd5);
        symbol = findViewById(R.id.symbol5);
        requestPrice(priceTextView,symbol,"BCH");

        coinIcon = findViewById(R.id.coinIcon4);
        coinIcon.setImageResource(R.drawable.eos);
        priceTextView = findViewById(R.id.priceUsd4);
        symbol = findViewById(R.id.symbol4);
        requestPrice(priceTextView,symbol,"EOS");
    }

    private void refreshPrices(){

        priceTextView = findViewById(R.id.priceUsd);
        symbol = findViewById(R.id.symbol);
        requestPrice(priceTextView,symbol,"BTC");

        priceTextView = findViewById(R.id.priceUsd2);
        symbol = findViewById(R.id.symbol2);
        requestPrice(priceTextView,symbol,"ETH");

        priceTextView = findViewById(R.id.priceUsd3);
        symbol = findViewById(R.id.symbol3);
        requestPrice(priceTextView,symbol,"XRP");

        priceTextView = findViewById(R.id.priceUsd5);
        symbol = findViewById(R.id.symbol5);
        requestPrice(priceTextView,symbol,"BCH");

        priceTextView = findViewById(R.id.priceUsd4);
        symbol = findViewById(R.id.symbol4);
        requestPrice(priceTextView,symbol,"EOS");
    }

    private void requestPrice(final TextView priceTextView, final TextView symbol,String COIN){
        OkHttpClient client = new OkHttpClient();
        String url = "https://min-api.cryptocompare.com/data/price?fsym="+COIN+"&tsyms="+COUNTRY_CURRENCY;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    assert response.body() != null;
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
