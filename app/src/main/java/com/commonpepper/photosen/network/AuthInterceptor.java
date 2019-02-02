package com.commonpepper.photosen.network;

import com.commonpepper.photosen.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Client-ID " + BuildConfig.APP_KEY)
                .build();
        return chain.proceed(request);
    }
}