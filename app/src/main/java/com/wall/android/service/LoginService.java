package com.wall.android.service;

import com.wall.android.instances.AppUser;
import com.wall.android.instances.FacebookUser;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface LoginService {

    @POST("/api/auth/facebook/callback")
    public void postUser(@Body FacebookUser body,
                      Callback<AppUser> userCallback);
}
