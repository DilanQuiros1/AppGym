package com.example.fitgymapp.BDentranadores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import android.util.Log;

import com.example.fitgymapp.BDHelper;
import com.example.fitgymapp.Entidades.Entidad_entrenador;

import java.util.ArrayList;

public class BDentrenador extends BDHelper {
    Context context;
    public BDentrenador(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public boolean AgregarEntrenador(String id, String nombre, String urlimg, String edad, String tel, String Hora_inicio, String Hora_salida) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_entrenador", id);
        contentValues.put("nombre", nombre);
        contentValues.put("url_img", urlimg);
        contentValues.put("edad", edad);
        contentValues.put("telefono", tel);
        contentValues.put("hora_inicio", Hora_inicio);
        contentValues.put("hora_salida", Hora_salida);
        long result = MyDB.insert("tb_entrenadores", null, contentValues);

        if (result == -1) return false;
        else return true;
    }


    public ArrayList<Entidad_entrenador> MostrarEntrenadores() {
        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_entrenador> listaMembresias = new ArrayList<>();

        Entidad_entrenador entrenador = null;
        Cursor cursorContatos = null;

        cursorContatos = db.rawQuery("SELECT ID_entrenador, nombre, url_img,telefono, hora_inicio, hora_salida   FROM tb_entrenadores ;", null);//se puede usar Where

        if (cursorContatos.moveToFirst()) {
            do {
                entrenador = new Entidad_entrenador();
                entrenador.setId_entrenador(cursorContatos.getString(0));
                entrenador.setNombre_entrenador(cursorContatos.getString(1));
                entrenador.setUrlImg_entrenador(cursorContatos.getString(2));
                entrenador.setTel_entrenador(cursorContatos.getString(3));
                entrenador.setHoraEntrada_entrenador(cursorContatos.getString(4));
                entrenador.setHoraSalida_entrenador(cursorContatos.getString(5));

                Log.i("DATOS1", cursorContatos.getString(1));
                Log.i("DATOS2", cursorContatos.getString(2));
                Log.i("DATOS3", cursorContatos.getString(3));
                Log.i("DATOS4", cursorContatos.getString(4));
                Log.i("DATOS5", cursorContatos.getString(5));
                listaMembresias.add(entrenador);

            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public boolean editarEntrenador(int id, String nombre, String url, String edad, String tel, String hora_ini, String hora_salida) {
        boolean correcto = false;

        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE tb_entrenadores SET ID_entrenador = " +
                    id+", nombre = '" +
                    nombre+"', url_img = '" +
                    url+"', edad = '" + edad +"', telefono = '" +
                    tel+"', hora_inicio = '" + hora_ini +"', hora_salida = '"+hora_salida+"' WHERE  ID_entrenador = "+id+" ;");
            correcto = true;
        } catch (Exception e) {
            e.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;

    }

    public boolean EliminarEntrenador(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("tb_entrenadores", "ID_entrenador" + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public Entidad_entrenador verEntrandor_editar(int id) {
        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Entidad_entrenador entrenador = null;
        Cursor cursorContatos;

        cursorContatos = db.rawQuery("SELECT ID_entrenador, nombre, url_img, edad, telefono, hora_inicio, hora_salida FROM tb_entrenadores WHERE ID_entrenador = " + id + " LIMIT 1", null);//se puede usar Where

        if (cursorContatos.moveToFirst()) {

            entrenador = new Entidad_entrenador();
            entrenador.setId_entrenador(cursorContatos.getString(0));
            entrenador.setNombre_entrenador(cursorContatos.getString(1));
            entrenador.setUrlImg_entrenador(cursorContatos.getString(2));
            entrenador.setEdad_entrenador(cursorContatos.getString(3));
            entrenador.setTel_entrenador(cursorContatos.getString(4));
            entrenador.setHoraEntrada_entrenador(cursorContatos.getString(5));
            entrenador.setHoraSalida_entrenador(cursorContatos.getString(6));

        }

        cursorContatos.close();

        return entrenador;
    }

    public ArrayList<Entidad_entrenador> ContactosTodosEntrenador() {
        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_entrenador> listaMembresias = new ArrayList<>();

        Entidad_entrenador contactos = null;
        Cursor cursorContatos = null;

        cursorContatos = db.rawQuery("SELECT ID_entrenador, nombre, telefono FROM tb_entrenadores;", null);//se puede usar Where

        if (cursorContatos.moveToFirst()) {
            do {
                contactos = new Entidad_entrenador();
                contactos.setId_entrenador(cursorContatos.getString(0));
                contactos.setNombre_entrenador(cursorContatos.getString(1));
                contactos.setTel_entrenador(cursorContatos.getString(2));
                listaMembresias.add(contactos);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

}
