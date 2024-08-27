package com.example.fitgymapp.BDDatos_membresia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

import com.example.fitgymapp.BDHelper;
import com.example.fitgymapp.Entidades.Entidad_usuarios_registrosCadamembresias;

import java.util.ArrayList;

public class BDDatos_membresia extends BDHelper {
    Context context;
    public BDDatos_membresia(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public int cantidad_usuarios_cada_membresia(int IDMembresia) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM tb_compra INNER JOIN tb_membresia " +
                "ON tb_compra.ID_membresia = tb_membresia.ID_membresia " +
                "WHERE tb_compra.ID_membresia = " + IDMembresia+"  AND tb_compra.estado='Activo' ;";
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

    public int cantidad_usuariosVencidos_cada_membresia(int IDmembresia) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM tb_compra INNER JOIN tb_membresia " +
                "ON tb_compra.ID_membresia = tb_membresia.ID_membresia " +
                "WHERE tb_compra.ID_membresia = " + IDmembresia+"  AND tb_compra.estado='Desactivado';";
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


    public ArrayList<Entidad_usuarios_registrosCadamembresias> MostrarUserCadaMembresias(int id)//mostrar solo los que estan activos segun fechas de compra
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_usuarios_registrosCadamembresias> listaMembresias=new ArrayList<>();

        Entidad_usuarios_registrosCadamembresias membresias = null;
        Cursor cursorContatos =null;

        cursorContatos = db.rawQuery("SELECT DISTINCT tb_login.ID_usuario, tb_login.nombre, tb_login.correo,tb_compra.estado FROM " +
                "  tb_compra INNER JOIN tb_login ON tb_compra.ID_usuario_compra = tb_login.ID_usuario " +
                "WHERE ID_membresia = "+id+" AND estado = 'Activo';", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {
            do {
                membresias = new Entidad_usuarios_registrosCadamembresias();
                membresias.setID_usuario(cursorContatos.getInt(0));
                membresias.setNombre(cursorContatos.getString(1));
                membresias.setCorreo(cursorContatos.getString(2));
                membresias.setEstado(cursorContatos.getString(3));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public ArrayList<Entidad_usuarios_registrosCadamembresias> BuscarUserID(String id)//mostrar solo los que estan activos segun fechas de compra
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_usuarios_registrosCadamembresias> listaMembresias=new ArrayList<>();

        Entidad_usuarios_registrosCadamembresias membresias = null;
        Cursor cursorContatos =null;

        cursorContatos = db.rawQuery("SELECT DISTINCT tb_login.ID_usuario, tb_login.nombre, tb_login.correo, tb_compra.estado " +
                "FROM tb_compra " +
                "INNER JOIN tb_login ON tb_compra.ID_usuario_compra = tb_login.ID_usuario " +
                "WHERE tb_login.ID_usuario LIKE '" +
                id+"%';", null);

        if(cursorContatos.moveToFirst())
        {
            do {
                membresias = new Entidad_usuarios_registrosCadamembresias();
                membresias.setID_usuario(cursorContatos.getInt(0));
                membresias.setNombre(cursorContatos.getString(1));
                membresias.setCorreo(cursorContatos.getString(2));
                membresias.setEstado(cursorContatos.getString(3));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    //mostrar los que ya se les vencio la membresia

    public ArrayList<Entidad_usuarios_registrosCadamembresias> MostrarUserCadaMembresiasVencidos(int id)//mostrar solo los que estan activos segun fechas de compra
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_usuarios_registrosCadamembresias> listaMembresias=new ArrayList<>();

        Entidad_usuarios_registrosCadamembresias membresias = null;
        Cursor cursorContatos =null;

        cursorContatos = db.rawQuery("SELECT DISTINCT tb_login.ID_usuario, tb_login.nombre, tb_login.correo,tb_compra.estado FROM " +
                "  tb_compra INNER JOIN tb_login ON tb_compra.ID_usuario_compra = tb_login.ID_usuario " +
                "WHERE ID_membresia = "+id+" AND estado = 'Desactivado';", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {
            do {
                membresias = new Entidad_usuarios_registrosCadamembresias();
                membresias.setID_usuario(cursorContatos.getInt(0));
                membresias.setNombre(cursorContatos.getString(1));
                membresias.setCorreo(cursorContatos.getString(2));
                membresias.setEstado(cursorContatos.getString(3));

                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public ArrayList<Entidad_usuarios_registrosCadamembresias> MostrarTodosUserActivos()//mostrar solo los que estan activos segun fechas de compra
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_usuarios_registrosCadamembresias> listaMembresias=new ArrayList<>();

        Entidad_usuarios_registrosCadamembresias membresias = null;
        Cursor cursorContatos =null;

        cursorContatos = db.rawQuery("SELECT DISTINCT tb_login.ID_usuario, tb_login.nombre, tb_login.correo, tb_compra.estado FROM " +
                "  tb_compra INNER JOIN tb_login ON tb_compra.ID_usuario_compra = tb_login.ID_usuario " +
                "WHERE estado = 'Activo';", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {
            do {
                membresias = new Entidad_usuarios_registrosCadamembresias();
                membresias.setID_usuario(cursorContatos.getInt(0));
                membresias.setNombre(cursorContatos.getString(1));
                membresias.setCorreo(cursorContatos.getString(2));
                membresias.setEstado(cursorContatos.getString(3));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public ArrayList<Entidad_usuarios_registrosCadamembresias> MostrarTodosUserVencidos()//mostrar solo los que estan activos segun fechas de compra
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_usuarios_registrosCadamembresias> listaMembresias=new ArrayList<>();

        Entidad_usuarios_registrosCadamembresias membresias = null;
        Cursor cursorContatos =null;

        cursorContatos = db.rawQuery("SELECT DISTINCT tb_login.ID_usuario, tb_login.nombre, tb_login.correo , tb_compra.estado FROM " +
                "  tb_compra INNER JOIN tb_login ON tb_compra.ID_usuario_compra = tb_login.ID_usuario " +
                "WHERE estado = 'Desactivado';", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {
            do {
                membresias = new Entidad_usuarios_registrosCadamembresias();
                membresias.setID_usuario(cursorContatos.getInt(0));
                membresias.setNombre(cursorContatos.getString(1));
                membresias.setCorreo(cursorContatos.getString(2));
                membresias.setEstado(cursorContatos.getString(3));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public int cantidad_todos_usuarios() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT COUNT(*) FROM tb_compra INNER JOIN tb_membresia " +
                "ON tb_compra.ID_membresia = tb_membresia.ID_membresia " +
                "WHERE tb_compra.estado='Activo' ;";
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


}
