package com.wall.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wall.android.views.TouchImageView;

public class ImageDrawer {

    private static String LOG_TAG = "ImageDrawer";

    private int mMinZoom = 1;
    private int mMaxZoom = 250;
    private Context mContext;
    private String mUrl;
    private ImageView mImageAuxView;
    private TouchImageView mImage;

    public ImageDrawer(Context context, String url, TouchImageView imageMain) {
        mContext = context;
        mUrl = url;
        mImageAuxView = new ImageView(mContext);
        mImage = imageMain;
    }

    public void setZoom(int minValue, int maxValue) {
        mMinZoom = minValue;
        mMaxZoom = maxValue;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void loadImage() {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mImageAuxView.setImageBitmap(bitmap);
                mImage.setImageDrawable(getDrawable(true));
                mImage.setMinZoom(mMinZoom);
                mImage.setMaxZoom(mMaxZoom);
                Log.d(LOG_TAG, "Ok!");
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {
                Log.d(LOG_TAG, "Loading");
            }

            @Override
            public void onBitmapFailed(Drawable drawable) {
                Log.d(LOG_TAG, "Failed");
            }
        };

        Picasso.with(mContext).load(mUrl).into(target);
    }

    private Drawable getDrawable(boolean antiAliasing) {
        if (antiAliasing) disableAntiAliasing();
        return mImageAuxView.getDrawable();
    }

    private void disableAntiAliasing() {
        mImageAuxView.getDrawable().setFilterBitmap(false);
    }
}
