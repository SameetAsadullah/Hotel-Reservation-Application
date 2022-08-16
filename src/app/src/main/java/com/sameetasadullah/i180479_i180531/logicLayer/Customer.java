package com.sameetasadullah.i180479_i180531.logicLayer;

public class Customer {
    private String name, address, email, password, phoneNo, CNIC, accountNo, dp;
    private int ID;

    //constructors
    public Customer() {
    }
    public Customer(int id, String mail, String pass, String Name,
                    String add, String phone, String cnic, String accNo, String dp) {
        ID = id;
        name = Name;
        address = add;
        phoneNo = phone;
        CNIC = cnic;
        accountNo = accNo;
        email = mail;
        password = pass;
        this.dp = dp;
    }

    //getters
    public int getID() {
        return ID;
    }
    public String getEmail() {
        return email;
    }
    public String getAccountNo() {
        return accountNo;
    }
    public String getCNIC() {
        return CNIC;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getDp() { return dp; }

    //setters
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDp(String dp) { this.dp = dp; }
}
