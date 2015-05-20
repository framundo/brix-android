package com.wall.android.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.wall.android.R;
import com.wall.android.instances.Snapshot;
import com.wall.android.service.ImageService;
import com.wall.android.service.adapter.RestAdapter;
import com.wall.android.utils.ImageDrawer;
import com.wall.android.views.TouchImageView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WallActivity extends WoloxActivity {

    TouchImageView mImage;
    ImageDrawer mImageDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout());

        setUi();
        init();
    }

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUi() {
        mImage = (TouchImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void init() {
        ImageService imageTry = RestAdapter.
                getAdapter().create(ImageService.class);

        imageTry.getSnapshot(new Callback<Snapshot>() {
            @Override
            public void success(Snapshot snapshot, Response response) {
                mImageDrawer = new ImageDrawer(getApplicationContext(),
                        snapshot.getUrl(), mImage);
                mImageDrawer.loadImage();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),
                        R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void populate() {
    }
}
