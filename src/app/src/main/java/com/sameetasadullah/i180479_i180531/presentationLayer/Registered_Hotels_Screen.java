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
import com.sameetasadullah.i180479_i180531.logicLayer.Hotel;

import java.util.ArrayList;
import java.util.List;

public class Registered_Hotels_Screen extends AppCompatActivity {
    RecyclerView rv;
    List<Hotel_Registraion_row> ls;
    ImageView backButton;
    HRS hrs;
    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_hotels_screen);

        rv = findViewById(R.id.rv);
        backButton = findViewById(R.id.back_button);
        searchEditText = findViewById(R.id.search_edit_text);
        ls = new ArrayList<>();
        hrs = HRS.getInstance(Registered_Hotels_Screen.this);

        String email = getIntent().getStringExtra("email");

        for (Hotel hotel : hrs.getHotels()) {
            if (hotel.getRegistered_by().equals(email)) {
                ls.add(new Hotel_Registraion_row(hotel.getName(), hotel.getLocation(),
                        hotel.getSingleRoomPrice(), hotel.getDoubleRoomPrice()));
            }
        }

        //Adapter
        Hotel_Registration_adapter adapter = new Hotel_Registration_adapter(ls,this);
        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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