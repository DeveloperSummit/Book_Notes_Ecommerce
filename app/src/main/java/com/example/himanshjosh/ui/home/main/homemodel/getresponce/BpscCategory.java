
package com.example.himanshjosh.ui.home.main.homemodel.getresponce;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BpscCategory {

    @SerializedName("data")
    @Expose
    private List<Datum__7> data = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public List<Datum__7> getData() {
        return data;
    }

    public void setData(List<Datum__7> data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
