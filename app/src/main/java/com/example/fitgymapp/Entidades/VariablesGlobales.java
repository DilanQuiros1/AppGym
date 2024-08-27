package com.example.fitgymapp.Entidades;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

public class VariablesGlobales  {


    static String ID = "";
    static Boolean compra_membresia_realizada = true;
    static String SpinnerTipo = "";
    static String HoraIni = "";
    static String hrf_imgProducto = "";

    static Bitmap bitmapImg;
    static boolean agrego_img;
    static Uri img;
    static Bitmap bitmapImg_usuario;
    static boolean agrego_img_usuario;

    public static Bitmap getBitmapImg_usuario() {
        return bitmapImg_usuario;
    }

    public static void setBitmapImg_usuario(Bitmap bitmapImg_usuario) {
        VariablesGlobales.bitmapImg_usuario = bitmapImg_usuario;
    }

    public static String getHrf_imgProducto() {
        return hrf_imgProducto;
    }

    public static void setHrf_imgProducto(String hrf_imgProducto) {
        VariablesGlobales.hrf_imgProducto = hrf_imgProducto;
    }

    public static Uri getImg() {
        return img;
    }

    public static void setImg(Uri img) {
        VariablesGlobales.img = img;
    }

    public static boolean isAgrego_img_usuario() {
        return agrego_img_usuario;
    }

    public static void setAgrego_img_usuario(boolean agrego_img_usuario) {
        VariablesGlobales.agrego_img_usuario = agrego_img_usuario;
    }



    public static boolean isAgrego_img() {
        return agrego_img;
    }

    public static void setAgrego_img(boolean agrego_img) {
        VariablesGlobales.agrego_img = agrego_img;
    }



    public static Bitmap getBitmapImg() {
        return bitmapImg;
    }

    public static void setBitmapImg(Bitmap bitmapImg) {
        VariablesGlobales.bitmapImg = bitmapImg;
    }





    public static String getHoraSa() {
        return HoraSa;
    }

    public static void setHoraSa(String horaSa) {
        HoraSa = horaSa;
    }

    static String HoraSa = "";

    public static String getHoraIni() {
        return HoraIni;
    }

    public static void setHoraIni(String horaIni) {
        HoraIni = horaIni;
    }



    public static String getSpinnerTipo() {
        return SpinnerTipo;
    }

    public static void setSpinnerTipo(String spinnerTipo) {
        SpinnerTipo = spinnerTipo;
    }



    public static Boolean getCompra_membresia_realizada() {
        return compra_membresia_realizada;
    }

    public static void setCompra_membresia_realizada(Boolean compra_membresia_realizada) {
        VariablesGlobales.compra_membresia_realizada = compra_membresia_realizada;
    }



    private static VariablesGlobales instance = null;

    public VariablesGlobales() {}
    public static VariablesGlobales getInstance() {
        if(instance == null) {instance = new VariablesGlobales(); }
        return instance;
    }

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        VariablesGlobales.ID = ID;
    }



}// fin de la clase de variables globales