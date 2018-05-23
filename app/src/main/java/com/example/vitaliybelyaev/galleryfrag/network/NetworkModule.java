package com.example.vitaliybelyaev.galleryfrag.network;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {

    private static final String API_HOST = "https://api.unsplash.com/";

    private static final String API_KEY = "bad64aed35624bb8365b4bddb05e6b7da1e1064b3d54cf299e75d427b8d00f10";
    private static final String API_KEY_NAME = "client_id";

    private final Api api;
    private static final long HTTP_CACHE_SIZE = 1024 * 1024 * 24L; // 24 MB

    public NetworkModule(Context context) {
        final Interceptor interceptor = provideRequestInterceptor();
        final OkHttpClient okHttpClient = provideOkHttp(context.getCacheDir(), interceptor);
        final Retrofit retrofit = provideRetrofit(okHttpClient);
        api = retrofit.create(Api.class);

        providePicasso(context, okHttpClient);
    }

    public Api getApi() {
        return api;
    }

    private Interceptor provideRequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request originalRequest = chain.request();
                final HttpUrl newUrl = originalRequest.url().newBuilder()
                        .addQueryParameter(API_KEY_NAME, API_KEY)
                        .build();

                final Request newRequest = originalRequest.newBuilder()
                        .url(newUrl)
                        .addHeader("Accept", "application/json")
                        .build();

                return chain.proceed(newRequest);
            }
        };
    }

    private OkHttpClient provideOkHttp(File cacheDir, Interceptor interceptor) {
        final HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("OkHttp", message);
            }
        };

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger) //
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .cache(new Cache(new File(cacheDir, "http"), HTTP_CACHE_SIZE))
                .build();

    }

    private Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private void providePicasso(Context context,OkHttpClient okHttpClient){
        final Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();

        Picasso.setSingletonInstance(picasso);
    }
}
