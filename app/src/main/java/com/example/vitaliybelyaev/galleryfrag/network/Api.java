package com.example.vitaliybelyaev.galleryfrag.network;

import com.example.vitaliybelyaev.galleryfrag.domain.Collection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/collections/curated")
    Call<List<Collection>> getCollections();
}
