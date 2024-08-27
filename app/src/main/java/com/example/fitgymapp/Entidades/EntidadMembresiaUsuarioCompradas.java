package com.example.fitgymapp.Entidades;

public class EntidadMembresiaUsuarioCompradas {

    private int id;
    private String tipo_membresia;
    private String areas;
    private String fecha_inicio;
    private String fecha_vencimiento;
    private String estado;
    private String ID_UsuarioCompra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_membresia() {
        return tipo_membresia;
    }

    public void setTipo_membresia(String tipo_membresia) {
        this.tipo_membresia = tipo_membresia;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getID_UsuarioCompra() {
        return ID_UsuarioCompra;
    }

    public void setID_UsuarioCompra(String ID_UsuarioCompra) {
        this.ID_UsuarioCompra = ID_UsuarioCompra;
    }

}
