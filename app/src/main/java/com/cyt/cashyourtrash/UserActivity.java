package com.cyt.cashyourtrash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    NavigationView navigationView;
    View header;

    Toolbar toolbar;
    TextView surveyorUserName;
    TextView surveyorName;
    TextView binText;

    String username;
    String name;
    Integer walletMoney;

    BarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        barcodeView = (BarcodeView) findViewById(R.id.barcode_view);
        binText = (TextView) findViewById(R.id.bin_text);

        //check credentials for login
        pref = getApplicationContext().getSharedPreferences("CYTPref", 0);

        //set title
        getSupportActionBar().setTitle("Dashboard");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);

        username = getIntent().getStringExtra("UserName");
        name = getIntent().getStringExtra("Name");
        walletMoney = getIntent().getIntExtra("WalletMoney", 0);

        surveyorUserName = header.findViewById(R.id.user_username);
        surveyorUserName.setText(username);

        surveyorName = header.findViewById(R.id.user_name);
        surveyorName.setText(name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Service will be available soon.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_activty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.wallet_money) {
            item.setTitle(walletMoney.toString());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myAccount) {
            // Handle the account action
            barcodeView.setVisibility(View.INVISIBLE);
            binText.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_earn) {
            barcodeView.setVisibility(View.VISIBLE);
            binText.setVisibility(View.INVISIBLE);
            onEarnClicked();
        } else if (id == R.id.nav_redeem) {
            barcodeView.setVisibility(View.INVISIBLE);
            binText.setVisibility(View.INVISIBLE);
            onRedeemClicked();
        } else if (id == R.id.nav_Settings) {
            barcodeView.setVisibility(View.INVISIBLE);
            binText.setVisibility(View.INVISIBLE);

        } else if (id == R.id.nav_help) {
            barcodeView.setVisibility(View.INVISIBLE);
            binText.setVisibility(View.INVISIBLE);

        } else if (id == R.id.nav_about) {
            barcodeView.setVisibility(View.INVISIBLE);
            binText.setVisibility(View.INVISIBLE);

        } else if (id == R.id.nav_logout) {

            editor = pref.edit();
            editor.clear();
            editor.apply();

            Intent i = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onRedeemClicked() {
        binText.setText("You can redeem your points here later");
        binText.setVisibility(View.VISIBLE);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void onEarnClicked() {
        Toast.makeText(getApplicationContext(), "Scan a barcode", Toast.LENGTH_LONG).show();
        barcodeView.decodeContinuous(callback);
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(final BarcodeResult result) {
            //code to handle “result”
//            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
            barcodeView.setVisibility(View.GONE);
            binText.setText("Welcome to " + result.getText() + "\n Put Trash Now");
            binText.setVisibility(View.VISIBLE);
            navigationView.getMenu().getItem(0).setChecked(true);

        }

        @Override
        public void possibleResultPoints(List resultPoints) {
        }
    };

    @Override
    public void onResume() {
        barcodeView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        barcodeView.pause();
        super.onPause();
    }
}
