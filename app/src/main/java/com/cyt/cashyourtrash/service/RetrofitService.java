package com.cyt.cashyourtrash.service;

import android.util.Log;

import com.cyt.cashyourtrash.model.Register;
import com.cyt.cashyourtrash.model.User;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kunal on 14-11-2017.
 */

public class RetrofitService {

    private static final String BASE_URL = "https://sheltered-bastion-34221.herokuapp.com/api/";

    private RetrofitServiceInterface service;
    private Retrofit retrofit;

    public RetrofitService(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RetrofitServiceInterface.class);
    }


    public void UserInfo(String username,String password,Callback<User> callback) {
        service.UserInfo(username,password).enqueue(new CallbackHandler<User>(retrofit, callback));
    }

    public void Register(String name,String username,String password,Double mobileNo,Callback<Register> callback) {
        service.Register(name,username,password,mobileNo).enqueue(new CallbackHandler<Register>(retrofit, callback));
    }


    private class CallbackHandler<T> implements retrofit2.Callback<T> {

        private Retrofit retrofit;
        private Callback callback;

        public CallbackHandler(Retrofit retrofit, Callback callback) {
            this.retrofit = retrofit;
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful()) {
                callback.onSuccess(response.body());
            } else {
                Log.d("CYT", "response not isSuccessful");
                Log.d("CYT", response.toString());

                Converter<ResponseBody, Error> errorConverter =
                        retrofit.responseBodyConverter(Error.class, new Annotation[0]);
                try {
                    Error error = errorConverter.convert(response.errorBody());
                    callback.onFailure(error);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.d("CYT", "response onFailure");

            Error error = new Error(t.getMessage());

            callback.onFailure(error);
        }
    }

}
