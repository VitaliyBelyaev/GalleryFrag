package com.example.vitaliybelyaev.galleryfrag.network;

import android.content.Context;
import android.util.Log;

import com.example.vitaliybelyaev.galleryfrag.auth.AuthInterceptor;
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

    public static final String API_HOST = "https://api.unsplash.com/";
    public static final String API_AUTH_HOST = "https://unsplash.com/";

    public static final String CLIENT_ID = "bad64aed35624bb8365b4bddb05e6b7da1e1064b3d54cf299e75d427b8d00f10";
    public static final String CLIENT_ID_KEY = "client_id";
    public static final String CLIENT_SECRET = "b0770812ff78bec204d7fe3b4910b0d43d825697bc7d893e99e2871d3a3fdece";

    public static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    private static Api api;
    private static AuthApi authApi;

    private static final long HTTP_CACHE_SIZE = 1024 * 1024 * 24L; // 24 MB

    private NetworkModule(){}

    public static void initialize(Context context){
        final Interceptor interceptor = provideRequestInterceptor();
        final OkHttpClient okHttpClient = provideOkHttp(context.getCacheDir(), interceptor);

        final Retrofit apiRetrofit = provideRetrofit(okHttpClient, API_HOST);
        api = apiRetrofit.create(Api.class);

        final Retrofit apiAuthRetrofit = provideRetrofit(okHttpClient, API_AUTH_HOST);
        authApi = apiAuthRetrofit.create(AuthApi.class);
    }

    public static Api getApi() {
        return api;
    }

    public static AuthApi getAuthApi() {
        return authApi;
    }

    private static Interceptor provideRequestInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request originalRequest = chain.request();
                final HttpUrl newUrl = originalRequest.url().newBuilder()
                        .addQueryParameter(CLIENT_ID_KEY, CLIENT_ID)
                        .build();

                final Request newRequest = originalRequest.newBuilder()
                        .url(newUrl)
                        .addHeader("Accept", "application/json")
                        .build();

                return chain.proceed(newRequest);
            }
        };
    }

    private static OkHttpClient provideOkHttp(File cacheDir, Interceptor interceptor) {
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
                .addInterceptor(new AuthInterceptor())
                .cache(new Cache(new File(cacheDir, "http"), HTTP_CACHE_SIZE))
                .build();

    }

    private static Retrofit provideRetrofit(OkHttpClient okHttpClient, String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
