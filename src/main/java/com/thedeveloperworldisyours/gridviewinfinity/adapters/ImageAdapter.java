package com.thedeveloperworldisyours.gridviewinfinity.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thedeveloperworldisyours.gridviewinfinity.R;

import java.util.List;

import static android.R.drawable.ic_menu_help;
import static com.thedeveloperworldisyours.gridviewinfinity.R.drawable.ic_launcher;

/**
 * Created by javiergonzalezcabezas on 1/4/15.
 */
public class ImageAdapter extends ArrayAdapter<String> {
    private Activity mActivity;
    private final List<String> mValues;

    public ImageAdapter( Activity activity, List<String> vaules) {
        super(activity, R.layout.activity_main, vaules);
        this.mActivity = activity;
        this.mValues = vaules;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mActivity);
            imageView.setLayoutParams(new GridView.LayoutParams(385, 285));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackground(mActivity.getResources().getDrawable(android.R.drawable.ic_dialog_info));
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mActivity).load(mValues.get(position)).into(imageView);
        return imageView;
    }
}
