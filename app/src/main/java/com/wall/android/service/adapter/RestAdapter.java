package com.wall.android.service.adapter;

import com.wall.android.Config;
import com.wall.android.service.interceptor.SecuredRequestInterceptor;

import retrofit.RequestInterceptor;

public class RestAdapter {

    public static retrofit.RestAdapter getAdapter() {

        RequestInterceptor requestInterceptor = new SecuredRequestInterceptor();

        return new retrofit.RestAdapter.Builder()
                .setEndpoint(Config.API_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();
    }

}
