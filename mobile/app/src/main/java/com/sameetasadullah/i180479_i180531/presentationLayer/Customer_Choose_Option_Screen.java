package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.Customer;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import de.hdodenhof.circleimageview.CircleImageView;

public class Customer_Choose_Option_Screen extends AppCompatActivity {
    RelativeLayout reserve_hotel, view_old_reservations;
    CircleImageView dp;
    HRS hrs;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_choose_option_screen);

        reserve_hotel = findViewById(R.id.rl_reserve_hotel_button);
        view_old_reservations = findViewById(R.id.rl_view_old_reservations);
        dp = findViewById(R.id.display_pic);
        hrs = HRS.getInstance(Customer_Choose_Option_Screen.this);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String email = getIntent().getStringExtra("email");
        for(Customer customer : hrs.getCustomers()) {
            if (customer.getEmail().equals(email)) {
                Picasso.get().load(customer.getDp()).into(dp);
            }
        }

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Customer_Choose_Option_Screen.this)
                        .setTitle("Log out")
                        .setMessage("Are you sure you want to log out?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putBoolean("loggedIn", false);
                                editor.commit();
                                editor.apply();
                                Intent intent = new Intent(Customer_Choose_Option_Screen.this, Splash_Screen.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        reserve_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer_Choose_Option_Screen.this, Reserve_Screen.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        view_old_reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Customer_Choose_Option_Screen.this, Reservations_Screen.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}