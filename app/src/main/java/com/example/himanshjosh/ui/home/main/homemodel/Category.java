
package com.example.himanshjosh.ui.home.main.homemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("cimage")
    @Expose
    private String cimage;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("mbanner")
    @Expose
    private String mbanner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCimage() {
        return cimage;
    }

    public void setCimage(String cimage) {
        this.cimage = cimage;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getMbanner() {
        return mbanner;
    }

    public void setMbanner(String mbanner) {
        this.mbanner = mbanner;
    }

}
