package com.sameetasadullah.i180479_i180531.presentationLayer;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;
import com.sameetasadullah.i180479_i180531.logicLayer.Hotel;

public class Reserve_Screen extends AppCompatActivity {

    ImageView backbutton;
    EditText location,persons,checkinDate,checkoutDate;
    CheckBox single_room,double_room;
    RelativeLayout submitButton;
    HRS hrs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_screen);

        backbutton=findViewById(R.id.back_button);
        location = findViewById(R.id.Location_text);
        persons = findViewById(R.id.Persons_text);
        checkinDate = findViewById(R.id.Check_in_date);
        checkoutDate = findViewById(R.id.Check_out_date);
        submitButton = findViewById(R.id.submit_button);
        single_room = findViewById(R.id.Single_box);
        double_room = findViewById(R.id.Double_box);
        hrs= HRS.getInstance(Reserve_Screen.this);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Location,Persons,CheckinDate,CheckoutDate;
                Location = location.getText().toString();
                Persons = persons.getText().toString();
                CheckinDate = checkinDate.getText().toString();
                CheckoutDate = checkoutDate.getText().toString();
                if(Location.equals("") || Persons.equals("") || CheckinDate.equals("") || CheckoutDate.equals("") ||
                        (single_room.isChecked()==Boolean.FALSE && double_room.isChecked()==Boolean.FALSE)){
                    Toast.makeText(Reserve_Screen.this,"Fill All Boxes Correctly.",Toast.LENGTH_LONG).show();
                }
                else {
                    Boolean single, doub, both;
                    both = Boolean.FALSE;
                    single = Boolean.FALSE;
                    doub = Boolean.FALSE;
                    String TypeRoom="";
                    if (single_room.isChecked()) {
                        single = Boolean.TRUE;
                        TypeRoom="Single";
                    }
                    if (double_room.isChecked()) {
                        doub = Boolean.TRUE;
                        TypeRoom="Double";
                    }
                    if (single && doub) {
                        both = Boolean.TRUE;
                        TypeRoom="";
                    }
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate localDate = LocalDate.parse(CheckinDate, formatter);
                        LocalDate localDate1 = LocalDate.parse(CheckoutDate, formatter);

                        Vector<Hotel> hotels=hrs.getHotels(Location,Persons,localDate,TypeRoom,both);
                        if (hotels.isEmpty()==Boolean.TRUE){
                            Toast.makeText(Reserve_Screen.this,"No Hotels Found",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent(Reserve_Screen.this, Hotel_Selection.class);
                            intent.putExtra("Location",Location);
                            intent.putExtra("Persons",Persons);
                            intent.putExtra("localDate",CheckinDate);
                            intent.putExtra("checkoutDate",CheckoutDate);
                            intent.putExtra("TypeRoom",TypeRoom);
                            intent.putExtra("both",both);
                            intent.putExtra("Email",getIntent().getStringExtra("email"));
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Toast.makeText(Reserve_Screen.this,"Kindly enter dates in correct format (dd/MM/yyyy)",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }
}