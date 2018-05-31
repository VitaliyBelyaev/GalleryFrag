package com.example.vitaliybelyaev.galleryfrag.auth;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthWebViewClient extends WebViewClient{

    private static final String REDIRECT_URL = "https://unsplash.com/oauth/authorize/";

    private final AuthCallback authCallback;

    public AuthWebViewClient(AuthCallback authCallback){
        this.authCallback = authCallback;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("AUTH","Redirect url: "+url);
        if(url.startsWith(REDIRECT_URL)){
            String authCode = url.substring(REDIRECT_URL.length());
            authCallback.onAuthCodeReceived(authCode);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}
