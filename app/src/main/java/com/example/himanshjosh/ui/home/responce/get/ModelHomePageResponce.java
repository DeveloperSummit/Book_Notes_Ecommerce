
package com.example.himanshjosh.ui.home.responce.get;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelHomePageResponce {


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;
    @SerializedName("homeMidBanner")
    @Expose
    private List<HomeMidBanner> homeMidBanner = null;
    @SerializedName("upscCategory")
    @Expose
    private List<UpscCategory> upscCategory = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

    public List<HomeMidBanner> getHomeMidBanner() {
        return homeMidBanner;
    }

    public void setHomeMidBanner(List<HomeMidBanner> homeMidBanner) {
        this.homeMidBanner = homeMidBanner;
    }

    public List<UpscCategory> getUpscCategory() {
        return upscCategory;
    }

    public void setUpscCategory(List<UpscCategory> upscCategory) {
        this.upscCategory = upscCategory;
    }

}
