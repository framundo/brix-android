package com.wall.android.activity;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.wall.android.R;
import com.wall.android.utils.UserUtils;
import com.wall.android.instances.AppUser;

public class MainActivity extends WoloxActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        if (isLogged()) {
            startAppActivity();
            return;
        }

        startLoginActivity();
    }

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUi() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void populate() {

    }

    @Override
    protected void init() {
    }

    private boolean isLogged() {
        AppUser user = UserUtils.getUser(getApplicationContext());
        return user != null && !user.getId().isEmpty();
    }

    private void startLoginActivity() {
        startActivity(new Intent(MainActivity.this, LogInActivity.class));
        finish();
    }

    private void startAppActivity() {
        startActivity(new Intent(MainActivity.this, TestActivity.class));
        finish();
    }
}
