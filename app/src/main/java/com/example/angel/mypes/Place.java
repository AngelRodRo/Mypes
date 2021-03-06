package com.example.angel.mypes;

import android.graphics.Bitmap;

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
    private ArrayList<Comentario> comments;
    private String score;
    private String description;
    private ArrayList<String> photos;
    private Bitmap foto;

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

    public Place(String _id,Bitmap _foto,String _name,String _address,String _phone,LatLng _position,String _category)
    {
        id=_id;
        name=_name;
        address=_address;
        position = _position;
        phone=_phone;
        category=_category;
        foto = _foto;
    }


    public Place(String _name,String _address,String _phone,LatLng _position,String _category,ArrayList<Comentario> _comments,String _description)
    {
        name=_name;
        address=_address;
        position = _position;
        phone=_phone;
        category=_category;
        comments = _comments;
        description = _description;

    }

    public Place(String _name,String _address,String _phone,LatLng _position,String _category,ArrayList<Comentario> _comments,String _score,String _description)
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

    public Place(String _name,Bitmap _foto,String _address,String _phone,LatLng _position,String _category,ArrayList<Comentario> _comments,String _score,String _description,ArrayList<String> _photos)
    {
        name=_name;
        address=_address;
        position = _position;
        phone=_phone;
        category=_category;
        comments = _comments;
        score = _score;
        description = _description;
        photos = _photos;
        foto = _foto;
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


    public void setComments(ArrayList<Comentario> comments) {
        this.comments = comments;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
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

    public ArrayList<Comentario> getComments() {
        return comments;
    }

    public String getScore() {
        return score;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public Bitmap getFoto() {
        return foto;
    }
}
