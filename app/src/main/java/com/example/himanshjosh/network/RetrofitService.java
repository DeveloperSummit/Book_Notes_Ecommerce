package com.example.himanshjosh.network;

import static com.example.himanshjosh.utlity.ConstantField.BASE_URL;
import static com.example.himanshjosh.utlity.ConstantField.CONNECTION_TIMEOUT;
import static com.example.himanshjosh.utlity.ConstantField.READ_TIMEOUT;

import static java.util.concurrent.TimeUnit.MINUTES;

import com.example.himanshjosh.utlity.ConstantField;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {



    public static Retrofit retrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, MINUTES)
                .writeTimeout(READ_TIMEOUT, MINUTES)
                .readTimeout(READ_TIMEOUT, MINUTES)
                .build();



        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();



        return retrofit;
    }

}



