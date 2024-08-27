package com.example.fitgymapp.BDcompraMembresias;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

import com.example.fitgymapp.BDHelper;
import com.example.fitgymapp.Entidades.EntidadMembresiaActualUsuario;
import com.example.fitgymapp.Entidades.EntidadMembresiaUsuarioCompradas;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;

import java.util.ArrayList;

public class BDcompra_membresia extends BDHelper {
    Context context;
    public BDcompra_membresia(@Nullable Context context) {
        super(context);
        this.context=context;
    }


    public boolean ComprarMembresia(int usu_id, int membresia_id, String inicio, String vencimiento)
    {
        SQLiteDatabase MyDB =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_usuario_compra", usu_id);
        contentValues.put("ID_membresia", membresia_id);
        contentValues.put("fecha_inicio", inicio);
        contentValues.put("fecha_vencimiento", vencimiento);
        contentValues.put("estado", "Activo");
        long result = MyDB.insert("tb_compra", null, contentValues);

        if(result ==-1) return false;
        else return true;
    }

    public Entidad_ListaMembresias verMembresia_Comprar(int id)
    {
        BDHelper     dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        Entidad_ListaMembresias membresias = null;
        Cursor cursorContatos ;

        cursorContatos = db.rawQuery("SELECT ID_membresia, tipo_membresia, areas, precio, descripcion FROM tb_membresia WHERE ID_membresia = "+id+" LIMIT 1", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {

            membresias = new Entidad_ListaMembresias();
            membresias.setId(cursorContatos.getInt(0));
            membresias.setTipo(cursorContatos.getString(1));
            membresias.setAreas(cursorContatos.getString(2));
            membresias.setPrecio(cursorContatos.getString(3));
            membresias.setDescripcion(cursorContatos.getString(4));

        }

        cursorContatos.close();

        return membresias;
    }

    public ArrayList<EntidadMembresiaUsuarioCompradas> MostrarMembresiasCompraUsuario(int id)
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<EntidadMembresiaUsuarioCompradas> listaMembresias=new ArrayList<>();

        EntidadMembresiaUsuarioCompradas membresias = null;
        Cursor cursorContatos =null;

        cursorContatos = db.rawQuery("SELECT ID_compra, tb_membresia.tipo_membresia, tb_membresia.areas, fecha_inicio, fecha_vencimiento, estado FROM tb_compra" +
                " INNER JOIN tb_membresia ON tb_compra.ID_membresia = tb_membresia.ID_membresia WHERE ID_usuario_compra = "+id+";", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {
            do {
                membresias = new EntidadMembresiaUsuarioCompradas();
                membresias.setId(cursorContatos.getInt(0));
                membresias.setTipo_membresia(cursorContatos.getString(1));
                membresias.setAreas(cursorContatos.getString(2));
                membresias.setFecha_inicio(cursorContatos.getString(3));
                membresias.setFecha_vencimiento(cursorContatos.getString(4));
                membresias.setEstado(cursorContatos.getString(5));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public EntidadMembresiaActualUsuario VerMembresiaActualUsuario(int id)
    {
        BDHelper     dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EntidadMembresiaActualUsuario membresias = null;
        Cursor cursorContatos ;

        cursorContatos = db.rawQuery("SELECT ID_compra, tb_membresia.tipo_membresia, tb_membresia.areas, fecha_vencimiento FROM tb_compra " +
                " INNER JOIN tb_membresia on tb_compra.ID_membresia=tb_membresia.ID_membresia WHERE ID_usuario_compra = " +id+
                " AND estado = 'Activo' LIMIT 1;", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {

            membresias = new EntidadMembresiaActualUsuario();
            membresias.setId(cursorContatos.getInt(0));
            membresias.setTipoMembresiaActual(cursorContatos.getString(1));
            membresias.setAreasMembresiaActual(cursorContatos.getString(2));
            membresias.setVencimientoMembresiaActual(cursorContatos.getString(3));
        }

        cursorContatos.close();

        return membresias;
    }

    public ArrayList<EntidadMembresiaUsuarioCompradas> AdminMostrarMembresiasCompraUsuarioTodas()
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<EntidadMembresiaUsuarioCompradas> listaMembresias=new ArrayList<>();

        EntidadMembresiaUsuarioCompradas membresias = null;
        Cursor cursorContatos =null;

        cursorContatos = db.rawQuery("SELECT ID_compra, tb_membresia.tipo_membresia, tb_membresia.areas, fecha_inicio, fecha_vencimiento, estado, ID_usuario_compra FROM tb_compra" +
                " INNER JOIN tb_membresia ON tb_compra.ID_membresia = tb_membresia.ID_membresia;", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {
            do {
                membresias = new EntidadMembresiaUsuarioCompradas();
                membresias.setId(cursorContatos.getInt(0));
                membresias.setTipo_membresia(cursorContatos.getString(1));
                membresias.setAreas(cursorContatos.getString(2));
                membresias.setFecha_inicio(cursorContatos.getString(3));
                membresias.setFecha_vencimiento(cursorContatos.getString(4));
                membresias.setEstado(cursorContatos.getString(5));
                membresias.setID_UsuarioCompra(cursorContatos.getString(6));
                listaMembresias.add(membresias);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }
//actualizarEstadoDeCompras
public boolean actualizarEstadoDeCompras(String fechaAc) {
    boolean correcto = false;

    BDHelper dbHelper = new BDHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    try {
        db.execSQL("UPDATE tb_compra SET estado = 'Desactivado' WHERE fecha_vencimiento < '"+fechaAc+"'");
        correcto = true;
    } catch (Exception e) {
        e.toString();
        correcto = false;
    } finally {
        db.close();
    }
    return correcto;

}



}
