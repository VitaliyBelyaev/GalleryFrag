package com.example.vitaliybelyaev.galleryfrag.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreviewPhoto {

    @SerializedName("urls")
    @Expose
    private Urls urls;

    static class Urls{

        @SerializedName("small")
        @Expose
        private String small;
        @SerializedName("thumb")
        @Expose
        private String thumb;

    }

    public String getUrlSmall() {
        return urls.small;
    }

    public String getUrlThumb() {
        return urls.thumb;
    }
}
