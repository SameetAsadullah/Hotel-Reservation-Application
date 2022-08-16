package com.sameetasadullah.i180479_i180531.presentationLayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    String CREATE_RESERVATION_TABLE="CREATE TABLE " +
            Reservations_Store.OneReservation.TABLENAME + "("+
            Reservations_Store.OneReservation._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            Reservations_Store.OneReservation._NAME + " TEXT NOT NULL, "+
            Reservations_Store.OneReservation._LOCATION + " TEXT , "+
            Reservations_Store.OneReservation._TOTALROOMS + " TEXT , "+
            Reservations_Store.OneReservation._TOTALPRICE + " TEXT , "+
            Reservations_Store.OneReservation._RESERVEDBY + " TEXT , "+
            Reservations_Store.OneReservation._CHECKIN + " TEXT ," +
            Reservations_Store.OneReservation._ROOMS + " TEXT ,"+
            Reservations_Store.OneReservation._CHECKOUT + " TEXT );";

    String DELETE_RESERVATION_TABLE="DROP TABLE IF EXISTS "+Reservations_Store.OneReservation.TABLENAME;


    String CREATE_REGISTRATION_TABLE="CREATE TABLE " +
            Reservations_Store.OneRegisteration.TABLENAME + "("+
            Reservations_Store.OneRegisteration._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            Reservations_Store.OneRegisteration._NAME + " TEXT NOT NULL, "+
            Reservations_Store.OneRegisteration._LOCATION + " TEXT , "+
            Reservations_Store.OneRegisteration._ADDRESS + " TEXT , "+
            Reservations_Store.OneRegisteration._SINGLEROOMS + " TEXT , "+
            Reservations_Store.OneRegisteration._SINGLEPRICE + " TEXT , "+
            Reservations_Store.OneRegisteration._DOUBLEPRICE + " TEXT ," +
            Reservations_Store.OneRegisteration._DOUBLEROOMS + " TEXT ,"+
            Reservations_Store.OneRegisteration._REGISTEREDBY + " TEXT );";

    String DELETE_REGISTRATION_TABLE="DROP TABLE IF EXISTS "+Reservations_Store.OneRegisteration.TABLENAME;



    public MyDBHelper(@Nullable Context context) {
        super(context, Reservations_Store.DB_NAME,null,Reservations_Store.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RESERVATION_TABLE);
        sqLiteDatabase.execSQL(CREATE_REGISTRATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_RESERVATION_TABLE);
        sqLiteDatabase.execSQL(DELETE_REGISTRATION_TABLE);
        onCreate(sqLiteDatabase);
    }
}
