package com.cyt.cashyourtrash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cyt.cashyourtrash.model.Register;
import com.cyt.cashyourtrash.model.User;
import com.cyt.cashyourtrash.service.Callback;
import com.cyt.cashyourtrash.service.RetrofitService;

/**
 * Created by Kunal on 06-12-2017.
 */

public class RegisterActivity extends AppCompatActivity{

    // UI references.
    private EditText mnameView;
    private EditText mPasswordView;
    private EditText mUsernameView;
    private EditText mMobileNoView;
    private View mProgressView;
    private View mRegisterFormView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //FullScreen Mode
        getSupportActionBar().hide();

        // Set up the login form.
        mnameView=(EditText) findViewById(R.id.register_name);
        mUsernameView = (EditText) findViewById(R.id.register_username);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mMobileNoView = (EditText) findViewById(R.id.register_mobileNo);

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    private void attemptRegister() {

        // Store values at the time of the login attempt.
        String name = mnameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String username = mUsernameView.getText().toString();
        Double mobileNo = Double.valueOf(mMobileNoView.getText().toString());

        boolean cancel = false;
        View focusView = null;

        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            mnameView.setError(getString(R.string.error_field_required));
            focusView = mnameView;
            cancel = true;
        }else if (!isNameValid(name)) {
            mnameView.setError(getString(R.string.error_invalid_name));
            focusView = mnameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUserNameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        // Check for a valid mobile no, if the user entered one.
        if (TextUtils.isEmpty(mobileNo.toString())) {
            mMobileNoView.setError(getString(R.string.error_field_required));
            focusView = mMobileNoView;
            cancel = true;
        }else if (!isMobileNoValid(mobileNo)) {
            mMobileNoView.setError(getString(R.string.error_invalid_mobile_number));
            focusView = mMobileNoView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform t9he user login attempt.;
            showProgress(true);
            performRegister(name,username,password,mobileNo);

        }
    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
//        return username.contains("a-z");
        return true;
    }

    private boolean isUserNameValid(String username) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    private boolean isMobileNoValid(Double mobileNo) {
        //TODO: Replace this with your own logic
        return true;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void performRegister(String name,String username,String password,Double mobileNo) {


        RetrofitService retrofitService = new RetrofitService();
        retrofitService.Register(name,username,password,mobileNo,new Callback<Register>() {

            @Override
            public void onSuccess(Register response) {
                if (response != null) {
                    if(response.getMessage().equals("Successfully added")){
                        showProgress(false);
                        Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_LONG).show();

                        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Error error) {
                Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_SHORT).show();
                Log.d("CYT", error.getMessage());

            }
        });

    }


}
