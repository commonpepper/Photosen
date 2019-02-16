package com.commonpepper.photosen.network;

import android.util.Log;

import com.commonpepper.photosen.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class KeyFormatInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.APP_KEY)
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .build();

//        Log.d("URL:", url.toString());

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}