package com.wall.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wall.android.instances.CoordUv;
import com.wall.android.instances.UserPixel;
import com.wall.android.views.TouchImageView;

import java.util.ArrayList;

public class ImageDrawer {

    private static String LOG_TAG = "ImageDrawer";

    private Context mContext;
    private String mUrl;
    private ImageView mImageAuxView;
    private TouchImageView mImage;
    private ArrayList<UserPixel> mUserPixels;
    private Bitmap mBitmap;
    private int mMinZoom = 1;
    private int mMaxZoom = 1000;
    private int mScale = 300;

    public ImageDrawer(Context context, TouchImageView imageMain) {
        mUserPixels = new ArrayList<>();
        mContext = context;
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
                Log.d(LOG_TAG, "Height " + bitmap.getHeight());
                Log.d(LOG_TAG, "Width " + bitmap.getWidth());
                mBitmap = bitmap;

                if (mUserPixels.isEmpty()) return;
                selectAPixel(0);
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

    public ArrayList<String> getPixelsArray() {
        ArrayList<String> array = new ArrayList<>();

        for (UserPixel pixel : mUserPixels) {
            array.add("(X,Y)=(" + pixel.getX()+ "," + pixel.getY() + ") " + pixel.getColor());
        }

        return array;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void addPixels(ArrayList<UserPixel> userPixels) {
        mUserPixels.addAll(userPixels);
    }

    public CoordUv getPixelMapped(int pos) {
        return MapUtils.map(mBitmap.getWidth(), mBitmap.getHeight(), mUserPixels.get(pos));
    }

    public void selectAPixel(int position) {
        CoordUv coordUv = getPixelMapped(position);
        mImage.setZoom(mScale, coordUv.getX(), coordUv.getY());
    }

    private Drawable getDrawable(boolean antiAliasing) {
        if (antiAliasing) disableAntiAliasing();
        return mImageAuxView.getDrawable();
    }

    private void disableAntiAliasing() {
        mImageAuxView.getDrawable().setFilterBitmap(false);
    }
}
