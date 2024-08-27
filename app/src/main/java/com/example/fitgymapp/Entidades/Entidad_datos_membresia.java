package com.example.fitgymapp.Entidades;

public class Entidad_datos_membresia {

    private int ID_membresia;
    private String Tipo_membresia;
    private String Areas_membresias;

    private String Cantidad_user_registrados;

    public int getID_membresia() {
        return ID_membresia;
    }

    public void setID_membresia(int ID_membresia) {
        this.ID_membresia = ID_membresia;
    }



    public String getTipo_membresia() {
        return Tipo_membresia;
    }

    public void setTipo_membresia(String tipo_membresia) {
        Tipo_membresia = tipo_membresia;
    }



    public String getAreas_membresias() {
        return Areas_membresias;
    }

    public void setAreas_membresias(String areas_membresias) {
        Areas_membresias = areas_membresias;
    }

    public String getCantidad_user_registrados() {
        return Cantidad_user_registrados;
    }

    public void setCantidad_user_registrados(String cantidad_user_registrados) {
        Cantidad_user_registrados = cantidad_user_registrados;
    }



}
