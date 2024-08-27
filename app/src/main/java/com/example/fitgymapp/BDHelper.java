package com.example.fitgymapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =5;
    private static final String DATABASE_NOMBRE ="Fit_Gym_App.db";

    private  String tb_login = "CREATE TABLE tb_login( " +
            " ID_usuario INTEGER PRIMARY KEY," +
            " nombre TEXT NOT NULL, " +
            " correo TEXT NOT NULL, " +
            " password TEXT NOT NULL " +
            ")";


    private  String tb_datos_usuario="CREATE TABLE tb_datos_usuario( " +
            "ID_tb_datos INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID_datos_usuario INT NOT NULL, " +
            "peso TEXT NOT NULL, " +
            "telefono TEXT NOT NULL, "+
            "url_img TEXT NOT NULL, "+
            "objetivo TEXT NOT NULL, " +
            "edad TEXT NOT NULL, " +
            "FOREIGN KEY(ID_datos_usuario) REFERENCES tb_login(ID_usuario) " +
            ")";

    private String tb_membresias="CREATE TABLE tb_membresia( " +
            "ID_membresia INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tipo_membresia TEXT NOT NULL, " +
            "precio TEXT NOT NULL, " +
            "areas TEXT NOT NULL, " +
            "descripcion TEXT NOT NULL " +
            ")";

    private String tb_compra="CREATE TABLE tb_compra( " +
            "ID_compra INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID_usuario_compra INT NOT NULL, " +
            "ID_membresia INTEGER NOT NULL, " +
            "fecha_inicio DATE NOT NULL, " +
            "fecha_vencimiento DATE NOT NULL, " +
            "estado TEXT NOT NULL, " +
            "FOREIGN KEY (ID_usuario_compra) REFERENCES tb_login(ID_usuario), " +
            "FOREIGN KEY (ID_membresia) REFERENCES tb_membresia(ID_membresia) " +
            ")";

    private String tb_entrenadores="CREATE TABLE tb_entrenadores( " +
            "ID_entrenador INTEGER PRIMARY KEY NOT NULL, " +
            "nombre TEXT NOT NULL, " +
            "url_img TEXT NOT NULL, " +
            "edad TEXT NOT NULL, " +
            "telefono TEXT NOT NULL, " +
            "hora_inicio DATE NOT NULL, " +
            "hora_salida DATE NOT NULL " +
            ");";

    public BDHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase MyBD) {
        MyBD.execSQL(tb_login);
        MyBD.execSQL(tb_datos_usuario);
        MyBD.execSQL(tb_membresias);
        MyBD.execSQL(tb_compra);
        MyBD.execSQL(tb_entrenadores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyBD, int i, int i1) {
        MyBD.execSQL("DROP TABLE IF EXISTS tb_login");
        MyBD.execSQL("DROP TABLE IF EXISTS tb_datos_usuario");
        MyBD.execSQL("DROP TABLE IF EXISTS tb_membresia");
        MyBD.execSQL("DROP TABLE IF EXISTS tb_compra");
        MyBD.execSQL("DROP TABLE IF EXISTS tb_entrenadores");
        onCreate(MyBD);
    }
}
