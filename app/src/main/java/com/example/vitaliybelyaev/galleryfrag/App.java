package com.example.vitaliybelyaev.galleryfrag;

import android.app.Application;

import com.example.vitaliybelyaev.galleryfrag.network.Api;
import com.example.vitaliybelyaev.galleryfrag.network.NetworkModule;

public class App extends Application {

    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();

        api = new NetworkModule(this).getApi();
    }

    public Api getApi() {
        return api;
    }
}
