package com.example.fitgymapp.Entidades;

public class Entidad_producto {

    private String cantidad;
    private String ID_QRCODE;
    private String Nombre;
    private String Precio;
    private String Descripcion;
    private String url_img;

    public Entidad_producto()
    {

    }

    public Entidad_producto(String ID_QRCODE, String Nombre, String Precio) {
        this.ID_QRCODE = ID_QRCODE;
        this.Nombre = Nombre;
        this.Precio = Precio;
    }

    public Entidad_producto(String ID_QRCODE, String Nombre, String url_img, String Precio, String Descripcion,  String cantidad) {
        this.ID_QRCODE = ID_QRCODE;
        this.Nombre = Nombre;
        this.Precio = Precio;
        this.url_img = url_img;
        this.Descripcion = Descripcion;
        this.cantidad = cantidad;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }



    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }



    public String getID_QRCODE() {
        return ID_QRCODE;
    }

    public void setID_QRCODE(String ID_QRCODE) {
        this.ID_QRCODE = ID_QRCODE;
    }



    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }



    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }



    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }



}
