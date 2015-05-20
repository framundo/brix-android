package com.wall.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.wall.android.R;
import com.wall.android.utils.UserUtils;
import com.wall.android.instances.AppUser;
import com.wall.android.instances.FacebookUser;
import com.wall.android.service.LoginService;
import com.wall.android.service.adapter.RestAdapter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LogInActivity extends WoloxActivity {

    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setUi();
        initFacebook();
    }

    @Override
    protected int layout() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUi() {
        mLoginButton = (LoginButton) findViewById(R.id.login_button);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void populate() {

    }

    @Override
    protected void init() {
        mCallbackManager = CallbackManager.Factory.create();
    }

    private void initFacebook() {
        mLoginButton.setReadPermissions("user_friends");
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                FacebookUser facebookUser = new FacebookUser(loginResult.getAccessToken().getToken(),
                        loginResult.getAccessToken().getExpires());

                LoginService loginTry = RestAdapter.
                        getAdapter().create(LoginService.class);

                loginTry.postUser(facebookUser, new Callback<AppUser>() {
                    @Override
                    public void success(AppUser user, Response response) {
                        UserUtils.saveUser(getApplicationContext(), user);
                        startActivity(new Intent(LogInActivity.this, WallActivity.class));
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(),
                                R.string.error_network, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancel() {
                Log.d("Callback", "Cancel!");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Callback", "Error!");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
