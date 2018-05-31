package com.example.vitaliybelyaev.galleryfrag.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.vitaliybelyaev.galleryfrag.App;
import com.example.vitaliybelyaev.galleryfrag.R;
import com.example.vitaliybelyaev.galleryfrag.domain.AuthResponse;
import com.example.vitaliybelyaev.galleryfrag.network.NetworkModule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    private static final String LOG_TAG = AuthActivity.class.getSimpleName();
    public static final int AUTH_REQUEST_CODE = 13;
    public static final String TOKEN_KEY = "token";
    public static final String GRANT_TYPE = "authorization_code";

    public static final String AUTH_URL = NetworkModule.API_AUTH_HOST + "oauth/authorize?" +
            "client_id=" + NetworkModule.CLIENT_ID +
            "&redirect_uri=" + NetworkModule.REDIRECT_URI +
            "&response_type=code" +
            "&scope=public+write_likes";

    private final AuthCallback authCallback = new AuthCallback() {
        @Override
        public void onAuthCodeReceived(@NonNull String authCode) {
            authorizeUser(authCode);
        }
    };

    public static void startForResult(Activity activity){
        Intent intent = new Intent(activity, AuthActivity.class);
        activity.startActivityForResult(intent, AUTH_REQUEST_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new AuthWebViewClient(authCallback));
        webView.loadUrl(AUTH_URL);
    }

    public void authorizeUser(final String authCode){
        NetworkModule.getAuthApi().getAuthToken(
                NetworkModule.CLIENT_ID,
                NetworkModule.CLIENT_SECRET,
                NetworkModule.REDIRECT_URI,
                authCode,
                GRANT_TYPE
        ).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                final AuthResponse authResponse = response.body();
                if(authResponse != null){
                    Intent intent = new Intent();
                    intent.putExtra(TOKEN_KEY, authResponse.getToken());
                    setResult(RESULT_OK, intent);
                    Toast.makeText(getApplicationContext(),"Success authorized",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
               Log.e(LOG_TAG, t.toString());
               Toast.makeText(getApplicationContext(),"Error while authorizing",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
