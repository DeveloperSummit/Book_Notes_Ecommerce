
package com.example.himanshjosh.ui.cartitem.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCartListModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("cartData")
    @Expose
    private List<CartDatum> cartData = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CartDatum> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartDatum> cartData) {
        this.cartData = cartData;
    }

}
