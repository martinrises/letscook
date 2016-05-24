package com.lzf.letscook.entity;

import java.io.Serializable;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class CookStep implements Serializable{

    private String position = "";
    private String content = "";
    private String thumb = "";
    private String image = "";

    public CookStep(String position, String content) {
        this.position = position;
        this.content = content;
    }

    public CookStep(String position, String content, String thumb, String image) {
        this.position = position;
        this.content = content;
        this.thumb = thumb;
        this.image = image;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CookStep{" +
                "position='" + position + '\'' +
                ", content='" + content + '\'' +
                ", thumb='" + thumb + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
