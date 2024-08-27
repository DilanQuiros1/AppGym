package com.example.fitgymapp.Entidades;

public class EntidadMembresiaActualUsuario {

    private int id ;
    private String tipoMembresiaActual;
    private String AreasMembresiaActual;
    private String VencimientoMembresiaActual;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTipoMembresiaActual() {
        return tipoMembresiaActual;
    }

    public void setTipoMembresiaActual(String tipoMembresiaActual) {
        this.tipoMembresiaActual = tipoMembresiaActual;
    }

    public String getAreasMembresiaActual() {
        return AreasMembresiaActual;
    }

    public void setAreasMembresiaActual(String areasMembresiaActual) {
        AreasMembresiaActual = areasMembresiaActual;
    }

    public String getVencimientoMembresiaActual() {
        return VencimientoMembresiaActual;
    }

    public void setVencimientoMembresiaActual(String vencimientoMembresiaActual) {
        VencimientoMembresiaActual = vencimientoMembresiaActual;
    }

}
