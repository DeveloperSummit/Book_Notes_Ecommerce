
package com.example.himanshjosh.ui.home.main.homemodel.getresponce;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelHomeResponce {

    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("slider")
    @Expose
    private Slider slider;
    @SerializedName("allProduct")
    @Expose
    private AllProduct allProduct;
    @SerializedName("homeMidBanner")
    @Expose
    private HomeMidBanner homeMidBanner;
    @SerializedName("upscCategory")
    @Expose
    private UpscCategory upscCategory;
    @SerializedName("bankCategory")
    @Expose
    private BankCategory bankCategory;
    @SerializedName("homeBanner2")
    @Expose
    private HomeBanner2 homeBanner2;
    @SerializedName("bpscCategory")
    @Expose
    private BpscCategory bpscCategory;
    @SerializedName("homeBanner3")
    @Expose
    private HomeBanner3 homeBanner3;
    @SerializedName("sscCategory")
    @Expose
    private SscCategory sscCategory;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public AllProduct getAllProduct() {
        return allProduct;
    }

    public void setAllProduct(AllProduct allProduct) {
        this.allProduct = allProduct;
    }

    public HomeMidBanner getHomeMidBanner() {
        return homeMidBanner;
    }

    public void setHomeMidBanner(HomeMidBanner homeMidBanner) {
        this.homeMidBanner = homeMidBanner;
    }

    public UpscCategory getUpscCategory() {
        return upscCategory;
    }

    public void setUpscCategory(UpscCategory upscCategory) {
        this.upscCategory = upscCategory;
    }

    public BankCategory getBankCategory() {
        return bankCategory;
    }

    public void setBankCategory(BankCategory bankCategory) {
        this.bankCategory = bankCategory;
    }

    public HomeBanner2 getHomeBanner2() {
        return homeBanner2;
    }

    public void setHomeBanner2(HomeBanner2 homeBanner2) {
        this.homeBanner2 = homeBanner2;
    }

    public BpscCategory getBpscCategory() {
        return bpscCategory;
    }

    public void setBpscCategory(BpscCategory bpscCategory) {
        this.bpscCategory = bpscCategory;
    }

    public HomeBanner3 getHomeBanner3() {
        return homeBanner3;
    }

    public void setHomeBanner3(HomeBanner3 homeBanner3) {
        this.homeBanner3 = homeBanner3;
    }

    public SscCategory getSscCategory() {
        return sscCategory;
    }

    public void setSscCategory(SscCategory sscCategory) {
        this.sscCategory = sscCategory;
    }

}
