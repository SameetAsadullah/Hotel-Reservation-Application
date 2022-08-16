package com.sameetasadullah.i180479_i180531.presentationLayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sameetasadullah.i180479_i180531.R;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;
import com.sameetasadullah.i180479_i180531.logicLayer.Hotel;
import com.sameetasadullah.i180479_i180531.logicLayer.Reservation;
import com.sameetasadullah.i180479_i180531.logicLayer.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class Hotel_Reservation_Screen extends AppCompatActivity {

    RelativeLayout endButton;
    TextView hotelName,rooms,totalPrice,totalRooms;
    HRS hrs;
    String Email,checkInDate,checkOutDate,HotelName,HotelLocation;
    Hotel h1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservation_screen);

        hotelName = findViewById(R.id.tv_hotel_name);
        rooms = findViewById(R.id.tv_rooms);
        totalPrice = findViewById(R.id.tv_total_price);
        totalRooms = findViewById(R.id.tv_total_rooms);
        endButton = findViewById(R.id.END_button);
        hrs = HRS.getInstance(Hotel_Reservation_Screen.this);

        Email = getIntent().getStringExtra("Email");
        HotelName = getIntent().getStringExtra("Hotel_name");
        HotelLocation = getIntent().getStringExtra("Hotel_Loc");
        checkInDate = getIntent().getStringExtra("checkinDate");
        checkOutDate = getIntent().getStringExtra("checkOutDate");


        h1 = hrs.searchHotelByNameLoc(HotelName,HotelLocation);

        Vector<Reservation> res= h1.getReservations();

        Reservation reservation=res.get(res.size() - 1);
        hotelName.setText(h1.getName());
        totalRooms.setText(reservation.getTotalRooms());
        totalPrice.setText(reservation.getTotalPrice());
        rooms.setText(reservation.getRoomNumbers());

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Hotel_Reservation_Screen.this,Customer_Choose_Option_Screen.class);
                intent.putExtra("email",Email);

                //SQL LITE
                MyDBHelper helper = new MyDBHelper(Hotel_Reservation_Screen.this);
                SQLiteDatabase database = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(Reservations_Store.OneReservation._NAME,HotelName);
                cv.put(Reservations_Store.OneReservation._LOCATION,HotelLocation);
                cv.put(Reservations_Store.OneReservation._CHECKIN,checkInDate);
                cv.put(Reservations_Store.OneReservation._CHECKOUT,checkOutDate);
                cv.put(Reservations_Store.OneReservation._TOTALPRICE,String.valueOf(totalPrice));
                cv.put(Reservations_Store.OneReservation._TOTALROOMS,String.valueOf(totalPrice));
                cv.put(Reservations_Store.OneReservation._ROOMS,reservation.getRoomNumbers());
                cv.put(Reservations_Store.OneReservation._RESERVEDBY,Email);
                double tep = database.insert(Reservations_Store.OneReservation.TABLENAME,null,cv);
                database.close();
                helper.close();

                startActivity(intent);
                finish();
            }
        });
    }
}