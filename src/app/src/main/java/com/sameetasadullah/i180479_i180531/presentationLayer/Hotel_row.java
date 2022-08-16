package com.sameetasadullah.i180479_i180531.presentationLayer;

public class Hotel_row {

    private String name,singlePrice,doublePrice,location, check_out_date;
    //private int hotelImage;
    public Hotel_row(String name,String location,String singlePrice,String doublePrice, String check_out_date) {
        this.name = name;
        this.location= location;
        this.singlePrice=singlePrice;
        this.doublePrice=doublePrice;
        this.check_out_date = check_out_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
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
