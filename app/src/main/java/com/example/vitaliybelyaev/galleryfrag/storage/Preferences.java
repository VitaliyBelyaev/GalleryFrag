package com.example.vitaliybelyaev.galleryfrag.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String PREF_NAME = "unsplash_preferences";
    private static final String ACCESS_TOKEN_KEY = "access_token";

    private final SharedPreferences sharedPreferences;

    public Preferences(Context context){
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token){
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply();
    }

    public String getSavedToken(){
        return sharedPreferences.getString(ACCESS_TOKEN_KEY,"");
    }
}
