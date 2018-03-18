package com.valarino.fernando.lab3;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamai on 05-Mar-18.
 */

public class FeedFragment extends ItemDetailFragment {
    private ItemModel mItem;
    private List<FeedModel> mFeedList;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecycle;

    public List<FeedModel> parse(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String url = null;
        String description = null;
        boolean isItem = false;
        List<FeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }
                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    url = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }

                if (title != null && url != null && description != null) {
                    if(isItem) {
                        FeedModel item = new FeedModel(title, url, description);
                        items.add(item);
                    }

                    title = null;
                    url = null;
                    description = null;
                    isItem = false;
                }
            }
            return items;
        } finally {
            inputStream.close();
        }
    }


    private class FetchTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute(){
            mSwipe.setRefreshing(true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
             try{
                 URL url = new URL(getString(R.string.feed_url));
                 InputStream stream = url.openConnection().getInputStream();
                 mFeedList = parse(stream);
                 return true;
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             } catch (XmlPullParserException e) {
                 e.printStackTrace();
             }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean succ){
            mSwipe.setRefreshing(false);
            if (succ){
                mRecycle.setAdapter(new FeedAdapter(mFeedList));
            }
        }
    }

    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ItemDetailFragment.ARG_ITEM_ID)) {
            mItem = ItemListActivity.contentMap.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed, container, false);
        mSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mRecycle = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecycle.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchTask().execute((Void) null);
            }
        });
        new FetchTask().execute((Void) null);
        return rootView;
    }
}
