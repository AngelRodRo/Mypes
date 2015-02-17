package com.example.angel.mypes;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by angel on 09/02/15.
 */
public class Place {

    private String id;
    private String name;
    private String address;
    private String phone;
    private LatLng position;
    private String category;


    public Place(String _id,String _name,String _address,String _phone,LatLng _position,String _category)
    {
        id=_id;
        name=_name;
        address=_address;
        position = _position;
        phone=_phone;
        category=_category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getCategory() {
        return category;
    }

    public LatLng getPosition() {
        return position;
    }
}
