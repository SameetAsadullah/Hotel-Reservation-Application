package com.sameetasadullah.i180479_i180531.logicLayer;

import java.time.LocalDate;

public class Room {
    private int number;
    private String type;
    private LocalDate availableDate;
    private boolean available;

    //constructor
    public Room() {}
    public Room(int no, String Type) {
        //assigning values to data members
        number = no;
        type = Type;
        available = true;
        availableDate = null;
    }

    //getters
    public int getNumber() {
        return number;
    }
    public String getType() {
        return type;
    }
    public boolean isAvailable() {
        return available;
    }
    public LocalDate getAvailableDate() { return availableDate; }

    //setters
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setAvailableDate(LocalDate availableDate) { this.availableDate = availableDate; }
}

