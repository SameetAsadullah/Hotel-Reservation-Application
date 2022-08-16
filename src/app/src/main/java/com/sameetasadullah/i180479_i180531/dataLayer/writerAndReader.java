package com.sameetasadullah.i180479_i180531.dataLayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sameetasadullah.i180479_i180531.logicLayer.Customer;
import com.sameetasadullah.i180479_i180531.logicLayer.HRS;
import com.sameetasadullah.i180479_i180531.logicLayer.Hotel;
import com.sameetasadullah.i180479_i180531.logicLayer.Reservation;
import com.sameetasadullah.i180479_i180531.logicLayer.Room;
import com.sameetasadullah.i180479_i180531.logicLayer.Vendor;
import com.sameetasadullah.i180479_i180531.presentationLayer.MyDBHelper;
import com.sameetasadullah.i180479_i180531.presentationLayer.Reservations_Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class writerAndReader {
    Context context;
    String directoryUrl = "http://192.168.18.81/PHP_Files/";

    public writerAndReader(Context context) {
        this.context = context;
    }

    @NonNull
    private byte[] getByteArray(Uri image) {
        Bitmap pic = null;
        try {
            pic = MediaStore.Images.Media.getBitmap(context.getContentResolver(), image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void insertCustomerDataIntoServer(Customer customer, Uri dp, VolleyCallBack volleyCallBack) {
        String url = directoryUrl + "insert_image.php";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(
                Request.Method.POST,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            if (obj.getString("code").equals("1")) {
                                String imageUrl = directoryUrl + obj.getString("url");
                                customer.setDp(imageUrl);
                                insertCustomerIntoServer(customer,imageUrl, volleyCallBack);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                long imageName = System.currentTimeMillis();
                params.put("image", new DataPart(imageName + ".png", getByteArray(dp)));
                return params;
            }
        };
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }

    private void insertCustomerIntoServer(Customer customer, String imageUrl, VolleyCallBack volleyCallBack) {
        String url = directoryUrl + "insert_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleyCallBack.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "customers");
                data.put("name", customer.getName());
                data.put("email", customer.getEmail());
                data.put("password", customer.getPassword());
                data.put("phoneno", customer.getPhoneNo());
                data.put("cnic", customer.getCNIC());
                data.put("accountno", customer.getAccountNo());
                data.put("address", customer.getAddress());
                data.put("dp", imageUrl);
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    public void getCustomersFromServer(Vector<Customer> customers) {
        String url = directoryUrl + "get_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            if(object.getInt("reqcode")==1){
                                JSONArray data=object.getJSONArray("data");
                                for (int i=0;i<data.length();i++)
                                {
                                    customers.add(
                                            new Customer(
                                                    data.getJSONObject(i).getInt("id"),
                                                    data.getJSONObject(i).getString("email"),
                                                    data.getJSONObject(i).getString("password"),
                                                    data.getJSONObject(i).getString("name"),
                                                    data.getJSONObject(i).getString("address"),
                                                    data.getJSONObject(i).getString("phoneno"),
                                                    data.getJSONObject(i).getString("cnic"),
                                                    data.getJSONObject(i).getString("accountno"),
                                                    data.getJSONObject(i).getString("dp")
                                            )
                                    );
                                }
                            }
                            else {
                                Toast.makeText(context,
                                        "Failed to load data",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "customers");
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    public void insertVendorDataIntoServer(Vendor vendor, Uri dp, VolleyCallBack volleyCallBack) {
        String url = directoryUrl + "insert_image.php";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(
                Request.Method.POST,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            if (obj.getString("code").equals("1")) {
                                String imageUrl = directoryUrl + obj.getString("url");
                                vendor.setDp(imageUrl);
                                insertVendorIntoServer(vendor,
                                        imageUrl,
                                        volleyCallBack);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String, DataPart> params = new HashMap<>();
                long imageName = System.currentTimeMillis();
                params.put("image", new DataPart(imageName + ".png", getByteArray(dp)));
                return params;
            }
        };
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }

    private void insertVendorIntoServer(Vendor vendor, String imageUrl, VolleyCallBack volleyCallBack) {
        String url = directoryUrl + "insert_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleyCallBack.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "vendors");
                data.put("name", vendor.getName());
                data.put("email", vendor.getEmail());
                data.put("password", vendor.getPassword());
                data.put("phoneno", vendor.getPhoneNo());
                data.put("cnic", vendor.getCNIC());
                data.put("accountno", vendor.getAccountNo());
                data.put("address", vendor.getAddress());
                data.put("dp", imageUrl);
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    public void getVendorsFromServer(Vector<Vendor> vendors) {
        String url = directoryUrl + "get_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            if(object.getInt("reqcode")==1){
                                JSONArray data=object.getJSONArray("data");
                                for (int i=0;i<data.length();i++)
                                {
                                    vendors.add(
                                            new Vendor(
                                                    data.getJSONObject(i).getInt("id"),
                                                    data.getJSONObject(i).getString("email"),
                                                    data.getJSONObject(i).getString("password"),
                                                    data.getJSONObject(i).getString("name"),
                                                    data.getJSONObject(i).getString("address"),
                                                    data.getJSONObject(i).getString("phoneno"),
                                                    data.getJSONObject(i).getString("cnic"),
                                                    data.getJSONObject(i).getString("accountno"),
                                                    data.getJSONObject(i).getString("dp")
                                            )
                                    );
                                }
                            }
                            else {
                                Toast.makeText(context,
                                        "Failed to load data",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "vendors");
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    public void insertHotelIntoServer(Hotel hotel) {
        String url = directoryUrl + "insert_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // do nothing
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "hotels");
                data.put("name", hotel.getName());
                data.put("address", hotel.getAddress());
                data.put("location", hotel.getLocation());
                data.put("single_rooms", hotel.getSingleRooms());
                data.put("double_rooms", hotel.getDoubleRooms());
                data.put("single_room_price", hotel.getSingleRoomPrice());
                data.put("double_room_price", hotel.getDoubleRoomPrice());
                data.put("registered_by", hotel.getRegistered_by());
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }



    public void getHotelsFromServer(Vector<Hotel> hotels, VolleyCallBack volleyCallBack) {
        String url = directoryUrl + "get_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @SuppressLint("Range")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            if(object.getInt("reqcode")==1){
                                JSONArray data=object.getJSONArray("data");
                                for (int i=0;i<data.length();i++)
                                {
                                    hotels.add(
                                            new Hotel(
                                                    data.getJSONObject(i).getInt("id"),
                                                    data.getJSONObject(i).getString("name"),
                                                    data.getJSONObject(i).getString("address"),
                                                    data.getJSONObject(i).getString("location"),
                                                    data.getJSONObject(i).getString("single_rooms"),
                                                    data.getJSONObject(i).getString("double_rooms"),
                                                    data.getJSONObject(i).getString("single_room_price"),
                                                    data.getJSONObject(i).getString("double_room_price"),
                                                    data.getJSONObject(i).getString("registered_by")
                                                    )
                                    );
                                }
                                volleyCallBack.onSuccess();
                            }
                            else {
                                Toast.makeText(context,
                                        "Failed to load data fromm Server",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();

                        ///////////
                        MyDBHelper helper = new MyDBHelper(context);
                        SQLiteDatabase db = helper.getReadableDatabase();
                        String[] projection = new String[] {

                                Reservations_Store.OneRegisteration._NAME,
                                Reservations_Store.OneRegisteration._ID,
                                Reservations_Store.OneRegisteration._LOCATION,
                                Reservations_Store.OneRegisteration._ADDRESS,
                                Reservations_Store.OneRegisteration._SINGLEPRICE,
                                Reservations_Store.OneRegisteration._SINGLEROOMS,
                                Reservations_Store.OneRegisteration._DOUBLEROOMS,
                                Reservations_Store.OneRegisteration._DOUBLEPRICE,
                                Reservations_Store.OneRegisteration._REGISTEREDBY


                        };
                        String sort = Reservations_Store.OneRegisteration._ID + " ASC";
                        Cursor c =db.query(Reservations_Store.OneRegisteration.TABLENAME,projection,
                                null,
                                null,
                                null,
                                null,
                                sort
                        );

                        while(c.moveToNext())
                        {
                            int index_ID = c.getColumnIndex(Reservations_Store.OneRegisteration._ID);
                            int index_name = c.getColumnIndex(Reservations_Store.OneRegisteration._NAME);
                            int index_address = c.getColumnIndex(Reservations_Store.OneRegisteration._ADDRESS);
                            int index_location = c.getColumnIndex(Reservations_Store.OneRegisteration._LOCATION);
                            int index_singleRooms = c.getColumnIndex(Reservations_Store.OneRegisteration._SINGLEROOMS);
                            int index_doubleRooms = c.getColumnIndex(Reservations_Store.OneRegisteration._DOUBLEROOMS);
                            int index_singlePrice = c.getColumnIndex(Reservations_Store.OneRegisteration._SINGLEPRICE);
                            int index_doublePrice = c.getColumnIndex(Reservations_Store.OneRegisteration._DOUBLEPRICE);
                            int index_registeredBy = c.getColumnIndex(Reservations_Store.OneRegisteration._REGISTEREDBY);
                            hotels.add(
                                    new Hotel(
                                            c.getInt(index_ID),
                                            c.getString(index_name),
                                            c.getString(index_address),
                                            c.getString(index_location),
                                            c.getString(index_singleRooms),
                                            c.getString(index_doubleRooms),
                                            c.getString(index_singlePrice),
                                            c.getString(index_doublePrice),
                                            c.getString(index_registeredBy)
                                    )
                            );
                        }

                        volleyCallBack.onSuccess();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "hotels");
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    public void insertReservationIntoServer(Reservation reservation) {
        String url = directoryUrl + "insert_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // do nothing
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "reservations");
                data.put("hotel_name", reservation.getHotelName());
                data.put("hotel_location", reservation.getHotelLocation());
                data.put("total_rooms", reservation.getTotalRooms());
                data.put("room_numbers", reservation.getRoomNumbers());
                data.put("total_price", reservation.getTotalPrice());
                data.put("check_in_date", reservation.getCheckInDate());
                data.put("check_out_date", reservation.getCheckOutDate());
                data.put("reserved_by", reservation.getCustomerEmail());
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    public void getReservationsFromServer(Vector<Hotel> hotels) {
        String url = directoryUrl + "get_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @SuppressLint("Range")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            if(object.getInt("reqcode")==1){
                                JSONArray data=object.getJSONArray("data");
                                for (int i=0;i<data.length();i++)
                                {
                                    String hotel_name = data.getJSONObject(i).getString("hotel_name");
                                    String hotel_location = data.getJSONObject(i).getString("hotel_location");

                                    for (int j = 0; j < hotels.size(); ++j) {
                                        if (hotels.get(j).getName().equals(hotel_name) && hotels.get(j).getLocation().equals(hotel_location)) {
                                            hotels.get(j).getReservations().add(new Reservation
                                                    (
                                                            hotel_name,
                                                            hotel_location,
                                                            data.getJSONObject(i).getString("total_rooms"),
                                                            data.getJSONObject(i).getString("room_numbers"),
                                                            data.getJSONObject(i).getString("total_price"),
                                                            data.getJSONObject(i).getString("check_in_date"),
                                                            data.getJSONObject(i).getString("check_out_date"),
                                                            data.getJSONObject(i).getString("reserved_by")
                                                    )
                                            );
                                        }
                                    }
                                }
                            }
                            else {
                                Toast.makeText(context,
                                        "Failed to load data from server",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();

                        ///////////
                        MyDBHelper helper = new MyDBHelper(context);
                        SQLiteDatabase db = helper.getReadableDatabase();
                        String[] projection = new String[] {
                                Reservations_Store.OneReservation._NAME,
                                Reservations_Store.OneReservation._LOCATION,
                                Reservations_Store.OneReservation._CHECKIN,
                                Reservations_Store.OneReservation._CHECKOUT,
                                Reservations_Store.OneReservation._ROOMS,
                                Reservations_Store.OneReservation._TOTALPRICE,
                                Reservations_Store.OneReservation._TOTALROOMS,
                                Reservations_Store.OneReservation._RESERVEDBY

                        };
                        String sort = Reservations_Store.OneReservation._NAME + " ASC";
                        Cursor c =db.query(Reservations_Store.OneReservation.TABLENAME,projection,
                                null,
                                null,
                                null,
                                null,
                                sort
                        );
                        int i=0;
                        while(c.moveToNext())
                        {
                            @SuppressLint("Range") String hotel_name = c.getString(c.getColumnIndex(Reservations_Store.OneReservation._NAME));
                            @SuppressLint("Range") String hotel_location = c.getString(c.getColumnIndex(Reservations_Store.OneReservation._NAME));

                            for (int j = 0; j < hotels.size(); ++j) {
                                if (hotels.get(j).getName().equals(hotel_name) && hotels.get(j).getLocation().equals(hotel_location)) {
                                    int index_totalRooms = c.getColumnIndex(Reservations_Store.OneReservation._TOTALROOMS);
                                    int index_rooms = c.getColumnIndex(Reservations_Store.OneReservation._ROOMS);
                                    int index_totalPrice = c.getColumnIndex(Reservations_Store.OneReservation._TOTALPRICE);
                                    int index_checkIn = c.getColumnIndex(Reservations_Store.OneReservation._CHECKIN);
                                    int index_checkOut = c.getColumnIndex(Reservations_Store.OneReservation._CHECKOUT);
                                    int index_reservedBy = c.getColumnIndex(Reservations_Store.OneReservation._RESERVEDBY);
                                    hotels.get(j).getReservations().add(new Reservation
                                            (
                                                    hotel_name,
                                                    hotel_location,
                                                    c.getString(index_totalRooms),
                                                    c.getString(index_rooms),
                                                    c.getString(index_totalPrice),
                                                    c.getString(index_checkIn),
                                                    c.getString(index_checkOut),
                                                    c.getString(index_reservedBy)
                                            )
                                    );
                                }
                            }
                            i+=1;
                        }
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "reservations");
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    public void insertRoomsIntoServer(Hotel hotel) {
        String url = directoryUrl + "insert_data.php";
        for (int i = 0; i < hotel.getRooms().size(); ++i) {
            Room room = hotel.getRooms().get(i);
            StringRequest request=new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // do nothing
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,
                                    error.toString(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ){
                protected Map<String,String> getParams()
                {
                    Map<String,String> data=new HashMap<String,String>();
                    data.put("tableName", "rooms");
                    data.put("hotel_id", String.valueOf(hotel.getID()));
                    data.put("roomno", String.valueOf(room.getNumber()));
                    data.put("type", room.getType());
                    if (room.getAvailableDate() == null) {
                        data.put("available_date", "null");
                    } else {
                        data.put("available_date", room.getAvailableDate().toString());
                    }
                    data.put("is_available", String.valueOf(room.isAvailable()));
                    return data;
                }
            };
            Volley.newRequestQueue(context).add(request);
        }
    }

    public void getRoomsFromServer(Hotel hotel) {
        String url = directoryUrl + "get_data.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            if(object.getInt("reqcode")==1){
                                JSONArray data=object.getJSONArray("data");
                                Vector<Room> rooms = new Vector<>();
                                for (int i=0;i<data.length();i++)
                                {
                                    int hotel_id = data.getJSONObject(i).getInt("hotel_id");
                                    if (hotel_id == hotel.getID()) {
                                        Room r = new Room(
                                                data.getJSONObject(i).getInt("roomno"),
                                                data.getJSONObject(i).getString("type")
                                        );
                                        String date = data.getJSONObject(i).getString("available_date");
                                        if (date.equals("null")) {
                                            r.setAvailableDate(null);
                                        } else {
                                            r.setAvailableDate(LocalDate.parse(date));
                                        }
                                        r.setAvailable(Boolean.parseBoolean(data.getJSONObject(i).getString("is_available")));
                                        rooms.add(r);
                                    }
                                }
                                hotel.setRooms(rooms);
                            }
                            else {
                                Toast.makeText(context,
                                        "Failed to load data",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", "rooms");
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    public void truncateATable(String tableName, VolleyCallBack volleyCallBack) {
        String url = directoryUrl + "truncate_table.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleyCallBack.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            protected Map<String,String> getParams()
            {
                Map<String,String> data=new HashMap<String,String>();
                data.put("tableName", tableName);
                return data;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
}
