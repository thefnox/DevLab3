package com.valarino.fernando.lab3;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by kamai on 05-Mar-18.
 */

public class HomepageFragment extends ItemDetailFragment {
    private ItemModel mItem;

    public HomepageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ItemDetailFragment.ARG_ITEM_ID)) {
            mItem = ItemListActivity.contentMap.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        WebView mWebView = rootView.findViewById(R.id.web_view);
        mWebView.loadUrl(getString(R.string.url));
        mWebView.setWebViewClient(new WebViewClient() {
        });

        return rootView;
    }
}
