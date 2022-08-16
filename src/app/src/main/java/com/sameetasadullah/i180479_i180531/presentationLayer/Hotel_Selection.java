package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;
import com.sameetasadullah.i180479_i180531.logicLayer.Hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Hotel_Selection extends AppCompatActivity {

    RecyclerView rv;
    List<Hotel_Selection_row> ls;
    HRS hrs;
    String Location,Persons,checkInDate,TypeRoom,Email,checkOutDate;
    boolean both;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selection);

        backButton = findViewById(R.id.back_button);
        rv = findViewById(R.id.rv);
        ls = new ArrayList<>();
        hrs= HRS.getInstance(Hotel_Selection.this);
        Location = getIntent().getStringExtra("Location");
        Persons =getIntent().getStringExtra("Persons");
        checkInDate=getIntent().getStringExtra("localDate");
        checkOutDate=getIntent().getStringExtra("checkoutDate");
        TypeRoom=getIntent().getStringExtra("TypeRoom");
        Email = getIntent().getStringExtra("Email");
        Bundle bundle =getIntent().getExtras();
         both = bundle.getBoolean("both");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(checkInDate, formatter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Vector<Hotel> hotels=hrs.getHotels(Location,Persons,localDate,TypeRoom,both);
        for (int i=0; i < hotels.size();i++){
            ls.add(new Hotel_Selection_row(hotels.get(i).getName(),hotels.get(i).getLocation(),hotels.get(i).getSingleRoomPrice(),hotels.get(i).getDoubleRoomPrice()));
        }

        //Adapter
        Hotel_Selection_adapter adapter = new Hotel_Selection_adapter(ls,this, hotels);
        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

    }




}