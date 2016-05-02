package com.fito.ejemplopouncecorp.modelos;

/**
 * Created by fito on 5/01/16.
 */
public class JSONPais {
    private int id;
    private String nombre;
    private String imagen;

    public JSONPais() {}

    public JSONPais(int id, String nombre, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}