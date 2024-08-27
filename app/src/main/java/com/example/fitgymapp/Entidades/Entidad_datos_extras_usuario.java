package com.example.fitgymapp.Entidades;

public class Entidad_datos_extras_usuario {

    int ID_datos;
    String id_usario_tb_datos;
    String nombreUsu;
    String Peso;
    String Edad;
    String Objetivo;
    String Telefono;
    String Correo;
    private String urlImg_usuario;

    public String getUrlImg_usuario() {
        return urlImg_usuario;
    }

    public void setUrlImg_usuario(String urlImg_usuario) {
        this.urlImg_usuario = urlImg_usuario;
    }



    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }




    public String getId_usario_tb_datos() {
        return id_usario_tb_datos;
    }

    public void setId_usario_tb_datos(String id_usario_tb_datos) {
        this.id_usario_tb_datos = id_usario_tb_datos;
    }


    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    public String getPeso() {
        return Peso;
    }

    public void setPeso(String peso) {
        Peso = peso;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getObjetivo() {
        return Objetivo;
    }

    public void setObjetivo(String objetivo) {
        Objetivo = objetivo;
    }

    public int getID_datos() {
        return ID_datos;
    }

    public void setID_datos(int ID_datos) {
        this.ID_datos = ID_datos;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

}
