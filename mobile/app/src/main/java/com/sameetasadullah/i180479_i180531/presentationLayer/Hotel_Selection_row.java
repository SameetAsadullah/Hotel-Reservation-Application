package com.sameetasadullah.i180479_i180531.presentationLayer;

import android.widget.ImageView;

public class Hotel_Selection_row {

    private String name,singlePrice,doublePrice,location;
    //private int hotelImage;
    public Hotel_Selection_row(String name,String location,String singlePrice,String doublePrice) {
        this.name = name;
        this.location= location;
        this.singlePrice=singlePrice;
        this.doublePrice=doublePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSinglePrice() {return singlePrice;}
    public String getDoublePrice() {return doublePrice;}

    public void setSinglePrice(String sp){this.singlePrice=sp;}
    public void setDoublePrice(String dp){this.doublePrice=dp;}

    public String getLocation(){return location;}
    public void setLocation(String location){
        this.location=location;
    }
}
