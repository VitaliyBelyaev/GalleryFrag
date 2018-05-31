package com.example.vitaliybelyaev.galleryfrag.auth;

import android.support.annotation.NonNull;

public interface AuthCallback {

    void onAuthCodeReceived(@NonNull String authCode);
}
