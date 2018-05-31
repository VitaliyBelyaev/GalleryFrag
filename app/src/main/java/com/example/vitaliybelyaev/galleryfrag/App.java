package com.example.vitaliybelyaev.galleryfrag;

import android.app.Application;

import com.example.vitaliybelyaev.galleryfrag.network.Api;
import com.example.vitaliybelyaev.galleryfrag.network.AuthApi;
import com.example.vitaliybelyaev.galleryfrag.network.NetworkModule;
import com.example.vitaliybelyaev.galleryfrag.storage.PreferencesProvider;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NetworkModule.initialize(this);
        PreferencesProvider.initialize(this);
    }
}
