package com.example.fitgymapp.Entidades;

public class Entidad_productosCarrito {

    String QR_pro;
    String Nombre_pro;
    String Precio_pro;
    String Descripcion_pro;
    String Cantidad_pro;
    String Url_img_pro;

    public Entidad_productosCarrito()
    {

    }

    public Entidad_productosCarrito(String QR_pro, String Nombre_pro, String Precio_pro)
    {
        this.QR_pro = QR_pro;
        this.Nombre_pro = Nombre_pro;
        this.Precio_pro = Precio_pro;
    }

    public String getNombre_pro() {
        return Nombre_pro;
    }

    public void setNombre_pro(String nombre_pro) {
        Nombre_pro = nombre_pro;
    }

    public String getQR_pro() {
        return QR_pro;
    }

    public String getCantidad_pro() {
        return Cantidad_pro;
    }

    public String getUrl_img_pro() {
        return Url_img_pro;
    }

    public void setUrl_img_pro(String url_img_pro) {
        Url_img_pro = url_img_pro;
    }

    public void setCantidad_pro(String cantidad_pro) {
        Cantidad_pro = cantidad_pro;
    }

    public String getDescripcion_pro() {
        return Descripcion_pro;
    }

    public void setDescripcion_pro(String descripcion_pro) {
        Descripcion_pro = descripcion_pro;
    }

    public String getPrecio_pro() {
        return Precio_pro;
    }

    public void setPrecio_pro(String precio_pro) {
        Precio_pro = precio_pro;
    }

    public void setQR_pro(String QR_pro) {
        this.QR_pro = QR_pro;
    }
}
