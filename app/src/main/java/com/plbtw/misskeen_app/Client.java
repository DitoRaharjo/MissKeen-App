package com.plbtw.misskeen_app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paulina on 5/15/2017.
 */
public class Client {
    public static final String BASE_URL = "http://ditoraharjo.co/misskeen/api/v1/";

    private static Retrofit retrofit = null;



    public static Retrofit getClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor) //  todo for debug
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    private class MyInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder reqBuilder = original.newBuilder();

            Request request = reqBuilder.build();
            okhttp3.Response response = chain.proceed(request);

            String rawJson = response.body().string();

            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), rawJson)).build();
        }
    }
}
