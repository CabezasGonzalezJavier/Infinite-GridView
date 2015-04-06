package com.thedeveloperworldisyours.gridviewinfinity.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.thedeveloperworldisyours.gridviewinfinity.Listeners.InfiniteScrollListener;
import com.thedeveloperworldisyours.gridviewinfinity.R;
import com.thedeveloperworldisyours.gridviewinfinity.adapters.ImageAdapter;
import com.thedeveloperworldisyours.gridviewinfinity.models.Feed;
import com.thedeveloperworldisyours.gridviewinfinity.utils.Constants;
import com.thedeveloperworldisyours.gridviewinfinity.utils.Utils;
import com.thedeveloperworldisyours.gridviewinfinity.webservices.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.thedeveloperworldisyours.gridviewinfinity.R.string.loading;
import static com.thedeveloperworldisyours.gridviewinfinity.utils.Constants.IMAGE_HTTP;


public class MainActivity extends ActionBarActivity {

    private List<String> mList;
    private ImageAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private GridView mGridView;
    private boolean mFinishScroll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage(MainActivity.this.getResources().getString(R.string.loading));
        mList = new ArrayList<String>();
        mGridView = (GridView) findViewById(R.id.activity_main_gridView);
        mGridView.setOnScrollListener(new InfiniteScrollListener(5) {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                mProgressDialog.show();
                getInfo();
                mFinishScroll = true;
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Send intent to ZoomActivity
                Intent i = new Intent(getApplicationContext(), ZoomActivity.class);
                // Pass image index
                i.putExtra(IMAGE_HTTP, mList.get(position));
                startActivity(i);
            }
        });
        getInfo();
    }

    public void getInfo() {
        if (Utils.isOnline(MainActivity.this)) {
            getFeed();
        } else {
            Toast.makeText(MainActivity.this, R.string.check_internet, Toast.LENGTH_LONG).show();
        }
    }

    public void getList(Feed feed) {

        for (int i = 0; i < feed.getItems().size(); i++) {
            mList.add(feed.getItems().get(i).getMedia().getM());
        }

    }

    public void buildList() {


        if (!mFinishScroll) {
            mAdapter = new ImageAdapter(MainActivity.this, mList);
            mGridView.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
            mFinishScroll = false;
            mProgressDialog.dismiss();
        }
    }

    public void getFeed() {
        Callback<Feed> callback = new Callback<Feed>() {
            @Override
            public void success(Feed feed, Response response) {
                getList(feed);
                buildList();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(MainActivity.this, R.string.data_failed, Toast.LENGTH_LONG).show();
            }
        };
        Client.initRestAdapter().getQuestions(Constants.NOJSONCALLBACK, Constants.FORMAT, callback);
    }

}
