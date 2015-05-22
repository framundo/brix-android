package com.wall.android.service;

import com.wall.android.instances.UserPixel;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

public interface UserPixelsService {

    @GET("/pixels/mine")
    public void getPixels(Callback<ArrayList<UserPixel>> pixelsRequestCallback);

}
