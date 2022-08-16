package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;
import com.sameetasadullah.i180479_i180531.logicLayer.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Reservations_Screen extends AppCompatActivity {


    RecyclerView rv;
    List<Hotel_row> ls;
    HRS hrs;
    ImageView back_button;
    EditText searchEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_screen);

        rv = findViewById(R.id.rv);
        searchEditText = findViewById(R.id.search_edit_text);
        back_button = findViewById(R.id.back_button);
        ls = new ArrayList<>();
        hrs = HRS.getInstance(Reservations_Screen.this);

        String email = getIntent().getStringExtra("email");

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        for (int i = 0; i < hrs.getHotels().size(); ++i) {
            for (int j = 0; j < hrs.getHotels().get(i).getReservations().size(); ++j) {
                Reservation reservation = hrs.getHotels().get(i).getReservations().get(j);
                if (reservation.getCustomerEmail().equals(email)) {
                    ls.add(new Hotel_row(reservation.getHotelName(),
                            reservation.getHotelLocation(), reservation.getRoomNumbers(),
                            reservation.getCheckInDate(), reservation.getCheckOutDate()));
                }
            }
        }

        //Adapter
        Hotel_row_adapter adapter = new Hotel_row_adapter(ls,this);
        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}