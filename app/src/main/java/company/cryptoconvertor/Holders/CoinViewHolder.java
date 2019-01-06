package company.cryptoconvertor.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import company.cryptoconvertor.R;

public class CoinViewHolder extends RecyclerView.ViewHolder {
    public ImageView coinIcon;
    public TextView coinSymbol,coinName,coinPrice,twentyForHoursChange,sevenDaysChange;
    public CoinViewHolder(View itemView) {
        super(itemView);

        coinIcon = itemView.findViewById(R.id.coinIcon);
        coinSymbol = itemView.findViewById(R.id.coinSymbol);
        coinName = itemView.findViewById(R.id.coinName);
        coinPrice = itemView.findViewById(R.id.priceUsd);
       // twentyForHoursChange = itemView.findViewById(R.id.twentyFourHour);
       // sevenDaysChange = itemView.findViewById(R.id.sevenDay);
    }
}
