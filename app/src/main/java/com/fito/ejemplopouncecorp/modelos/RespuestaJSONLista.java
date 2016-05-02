package com.fito.ejemplopouncecorp.modelos;

import java.util.List;

/**
 * Created by fito on 5/01/16.
 */
public class RespuestaJSONLista {
    private String msg;
    private List<JSONPais> data;
    private int rc;

    public RespuestaJSONLista() {}

    public RespuestaJSONLista(String msg, List<JSONPais> data, int rc) {
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

    public List<JSONPais> getData() {
        return data;
    }

    public void setData(List<JSONPais> data) {
        this.data = data;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }
}
