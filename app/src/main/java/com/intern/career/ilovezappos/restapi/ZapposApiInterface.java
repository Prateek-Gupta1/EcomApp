package com.intern.career.ilovezappos.restapi;

import com.intern.career.ilovezappos.model.ZapposProductList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Home on 2/9/17.
 */

public interface ZapposApiInterface {

    @GET("Search")
    Call<ZapposProductList> getProducts(@Query("term") String searchKeyword, @Query("key") String apiKey);
}
