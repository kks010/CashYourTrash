package com.cyt.cashyourtrash.service;

import com.cyt.cashyourtrash.model.Register;
import com.cyt.cashyourtrash.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kunal on 14-11-2017.
 */

public interface RetrofitServiceInterface {

    @GET("user")
    Call<User> UserInfo(@Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST("newUser")
    Call<Register> Register(@Field("name") String name,@Field("username") String username,
                            @Field("password") String password,@Field("mobile") Double mobileNo);

}
