
package com.example.himanshjosh.ui.orderhistory.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetHistoryListModel {

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("product")
    @Expose
    private List<Product> product = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

}
