package com.lzf.letscook.entity;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class Material {

    private String title;

    private String note;

    private String image;

    public Material(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public Material(String title, String note, String image) {
        this.title = title;
        this.note = note;
        this.image = image;
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

    @Override
    public String toString() {
        return "Material{" +
                "title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
