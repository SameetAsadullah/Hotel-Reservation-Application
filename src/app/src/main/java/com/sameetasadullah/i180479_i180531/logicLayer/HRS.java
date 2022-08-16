package com.sameetasadullah.i180479_i180531.logicLayer;

import android.content.Context;
import android.net.Uri;

import com.sameetasadullah.i180479_i180531.dataLayer.VolleyCallBack;
import com.sameetasadullah.i180479_i180531.dataLayer.writerAndReader;

import java.time.LocalDate;
import java.util.List;
import java.util.Vector;
import java.io.*;

public class HRS {
    private Vector<Customer> customers;
    private Vector<Hotel> hotels;
    private Vector<Vendor> vendors;
    private writerAndReader readAndWrite;
    private static HRS hrs;

    // constructor
    private HRS(Context context) {
        // initializing data members
        customers = new Vector<>();
        hotels = new Vector<>();
        vendors = new Vector<>();
        readAndWrite = new writerAndReader(context);

        // fetching old data from server
        readAndWrite.getCustomersFromServer(customers);
        readAndWrite.getVendorsFromServer(vendors);
        readAndWrite.getHotelsFromServer(hotels, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                for (int i = 0; i < hotels.size(); ++i) {
                    readAndWrite.getRoomsFromServer(hotels.get(i));
                }
                readAndWrite.getReservationsFromServer(hotels);
            }
        });
    }

    // getters
    public static HRS getInstance(Context context) {
        if (hrs == null) {
            hrs = new HRS(context);
        }
        return hrs;
    }
    public Vector<Customer> getCustomers() {
        return customers;
    }
    public Vector<Hotel> getHotels() {
        return hotels;
    }
    public Vector<Vendor> getVendors() {
        return vendors;
    }
    public writerAndReader getReadAndWrite() {
        return readAndWrite;
    }

    // setters
    public void setCustomers(Vector<Customer> customers) {
        this.customers = customers;
    }
    public void setHotels(Vector<Hotel> hotels) {
        this.hotels = hotels;
    }
    public void setReadAndWrite(writerAndReader readAndWrite) {
        this.readAndWrite = readAndWrite;
    }
    public void setVendors(Vector<Vendor> vendors) { this.vendors = vendors; }

    // function to check if customer with same email already exists or not
    public boolean validateCustomerEmail(String email) {
        for (int i = 0; i < customers.size(); ++i) {
            if (email.equals(customers.get(i).getEmail())) {
                return false;
            }
        }
        return true;
    }

    // function to check if customer has logged in correctly or not
    public boolean validateCustomerAccount(String email, String pass) {
        for (int i = 0; i < customers.size(); ++i) {
            if (email.equals(customers.get(i).getEmail()) && customers.get(i).getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    // function to check if vendor with same email already exists or not
    public boolean validateVendorEmail(String email) {
        for (int i = 0; i < vendors.size(); ++i) {
            if (email.equals(vendors.get(i).getEmail())) {
                return false;
            }
        }
        return true;
    }

    // function to check if vendor has logged in correctly or not
    public boolean validateVendorAccount(String email, String pass) {
        for (int i = 0; i < vendors.size(); ++i) {
            if (email.equals(vendors.get(i).getEmail()) && vendors.get(i).getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

    // function to check if hotel with same name and location already exists or not
    public boolean validateHotel(String name, String loc) {
        for (int i = 0; i < hotels.size(); ++i) {
            if (name.equals(hotels.get(i).getName()) && loc.equals(hotels.get(i).getLocation())) {
                return false;
            }
        }
        return true;
    }

    // function for customer registration
    public void registerCustomer(String name, String email, String pass,
                                 String add, String phone, String cnic, String accNo, Uri dp,
                                 VolleyCallBack volleyCallBack) {
        int ID = 0;

        //getting maximum ID
        for (int i = 0; i < customers.size(); ++i) {
            if (customers.get(i).getID() > ID) {
                ID = customers.get(i).getID();
            }
        }
        ID++;

        //registering customer
        Customer c = new Customer(ID, email, pass, name, add, phone, cnic, accNo, "");
        readAndWrite.insertCustomerDataIntoServer(c, dp, volleyCallBack);
        customers.add(c);
    }

    //function for vendor registration
    public void registerVendor(String name, String email, String pass,
                               String add, String phone, String cnic, String accNo, Uri dp,
                               VolleyCallBack volleyCallBack) {
        int ID = 0;

        //getting maximum ID
        for (int i = 0; i < vendors.size(); ++i) {
            if (vendors.get(i).getID() > ID) {
                ID = vendors.get(i).getID();
            }
        }
        ID++;

        //registering vendor
        Vendor v = new Vendor(ID, email, pass, name, add, phone, cnic, accNo, "");
        readAndWrite.insertVendorDataIntoServer(v, dp, volleyCallBack);
        vendors.add(v);
    }

    //function for hotel registration
    public void registerHotel(String name, String add, String loc, String singleRooms, String doubleRooms,
                              String singleRoomPrice, String doubleRoomPrice, String registered_by) {
        int ID = 0;

        //getting maximum ID
        for (int i = 0; i < hotels.size(); ++i) {
            if (hotels.get(i).getID() > ID) {
                ID = hotels.get(i).getID();
            }
        }
        ID++;

        //registering hotel
        Hotel h = new Hotel(ID, name, add, loc, singleRooms, doubleRooms, singleRoomPrice, doubleRoomPrice, registered_by);
        hotels.add(h);
        readAndWrite.insertHotelIntoServer(h);
        readAndWrite.insertRoomsIntoServer(h);
    }

    //function for hotel booking
    public Vector<Hotel> getHotels(String location, String noOfPersons, LocalDate checkInDate, String roomType, boolean both) {
        Vector<Hotel> searchedHotels = new Vector<>();
        for (int i = 0; i < hotels.size(); ++i) {
            if (hotels.get(i).getLocation().equals(location)) {
                Hotel h1 = new Hotel();
                h1.setAddress(hotels.get(i).getAddress());
                h1.setName(hotels.get(i).getName());
                h1.setID(hotels.get(i).getID());
                h1.setRooms(hotels.get(i).getRooms());
                h1.setTotalRooms(hotels.get(i).getTotalRooms());
                h1.setLocation(hotels.get(i).getLocation());
                h1.setDoubleRoomPrice(hotels.get(i).getDoubleRoomPrice());
                h1.setDoubleRooms(hotels.get(i).getDoubleRooms());
                h1.setSingleRooms(hotels.get(i).getSingleRooms());
                h1.setSingleRoomPrice(hotels.get(i).getSingleRoomPrice());
                h1.setReservations(hotels.get(i).getReservations());
                Vector<Room> r;
                r = hotels.get(i).getRooms(noOfPersons, checkInDate, roomType, both);
                if (r != null) {
                    h1.setRooms(r);
                    searchedHotels.add(h1);
                }
            }
        }
        return searchedHotels;
    }

    //function for reserving room
    public void makeReservation(String email, Hotel h, LocalDate checkInDate, LocalDate checkOutDate) {
        //finding customer and calling for reservation
        for (int i = 0; i < customers.size(); ++i) {
            if (customers.get(i).getEmail().equals(email)) {
                Reservation reservation = h.reserveRoom(checkInDate, checkOutDate, customers.get(i), hotels);
                readAndWrite.truncateATable("rooms", new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        for (int j = 0; j < hotels.size(); ++j) {
                            readAndWrite.insertRoomsIntoServer(hotels.get(j));
                        }
                    }
                });
                if (reservation != null) {
                    readAndWrite.insertReservationIntoServer(reservation);
                }
                break;
            }
        }
    }

    // Search Customer On Email Basis
    public Customer searchCustomerByMail(String Email){
        for(int i=0;i<customers.size();++i){
            if(Email.equals(customers.get(i).getEmail())){
                return customers.get(i);
            }
        }
        return null;
    }
    // Search Vendor On Email Basis
    public Vendor searchVendorByMail(String Email){
        for(int i=0;i<vendors.size();++i){
            if(Email.equals(vendors.get(i).getEmail())){
                return vendors.get(i);
            }
        }
        return null;
    }
    // Search Hotel On Name and Location Basis
    public Hotel searchHotelByNameLoc(String Name,String Loc){
        for(int i=0;i<hotels.size();++i){
            if(Name.equals(hotels.get(i).getName()) && Loc.equals(hotels.get(i).getLocation())){
                return hotels.get(i);
            }
        }
        return null;
    }

    // Login Customer On Email and Password Basis
    public Boolean LoginCustomer(String Email,String Pass){
        for(int i=0;i<customers.size();++i){
            if(Email.equals(customers.get(i).getEmail()) &&  Pass.equals(customers.get(i).getPassword())){
                return true;
            }
        }
        return false;
    }
    // Login Vendor On Email and Password Basis
    public Boolean LoginVendor(String Email,String Pass){
        for(int i=0;i<vendors.size();++i){
            if(Email.equals(vendors.get(i).getEmail()) &&  Pass.equals(vendors.get(i).getPassword())){
                return true;
            }
        }
        return false;
    }
}
