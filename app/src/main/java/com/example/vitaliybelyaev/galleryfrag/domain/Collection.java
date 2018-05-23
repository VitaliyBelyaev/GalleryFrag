package com.example.vitaliybelyaev.galleryfrag.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Collection{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("total_photos")
    @Expose
    private Integer totalPhotos;

    @SerializedName("preview_photos")
    @Expose
    private List<PreviewPhoto> previewPhotos = null;

    @SerializedName("links")
    @Expose
    private Links links;

    static class Links{
        @SerializedName("html")
        @Expose
        private String html;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getTotalPhotos() {
        return totalPhotos;
    }

    public List<PreviewPhoto> getPreviewPhotos() {
        return previewPhotos;
    }

    public String getLink(){
        return links.html;
    }
}
