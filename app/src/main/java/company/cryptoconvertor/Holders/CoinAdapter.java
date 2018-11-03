package company.cryptoconvertor.Holders;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import company.cryptoconvertor.CoinModel;
import company.cryptoconvertor.Interfaces.ILoadMoreCoins;

import company.cryptoconvertor.R;

public class CoinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ILoadMoreCoins iLoadMoreCoins;
    boolean isLoading;
    Activity activity;
    List<CoinModel> coinModelList;

    int visibleThreshold = 5,lastVisibleItem,totalItemCount;

    public CoinAdapter(RecyclerView recyclerView, Activity activity, List<CoinModel> coinModelList) {
        this.activity = activity;
        this.coinModelList = coinModelList;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold)){
                    if(iLoadMoreCoins != null){
                        iLoadMoreCoins.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setiLoadMoreCoins(ILoadMoreCoins iLoadMoreCoins) {
        this.iLoadMoreCoins = iLoadMoreCoins;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.coin_layout,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CoinModel item = coinModelList.get(position);
        CoinViewHolder holderItem = (CoinViewHolder) holder;

        holderItem.coinName.setText(item.getName());
        holderItem.coinSymbol.setText(item.getSymbol());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
