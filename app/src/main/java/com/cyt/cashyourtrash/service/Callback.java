package com.cyt.cashyourtrash.service;

/**
 * Created by Kunal on 14-11-2017.
 */
public interface Callback<T> {

    void onSuccess(T response);

    void onFailure(Error error);
}
