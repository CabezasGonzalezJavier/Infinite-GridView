package com.thedeveloperworldisyours.gridviewinfinity.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thedeveloperworldisyours.gridviewinfinity.R;
import com.thedeveloperworldisyours.gridviewinfinity.adapters.ImageAdapter;
import com.thedeveloperworldisyours.gridviewinfinity.utils.Constants;

import static com.thedeveloperworldisyours.gridviewinfinity.utils.Constants.IMAGE_HTTP;

public class ZoomActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get intent data
        Intent i = getIntent();

        // Selected image
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String image = extras.getString(IMAGE_HTTP);

        ImageView imageView = (ImageView) findViewById(R.id.activity_zoom_imageView);
        Picasso.with(ZoomActivity.this).load(image).into(imageView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
