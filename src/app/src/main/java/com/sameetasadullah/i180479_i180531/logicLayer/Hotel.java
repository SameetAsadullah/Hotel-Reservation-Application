package com.sameetasadullah.i180479_i180531.logicLayer;

import com.sameetasadullah.i180479_i180531.dataLayer.writerAndReader;

import java.time.LocalDate;
import java.util.*;

public class Hotel {
    private int ID, totalRooms;
    private String singleRooms, doubleRooms, singleRoomPrice, doubleRoomPrice, name, address, location, registered_by;
    private Vector<Room> rooms;
    private Vector<Reservation> reservations;

    //constructors
    public Hotel() {
    }
    public Hotel(int id, String Name, String add, String loc, String sRooms, String dRooms,
                 String sRoomPrice, String dRoomPrice, String registered_by) {
        //assigning values to data members
        ID = id;
        name = Name;
        address = add;
        totalRooms = Integer.parseInt(sRooms) + Integer.parseInt(dRooms);
        singleRooms = sRooms;
        doubleRooms = dRooms;
        location = loc;
        singleRoomPrice = sRoomPrice;
        doubleRoomPrice = dRoomPrice;
        this.registered_by = registered_by;
        reservations = new Vector<>();

        //making rooms in hotel
        rooms = new Vector<>();
        for (int i = 0; i < totalRooms; ++i) {
            Room r1;
            if (i < Integer.parseInt(singleRooms)) {
                r1 = new Room(i + 1, "Single");
            } else {
                r1 = new Room(i + 1, "Double");
            }
            rooms.add(r1);
        }
    }

    //getters
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public int getID() {
        return ID;
    }
    public int getTotalRooms() {
        return totalRooms;
    }
    public Vector<Room> getRooms() {
        return rooms;
    }
    public String getLocation() {
        return location;
    }
    public String getDoubleRooms() {
        return doubleRooms;
    }
    public String getSingleRooms() {
        return singleRooms;
    }
    public String getSingleRoomPrice() {
        return singleRoomPrice;
    }
    public Vector<Reservation> getReservations() {
        return reservations;
    }
    public String getDoubleRoomPrice() {
        return doubleRoomPrice;
    }
    public String getRegistered_by() { return registered_by; }

    //setters
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setRooms(Vector<Room> rooms) {
        this.rooms = rooms;
    }
    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setDoubleRooms(String doubleRooms) {
        this.doubleRooms = doubleRooms;
    }
    public void setReservations(Vector<Reservation> reservations) { this.reservations = reservations; }
    public void setSingleRooms(String singleRooms) {
        this.singleRooms = singleRooms;
    }
    public void setSingleRoomPrice(String singleRoomPrice) { this.singleRoomPrice = singleRoomPrice; }
    public void setDoubleRoomPrice(String doubleRoomPrice) { this.doubleRoomPrice = doubleRoomPrice; }
    public void setRegistered_by(String registered_by) { this.registered_by = registered_by; }

    //return rooms of hotel which can accommodate user requirements
    public Vector<Room> getRooms(String noOfPersons, LocalDate checkInDate, String roomType, Boolean both) {
        int personsCount = 0;
        Vector<Room> searchedRooms = new Vector<>();

        for (int i = 0; i < totalRooms; ++i) {
            if (rooms.get(i).isAvailable() || checkInDate.isEqual(rooms.get(i).getAvailableDate()) ||
                    checkInDate.isAfter(rooms.get(i).getAvailableDate())) {
                if (both == true) {
                    if (rooms.get(i).getType().equals("Single")) {
                        personsCount += 1;
                    } else {
                        personsCount += 2;
                    }
                    searchedRooms.add(rooms.get(i));
                }
                else {
                    if (rooms.get(i).getType().equals("Single") && roomType.equals("Single")) {
                        personsCount += 1;
                        searchedRooms.add(rooms.get(i));
                    } else if (rooms.get(i).getType().equals("Double") && roomType.equals("Double")){
                        personsCount += 2;
                        searchedRooms.add(rooms.get(i));
                    }
                }
            }

            if (personsCount >= Integer.parseInt(noOfPersons)) {
                return searchedRooms;
            }
        }
        return null;
    }

    //function for reserving room in a hotel
    public Reservation reserveRoom(LocalDate checkInDate, LocalDate checkOutDate, Customer c, Vector<Hotel> hotels) {
        int temp = 0;
        for (int i = 0; i < rooms.size(); ++i) {
            rooms.get(i).setAvailable(false);
            rooms.get(i).setAvailableDate(checkOutDate.plusDays(1));
        }
        for (int i = 0; i < hotels.size(); ++i) {
            if (hotels.get(i).getID() == ID) {
                for (int j = 0; j < hotels.get(i).getRooms().size(); ++j) {
                    if (hotels.get(i).getRooms().get(j).getNumber() == rooms.get(temp).getNumber()) {
                        hotels.get(i).getRooms().get(j).setAvailable(rooms.get(temp).isAvailable());
                        hotels.get(i).getRooms().get(j).setAvailableDate(rooms.get(temp).getAvailableDate());
                        if ((temp + 1) != rooms.size()) {
                            temp++;
                        }
                    }
                }

                String roomNumbers = "";
                for (int j = 0; j < rooms.size(); ++j) {
                    roomNumbers += rooms.get(j).getNumber();
                    if (j != rooms.size() - 1) {
                        roomNumbers += ", ";
                    }
                }

                int totalPriceCal=0;
                for (int j=0;j<rooms.size();j++){
                    if (rooms.get(j).getType().equals("Single")){
                        totalPriceCal= totalPriceCal + Integer.parseInt(singleRoomPrice);
                    }
                    else{
                        totalPriceCal= totalPriceCal + Integer.parseInt(doubleRoomPrice);
                    }
                }

                Hotel hotel = hotels.get(i);
                Reservation r1 = new Reservation(hotel.getName(), hotel.getLocation(),
                        Integer.toString(rooms.size()), roomNumbers, Integer.toString(totalPriceCal),
                        checkInDate.toString(), checkOutDate.toString(), c.getEmail());
                hotels.get(i).getReservations().add(r1);
                return r1;
            }
        }
        return null;
    }
}
