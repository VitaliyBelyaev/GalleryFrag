package com.example.vitaliybelyaev.galleryfrag.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("access_token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }
}
