package com.lzf.letscook.entity;

import java.io.Serializable;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class Material implements Serializable{

    private String title;

    private String note;

    private String image;

    private boolean isMajor;

    private boolean isBuyed;

    private String _id;

    public Material(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public Material(String _id, String title, String note) {
        this.title = title;
        this.note = note;
        this._id = _id;
    }

    public Material(String _id, String title, String note, String image) {
        this.title = title;
        this.note = note;
        this.image = image;
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isMajor() {
        return isMajor;
    }

    public void setMajor(boolean major) {
        isMajor = major;
    }

    public boolean isBuyed() {
        return isBuyed;
    }

    public void setBuyed(boolean buyed) {
        isBuyed = buyed;
    }

    public void set_id(String _id){
        this._id = _id;
    }

    public String get_id(){
        return this._id;
    }
}
