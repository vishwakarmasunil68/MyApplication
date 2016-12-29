package com.example.emobi5.myapplication;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;

import java.util.ArrayList;

/**
 * Created by emobi5 on 4/15/2016.
 */
public class InfoApps {
    String trans_type;
    String account_user_id;
    String account_user_id_sender;

    public String getService_fees() {
        return service_fees;
    }

    public void setService_fees(String service_fees) {
        this.service_fees = service_fees;
    }

    String service_fees;

    public void setAccount_user_id(String account_user_id) {
        this.account_user_id = account_user_id;
    }

    public String getAccount_user_id() {
        return account_user_id;
    }

    public String getAccount_user_id_sender() {
        return account_user_id_sender;
    }

    public void setAccount_user_id_sender(String account_user_id_sender) {
        this.account_user_id_sender = account_user_id_sender;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public Bitmap getImage() {
        return image;
    }
    String refer;
    public void setRefer(String refer){
        this.refer=refer;
    }

    public String getRefer() {
        return refer;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap image;
    public String getDataAdd() {
        return DataAdd;
    }

    public void setDataAdd(String dataAdd) {
        DataAdd = dataAdd;
    }

    private String DataAdd;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String number;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean selected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int time;
    public String getAppname() {
        return Appname;
    }

    public void setAppname(String appname) {
        Appname = appname;
    }

    private String Appname;

    public Bitmap getAppbmp() {
        return Appbmp;
    }

    public void setAppbmp(Bitmap appbmp) {
        Appbmp = appbmp;
    }

    public Bitmap Appbmp;

    public StringBuilder getData() {
        return data;
    }

    public void setData(StringBuilder data) {
        this.data = data;
    }

    private StringBuilder data;

    public Address getDataadd() {
        return dataadd;
    }

    public void setDataadd(Address dataadd) {
        this.dataadd = dataadd;
    }

    private Address dataadd;

    public ArrayList<Address> getArraylisAddr() {
        return arraylisAddr;
    }

    public void setArraylisAddr(ArrayList<Address> arraylisAddr) {
        this.arraylisAddr = arraylisAddr;
    }

    private ArrayList<Address> arraylisAddr;
    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    private Drawable appicon;

    public String getPackagenameApps() {
        return PackagenameApps;
    }

    public void setPackagenameApps(String packagenameApps) {
        PackagenameApps = packagenameApps;
    }

    private String PackagenameApps;
}
