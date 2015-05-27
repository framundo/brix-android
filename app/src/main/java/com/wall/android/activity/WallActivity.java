package com.wall.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.wall.android.R;
import com.wall.android.instances.Snapshot;
import com.wall.android.instances.UserPixel;
import com.wall.android.service.ImageService;
import com.wall.android.service.UserPixelsService;
import com.wall.android.service.adapter.RestAdapter;
import com.wall.android.utils.ImageDrawer;
import com.wall.android.views.TouchImageView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WallActivity extends WoloxActivity {

    Activity mActivity;
    TouchImageView mImage;
    ImageDrawer mImageDrawer;
    Spinner mSpinner;
    ArrayAdapter<String> mAdapterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUi() {
        mImage = (TouchImageView) findViewById(R.id.imageView);
        mSpinner = (Spinner) findViewById(R.id.pixels_spinner);
    }

    @Override
    protected void init() {
        mActivity = this;
        mImageDrawer = new ImageDrawer(this, mImage);

        UserPixelsService userPixelTry = RestAdapter.
                getAdapter().create(UserPixelsService.class);

        userPixelTry.getPixels(new Callback<ArrayList<UserPixel>>() {
            @Override
            public void success(ArrayList<UserPixel> pixels, Response response) {
                mImageDrawer.addPixels(pixels);
                setSpinner();
                initImageLoad();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),
                        R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initImageLoad() {
        ImageService imageTry = RestAdapter.
                getAdapter().create(ImageService.class);

        imageTry.getSnapshot(new Callback<Snapshot>() {
            @Override
            public void success(Snapshot snapshot, Response response) {
                mImageDrawer.setUrl(snapshot.getUrl());
                mImageDrawer.loadImage();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),
                        R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSpinner() {
        mAdapterSpinner = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item,
                mImageDrawer.getPixelsArray());
        mAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapterSpinner);
    }

    @Override
    protected void setListeners() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                if (mImageDrawer.getBitmap() == null) return;
                mImageDrawer.selectAPixel(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Nothing
            }

        });
    }

    @Override
    protected void populate() {
    }
}
