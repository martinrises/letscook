package com.lzf.letscook.entity;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class User {

    private String user_id = "";

    private String nick = "";

    private String nickname = "";

    private String user_photo = "";

    private String user_large_photo = "";

    private String avatar_medium = "";

    private int gender; // 1: male 2:female

    private String email = "";

    private String mobile = "";

    private String user_cover = "";

    private int verified;

    private String sign = "";

    private String weibo_nick = "";

    private String qq_weibo_nick = "";

    private String qzone_nick = "";

    private int setted_email;

    private int followers_count;

    private int following_count;

    private int location;

    private int diaries_count; // 日记数量

    private int recipes_count;

    private int favorites_count;

    private int favor_diaries_count;

    public User(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getUser_large_photo() {
        return user_large_photo;
    }

    public void setUser_large_photo(String user_large_photo) {
        this.user_large_photo = user_large_photo;
    }

    public String getAvatar_medium() {
        return avatar_medium;
    }

    public void setAvatar_medium(String avatar_medium) {
        this.avatar_medium = avatar_medium;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_cover() {
        return user_cover;
    }

    public void setUser_cover(String user_cover) {
        this.user_cover = user_cover;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getWeibo_nick() {
        return weibo_nick;
    }

    public void setWeibo_nick(String weibo_nick) {
        this.weibo_nick = weibo_nick;
    }

    public String getQq_weibo_nick() {
        return qq_weibo_nick;
    }

    public void setQq_weibo_nick(String qq_weibo_nick) {
        this.qq_weibo_nick = qq_weibo_nick;
    }

    public String getQzone_nick() {
        return qzone_nick;
    }

    public void setQzone_nick(String qzone_nick) {
        this.qzone_nick = qzone_nick;
    }

    public int getSetted_email() {
        return setted_email;
    }

    public void setSetted_email(int setted_email) {
        this.setted_email = setted_email;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getDiaries_count() {
        return diaries_count;
    }

    public void setDiaries_count(int diaries_count) {
        this.diaries_count = diaries_count;
    }

    public int getRecipes_count() {
        return recipes_count;
    }

    public void setRecipes_count(int recipes_count) {
        this.recipes_count = recipes_count;
    }

    public int getFavorites_count() {
        return favorites_count;
    }

    public void setFavorites_count(int favorites_count) {
        this.favorites_count = favorites_count;
    }

    public int getFavor_diaries_count() {
        return favor_diaries_count;
    }

    public void setFavor_diaries_count(int favor_diaries_count) {
        this.favor_diaries_count = favor_diaries_count;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", nick='" + nick + '\'' +
                ", nickname='" + nickname + '\'' +
                ", user_photo='" + user_photo + '\'' +
                ", user_large_photo='" + user_large_photo + '\'' +
                ", avatar_medium='" + avatar_medium + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", user_cover='" + user_cover + '\'' +
                ", verified=" + verified +
                ", sign='" + sign + '\'' +
                ", weibo_nick='" + weibo_nick + '\'' +
                ", qq_weibo_nick='" + qq_weibo_nick + '\'' +
                ", qzone_nick='" + qzone_nick + '\'' +
                ", setted_email=" + setted_email +
                ", followers_count=" + followers_count +
                ", following_count=" + following_count +
                ", location=" + location +
                ", diaries_count=" + diaries_count +
                ", recipes_count=" + recipes_count +
                ", favorites_count=" + favorites_count +
                ", favor_diaries_count=" + favor_diaries_count +
                '}';
    }
}
