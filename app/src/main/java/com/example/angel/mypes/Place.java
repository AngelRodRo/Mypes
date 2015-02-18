package com.example.angel.mypes;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

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
    private ArrayList<ArrayList<String>> comments;
    private String score;
    private String description;

    public Place(){}


    public Place(String _id,String _name,String _address,String _phone,LatLng _position,String _category)
    {
        id=_id;
        name=_name;
        address=_address;
        position = _position;
        phone=_phone;
        category=_category;
    }

    public Place(String _name,String _address,String _phone,LatLng _position,String _category,ArrayList<ArrayList<String>> _comments,String _description)
    {
        name=_name;
        address=_address;
        position = _position;
        phone=_phone;
        category=_category;
        comments = _comments;
        description = _description;
    }

    public Place(String _name,String _address,String _phone,LatLng _position,String _category,ArrayList<ArrayList<String>> _comments,String _score,String _description)
    {
        name=_name;
        address=_address;
        position = _position;
        phone=_phone;
        category=_category;
        comments = _comments;
        score = _score;
        description = _description;
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

    public ArrayList<ArrayList<String>> getComments() {
        return comments;
    }

    public String getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }
}
