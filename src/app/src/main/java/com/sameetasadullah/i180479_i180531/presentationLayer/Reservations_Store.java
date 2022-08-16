package com.sameetasadullah.i180479_i180531.presentationLayer;

import android.provider.BaseColumns;

public class Reservations_Store {
    public static String DB_NAME="myReservations.db";
    public static int DB_VERSION=1;

    public static class OneReservation implements BaseColumns {
        public static String TABLENAME="ReservationsTable";
        public static String _NAME="name";
        public static String _LOCATION="location";
        public static String _CHECKIN ="checkin";
        public static String _CHECKOUT ="checkout";
        public static String _TOTALROOMS ="total_rooms";
        public static String _TOTALPRICE ="total_price";
        public static String _ROOMS ="rooms";
        public static String _RESERVEDBY ="reserved_by";
    }

    public static class OneRegisteration implements BaseColumns {
        public static String TABLENAME="RegisteredHotelTable";
        public static String _NAME="name";
        public static String _ADDRESS="address";
        public static String _LOCATION ="location";
        public static String _SINGLEROOMS ="single_rooms";
        public static String _DOUBLEROOMS ="double_rooms";
        public static String _SINGLEPRICE ="single_room_price";
        public static String _DOUBLEPRICE ="double_room_price";
        public static String _REGISTEREDBY ="registered_by";
    }



}
