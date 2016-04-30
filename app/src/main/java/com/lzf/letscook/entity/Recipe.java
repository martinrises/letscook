package com.lzf.letscook.entity;

import java.util.List;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class Recipe {

    private String cook_id = "";
    private String title = ""; // 食谱标题
    private String image = ""; // 食谱 小图
    private String thumb_path = ""; // 食谱 中图
    private String photo_path = ""; // 食谱 大图

    private List<String> tags;

    private String author_id = "";

    private String tips = "";

    private String cookstory = "";

    private List<CookStep> steps;

    private String create_time = "";

    private int recommended; // 推荐数

    private String act_des = "";

    private String v_u = "";

    private User user; // 作者

    private String author = ""; // 作者nickname

    private String author_photo = "";

    private int author_verified;

    private int collect_status;

    private int favo_counts;

    private int comments_count;

    private int dish_count;

    public Recipe(String cook_id) {
        this.cook_id = cook_id;
    }

    public String getCook_id() {
        return cook_id;
    }

    public void setCook_id(String cook_id) {
        this.cook_id = cook_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb_path() {
        return thumb_path;
    }

    public void setThumb_path(String thumb_path) {
        this.thumb_path = thumb_path;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getCookstory() {
        return cookstory;
    }

    public void setCookstory(String cookstory) {
        this.cookstory = cookstory;
    }

    public List<CookStep> getSteps() {
        return steps;
    }

    public void setSteps(List<CookStep> steps) {
        this.steps = steps;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getRecommended() {
        return recommended;
    }

    public void setRecommended(int recommended) {
        this.recommended = recommended;
    }

    public String getAct_des() {
        return act_des;
    }

    public void setAct_des(String act_des) {
        this.act_des = act_des;
    }

    public String getV_u() {
        return v_u;
    }

    public void setV_u(String v_u) {
        this.v_u = v_u;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_photo() {
        return author_photo;
    }

    public void setAuthor_photo(String author_photo) {
        this.author_photo = author_photo;
    }

    public int getAuthor_verified() {
        return author_verified;
    }

    public void setAuthor_verified(int author_verified) {
        this.author_verified = author_verified;
    }

    public int getCollect_status() {
        return collect_status;
    }

    public void setCollect_status(int collect_status) {
        this.collect_status = collect_status;
    }

    public int getFavo_counts() {
        return favo_counts;
    }

    public void setFavo_counts(int favo_counts) {
        this.favo_counts = favo_counts;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getDish_count() {
        return dish_count;
    }

    public void setDish_count(int dish_count) {
        this.dish_count = dish_count;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "cook_id='" + cook_id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", thumb_path='" + thumb_path + '\'' +
                ", photo_path='" + photo_path + '\'' +
                ", tags=" + tags +
                ", author_id='" + author_id + '\'' +
                ", tips='" + tips + '\'' +
                ", cookstory='" + cookstory + '\'' +
                ", steps=" + steps +
                ", create_time='" + create_time + '\'' +
                ", recommended=" + recommended +
                ", act_des='" + act_des + '\'' +
                ", v_u='" + v_u + '\'' +
                ", user=" + user +
                ", author='" + author + '\'' +
                ", author_photo='" + author_photo + '\'' +
                ", author_verified=" + author_verified +
                ", collect_status=" + collect_status +
                ", favo_counts=" + favo_counts +
                ", comments_count=" + comments_count +
                ", dish_count=" + dish_count +
                '}';
    }
}
