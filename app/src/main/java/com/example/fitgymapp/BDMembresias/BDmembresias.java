package com.example.fitgymapp.BDMembresias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.fitgymapp.BDHelper;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;
import com.example.fitgymapp.Entidades.Entidad_datos_membresia;
import com.example.fitgymapp.Entidades.Entidad_producto;

import java.util.ArrayList;

public class BDmembresias extends BDHelper {
    Context context;

    public BDmembresias(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public boolean AgregarMembresia(String tipo, String precio, String areas, String descripcion) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tipo_membresia", tipo);
        contentValues.put("precio", precio);
        contentValues.put("areas", areas);
        contentValues.put("descripcion", descripcion);
        long result = MyDB.insert("tb_membresia", null, contentValues);

        if (result == -1) return false;
        else return true;
    }

    public ArrayList<Entidad_ListaMembresias> mostrarMembresias1() {
        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_ListaMembresias> listaMembresias = new ArrayList<>();

        Entidad_ListaMembresias membresias = null;
        Cursor cursorContatos = null;

        cursorContatos = db.rawQuery("SELECT * FROM tb_membresia", null);//se puede usar Where

        if (cursorContatos.moveToFirst()) {
            do {
                membresias = new Entidad_ListaMembresias();
                membresias.setId(cursorContatos.getInt(0));
                membresias.setTipo(cursorContatos.getString(1));
                membresias.setAreas(cursorContatos.getString(2));
                membresias.setPrecio(cursorContatos.getString(3));
                membresias.setDescripcion(cursorContatos.getString(4));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public Entidad_ListaMembresias verMembresia_editar(int id) {
        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Entidad_ListaMembresias membresias = null;
        Cursor cursorContatos;

        cursorContatos = db.rawQuery("SELECT * FROM tb_membresia WHERE ID_membresia = " + id + " LIMIT 1", null);//se puede usar Where

        if (cursorContatos.moveToFirst()) {

            membresias = new Entidad_ListaMembresias();
            membresias.setId(cursorContatos.getInt(0));
            membresias.setTipo(cursorContatos.getString(1));
            membresias.setAreas(cursorContatos.getString(2));
            membresias.setPrecio(cursorContatos.getString(3));

        }

        cursorContatos.close();

        return membresias;
    }

    public boolean editarMembresia(int id, String tipo, String areas, String precio) {
        boolean correcto = false;

        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE tb_membresia SET tipo_membresia = '" +
                    tipo + "', areas = '" +
                    areas + "', precio = '" +
                    precio + "' WHERE ID_membresia = '" +
                    id + "'");
            correcto = true;
        } catch (Exception e) {
            e.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;

    }


    public boolean EliminarMembresia(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("tb_membresia", "ID_membresia" + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public int cantidad_membresia() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM tb_membresia ;";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }

        db.close();
        return count;
    }


    public ArrayList<Entidad_ListaMembresias> ObtenerNombresMembresias() {
        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_ListaMembresias> listaMembresias = new ArrayList<>();

        Entidad_ListaMembresias membresias = null;
        Cursor cursorContatos = null;

        cursorContatos = db.rawQuery("SELECT tipo_membresia FROM tb_membresia", null);//se puede usar Where

        if (cursorContatos.moveToFirst()) {
            do {
                membresias = new Entidad_ListaMembresias();
                membresias.setTipo(cursorContatos.getString(0));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public ArrayList<Entidad_datos_membresia> mostrarMembresias_seleccionar() {
        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_datos_membresia> listaMembresias = new ArrayList<>();

        Entidad_datos_membresia membresias = null;
        Cursor cursorContatos = null;

        cursorContatos = db.rawQuery("SELECT ID_membresia, tipo_membresia, areas FROM tb_membresia", null);//se puede usar Where

        if (cursorContatos.moveToFirst()) {
            do {
                membresias = new Entidad_datos_membresia();
                membresias.setID_membresia(cursorContatos.getInt(0));
                membresias.setTipo_membresia(cursorContatos.getString(1));
                membresias.setAreas_membresias(cursorContatos.getString(2));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }




}