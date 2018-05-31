package com.example.vitaliybelyaev.galleryfrag.network;

import com.example.vitaliybelyaev.galleryfrag.domain.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("/oauth/token")
    @FormUrlEncoded
    Call<AuthResponse> getAuthToken(@Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("redirect_uri") String redirectUri,
                                    @Field("code") String authCode,
                                    @Field("grant_type") String grantType);

}