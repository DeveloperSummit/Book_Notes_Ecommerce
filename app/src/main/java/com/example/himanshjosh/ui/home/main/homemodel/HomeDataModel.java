
package com.example.himanshjosh.ui.home.main.homemodel;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeDataModel {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;
    @SerializedName("allProduct")
    @Expose
    private List<AllProduct> allProduct = null;
    @SerializedName("homeMidBanner")
    @Expose
    private List<HomeMidBanner> homeMidBanner = null;
    @SerializedName("upscCategory")
    @Expose
    private List<UpscCategory> upscCategory = null;
    @SerializedName("bankCategory")
    @Expose
    private List<BankCategory> bankCategory = null;
    @SerializedName("bpscCategory")
    @Expose
    private List<BpscCategory> bpscCategory = null;
    @SerializedName("sscCategory")
    @Expose
    private List<SscCategory> sscCategory = null;

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

    public List<AllProduct> getAllProduct() {
        return allProduct;
    }

    public void setAllProduct(List<AllProduct> allProduct) {
        this.allProduct = allProduct;
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

    public List<BankCategory> getBankCategory() {
        return bankCategory;
    }

    public void setBankCategory(List<BankCategory> bankCategory) {
        this.bankCategory = bankCategory;
    }

    public List<BpscCategory> getBpscCategory() {
        return bpscCategory;
    }

    public void setBpscCategory(List<BpscCategory> bpscCategory) {
        this.bpscCategory = bpscCategory;
    }

    public List<SscCategory> getSscCategory() {
        return sscCategory;
    }

    public void setSscCategory(List<SscCategory> sscCategory) {
        this.sscCategory = sscCategory;
    }

}
