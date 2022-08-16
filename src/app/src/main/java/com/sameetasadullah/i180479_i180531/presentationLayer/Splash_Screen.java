package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;


public class Splash_Screen extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean loggedIn;
    String user, email;
    HRS hrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("loggedIn", false);
        user = sharedPreferences.getString("user", "");
        email  = sharedPreferences.getString("email", "");

        hrs = HRS.getInstance(Splash_Screen.this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = null;
                if (!loggedIn) {
                    intent = new Intent(Splash_Screen.this, Main_Screen.class);
                }
                else {
                    if (user.equals("Customer")) {
                        intent = new Intent(Splash_Screen.this, Customer_Choose_Option_Screen.class);
                    }
                    else if (user.equals("Vendor")) {
                        intent = new Intent(Splash_Screen.this, Vendor_Choose_Option_Screen.class);
                    }
                    assert intent != null;
                    intent.putExtra("email", email);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}