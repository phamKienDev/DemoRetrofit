package com.hlub.dev.demomvp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeRetrofit {

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static EmployeeService employeeService;

    public static EmployeeService getInstance() {
        if (employeeService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://dummy.restapiexample.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            employeeService = retrofit.create(EmployeeService.class);
        }
        return employeeService;
    }
}
