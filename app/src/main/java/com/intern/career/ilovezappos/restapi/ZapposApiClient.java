package com.intern.career.ilovezappos.restapi;

import com.intern.career.ilovezappos.model.ZapposProductList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prateek on 2/8/17.
 */

public class ZapposApiClient {

    public static final String BASE_URL = "https://api.zappos.com/";
    public static final String API_KEY = "b743e26728e16b81da139182bb2094357c31d331";
    private static Retrofit retrofit = null;

    public static final Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
