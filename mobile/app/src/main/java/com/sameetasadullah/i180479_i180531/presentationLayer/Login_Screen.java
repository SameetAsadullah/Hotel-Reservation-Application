package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login_Screen extends AppCompatActivity {
    ImageView backButton;
    EditText email, password;
    RelativeLayout loginButton;
    HRS hrs;
    TextView page_user, sign_up;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        backButton = findViewById(R.id.back_button);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.log_in_button);
        page_user = findViewById(R.id.tv_page);
        sign_up = findViewById(R.id.tv_sign_up);
        hrs = HRS.getInstance(Login_Screen.this);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String page = getIntent().getStringExtra("Page");
        page_user.setText(page);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();

                if (email_string.equals("") || password_string.equals("")) {
                    Toast.makeText(Login_Screen.this, "Kindly fill all the input fields", Toast.LENGTH_LONG).show();
                }
                else {
                    if (page.equals("Vendor")) {
                        if (hrs.validateVendorAccount(email_string, password_string)) {
                            editor.putString("user", "Vendor");
                            editor.putString("email", email_string);
                            editor.putBoolean("loggedIn", true);
                            editor.commit();
                            editor.apply();
                            Intent intent = new Intent(Login_Screen.this, Vendor_Choose_Option_Screen.class);
                            intent.putExtra("email", email_string);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(Login_Screen.this, "Incorrect email and password", Toast.LENGTH_LONG).show();
                        }
                    }
                    else if (page.equals("Customer")) {
                        if (hrs.validateCustomerAccount(email_string, password_string)) {
                            editor.putString("user", "Customer");
                            editor.putString("email", email_string);
                            editor.putBoolean("loggedIn", true);
                            editor.commit();
                            editor.apply();
                            Intent intent = new Intent(Login_Screen.this, Customer_Choose_Option_Screen.class);
                            intent.putExtra("email", email_string);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(Login_Screen.this, "Incorrect email and password", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Screen.this, Register_Screen.class);
                intent.putExtra("Page", page);
                startActivity(intent);
            }
        });
    }
}