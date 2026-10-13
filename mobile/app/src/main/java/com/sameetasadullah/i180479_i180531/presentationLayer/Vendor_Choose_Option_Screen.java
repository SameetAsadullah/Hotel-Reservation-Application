package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.Customer;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;
import com.sameetasadullah.i180479_i180531.logicLayer.Vendor;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Vendor_Choose_Option_Screen extends AppCompatActivity {
    RelativeLayout register_hotel, view_registered_hotels;
    CircleImageView dp;
    HRS hrs;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_choose_option_screen);

        register_hotel = findViewById(R.id.rl_register_hotel_button);
        view_registered_hotels = findViewById(R.id.rl_view_registered_hotels);
        dp = findViewById(R.id.display_pic);
        hrs = HRS.getInstance(Vendor_Choose_Option_Screen.this);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String email = getIntent().getStringExtra("email");
        for(int i = 0; i < hrs.getVendors().size(); ++i) {
            if (hrs.getVendors().get(i).getEmail().equals(email)) {
                Picasso.get().load(hrs.getVendors().get(i).getDp()).into(dp);
            }
        }

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Vendor_Choose_Option_Screen.this)
                        .setTitle("Log out")
                        .setMessage("Are you sure you want to log out?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putBoolean("loggedIn", false);
                                editor.commit();
                                editor.apply();
                                Intent intent = new Intent(Vendor_Choose_Option_Screen.this, Splash_Screen.class);
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
        register_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vendor_Choose_Option_Screen.this, Hotel_Registration_Screen.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        view_registered_hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vendor_Choose_Option_Screen.this, Registered_Hotels_Screen.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}