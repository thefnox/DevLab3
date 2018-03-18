package com.valarino.fernando.lab3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kamai on 06-Mar-18.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private List<FeedModel> mFeedList;

    public FeedAdapter(List<FeedModel> list){
        mFeedList = list;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rss_feed, parent, false);
        FeedViewHolder holder = new FeedViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        FeedModel model = mFeedList.get(position);
        ((TextView)holder.mFeedView.findViewById(R.id.titleText)).setText(model.title);
        ((TextView)holder.mFeedView.findViewById(R.id.descriptionText)).setText(model.description);
        ((TextView)holder.mFeedView.findViewById(R.id.linkText)).setText(model.url);
    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        private View mFeedView;
        public FeedViewHolder(View itemView) {
            super(itemView);
            mFeedView = itemView;
        }
    }

}
