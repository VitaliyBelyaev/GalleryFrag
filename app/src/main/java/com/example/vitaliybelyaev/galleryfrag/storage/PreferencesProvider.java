package com.example.vitaliybelyaev.galleryfrag.storage;

import android.content.Context;
import android.support.annotation.MainThread;

public class PreferencesProvider {

   private static Preferences sharedPreferences;

    private PreferencesProvider(){}

    @MainThread
    public static void initialize(Context context){
        if(sharedPreferences == null){
            sharedPreferences = new Preferences(context);
        } else {
            throw new IllegalStateException("You should not call initialize() twice");
        }
    }

    public static Preferences getPreferences(){
        return sharedPreferences;
    }
}
