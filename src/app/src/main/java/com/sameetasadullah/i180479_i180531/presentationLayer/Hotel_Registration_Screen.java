package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;

public class Hotel_Registration_Screen extends AppCompatActivity {
    EditText hotelName, hotelAdd, hotelLoc, totalSingleRooms,
            totalDoubleRooms, singleRoomPrice, doubleRoomPrice;
    RelativeLayout registerButton;
    HRS hrs;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_registration_screen);

        hotelName = findViewById(R.id.tv_hotel_name);
        hotelAdd = findViewById(R.id.tv_hotel_address);
        hotelLoc = findViewById(R.id.tv_hotel_loc);
        backButton = findViewById(R.id.back_button);
        totalSingleRooms = findViewById(R.id.tv_single_rooms);
        singleRoomPrice = findViewById(R.id.tv_single_price);
        totalDoubleRooms = findViewById(R.id.tv_double_rooms);
        doubleRoomPrice = findViewById(R.id.tv_double_price);
        registerButton = findViewById(R.id.register_button);
        hrs = HRS.getInstance(Hotel_Registration_Screen.this);

        String email = getIntent().getStringExtra("email");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelName_string = hotelName.getText().toString();
                String hotelAdd_string = hotelAdd.getText().toString();
                String hotelLoc_string = hotelLoc.getText().toString();
                String totalSingleRooms_string = totalSingleRooms.getText().toString();
                String singleRoomPrice_string = singleRoomPrice.getText().toString();
                String totalDoubleRooms_string = totalDoubleRooms.getText().toString();
                String doubleRoomPrice_string = doubleRoomPrice.getText().toString();

                if (hotelName_string.equals("") || hotelAdd_string.equals("") ||
                        hotelLoc_string.equals("") || totalSingleRooms_string.equals("") ||
                        singleRoomPrice_string.equals("") || totalDoubleRooms_string.equals("") ||
                        doubleRoomPrice_string.equals("")) {
                    Toast.makeText(Hotel_Registration_Screen.this,"Please Fill All Blocks",Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        Integer.parseInt(totalSingleRooms_string);
                        Integer.parseInt(totalDoubleRooms_string);
                        Integer.parseInt(singleRoomPrice_string);
                        Integer.parseInt(doubleRoomPrice_string);

                        if (hrs.validateHotel(hotelName_string, hotelLoc_string)) {
                            hrs.registerHotel(hotelName_string, hotelAdd_string, hotelLoc_string,
                                    totalSingleRooms_string, totalDoubleRooms_string,
                                    singleRoomPrice_string, doubleRoomPrice_string, email);
                            Toast.makeText(Hotel_Registration_Screen.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                            //SQL LITE
                            MyDBHelper helper = new MyDBHelper(Hotel_Registration_Screen.this);
                            SQLiteDatabase database = helper.getWritableDatabase();
                            ContentValues cv = new ContentValues();
                            cv.put(Reservations_Store.OneRegisteration._NAME,hotelName_string);
                            cv.put(Reservations_Store.OneRegisteration._LOCATION,hotelLoc_string);
                            cv.put(Reservations_Store.OneRegisteration._ADDRESS,hotelAdd_string);
                            cv.put(Reservations_Store.OneRegisteration._SINGLEROOMS,totalSingleRooms_string);
                            cv.put(Reservations_Store.OneRegisteration._DOUBLEROOMS,totalDoubleRooms_string);
                            cv.put(Reservations_Store.OneRegisteration._SINGLEPRICE,singleRoomPrice_string);
                            cv.put(Reservations_Store.OneRegisteration._DOUBLEPRICE,doubleRoomPrice_string);
                            cv.put(Reservations_Store.OneRegisteration._REGISTEREDBY,email);
                            double tep = database.insert(Reservations_Store.OneRegisteration.TABLENAME,null,cv);
                            database.close();
                            helper.close();
                            onBackPressed();
                        }
                        else {
                            Toast.makeText(Hotel_Registration_Screen.this,"Hotel with this name and location already exists",Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Hotel_Registration_Screen.this,"Kindly enter integer values where required",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}