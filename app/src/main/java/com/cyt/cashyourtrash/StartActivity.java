package com.cyt.cashyourtrash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Kunal on 06-12-2017.
 */

public class StartActivity extends AppCompatActivity {

    SharedPreferences pref;

    String name;
    String username;
    String Id;
    Integer walletMoney;

    Button loginBtn;
    Button registerBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        getSupportActionBar().hide();

        //check credentials for login
        pref = getApplicationContext().getSharedPreferences("CYTPref", 0);
        final Boolean login = pref.getBoolean("Login",false);
        name=pref.getString("Name",null);
        username=pref.getString("UserName",null);
        Id=pref.getString("Id",null);
        walletMoney=pref.getInt("WalletMoney",0);

        loginBtn=(Button)findViewById(R.id.start_login);
        registerBtn=(Button)findViewById(R.id.start_register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        if(login) {

            Intent mainIntent = new Intent(StartActivity.this, UserActivity.class);
            mainIntent.putExtra("Name",name);
            mainIntent.putExtra("UserName",username);
            mainIntent.putExtra("Id",Id);
            mainIntent.putExtra("WalletMoney",walletMoney);
            StartActivity.this.startActivity(mainIntent);
            StartActivity.this.finish();

        }
    }
}
