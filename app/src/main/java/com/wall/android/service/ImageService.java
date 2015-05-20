package com.wall.android.service;

import com.wall.android.instances.Snapshot;

import retrofit.Callback;
import retrofit.http.GET;

public interface ImageService {

    @GET("/snapshots/latest")
    public void getSnapshot(Callback<Snapshot> snapshot);

}
