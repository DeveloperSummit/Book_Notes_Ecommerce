
package com.example.himanshjosh.ui.home.main.homemodel.getresponce;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeMidBanner {

    @SerializedName("data")
    @Expose
    private List<Datum__3> data = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public List<Datum__3> getData() {
        return data;
    }

    public void setData(List<Datum__3> data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
