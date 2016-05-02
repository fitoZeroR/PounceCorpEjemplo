package com.fito.ejemplopouncecorp.modelos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fito on 11/01/16.
 */

public class JSONDetallePais {
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private JSONDetallePaisData data;
    @SerializedName("rc")
    private int rc;

    public JSONDetallePais() {}

    public JSONDetallePais(String msg, JSONDetallePaisData data, int rc) {
        this.msg = msg;
        this.data = data;
        this.rc = rc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONDetallePaisData getData() {
        return data;
    }

    public void setData(JSONDetallePaisData data) {
        this.data = data;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }
}
