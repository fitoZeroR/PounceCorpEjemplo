package com.fito.ejemplopouncecorp.modelos;

/**
 * Created by fito on 5/01/16.
 */
public class RespuestaJSONLogin {
    private String msg;
    private String data;
    private int rc;

    public RespuestaJSONLogin() {}

    public RespuestaJSONLogin(String msg, String data, int rc) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }
}