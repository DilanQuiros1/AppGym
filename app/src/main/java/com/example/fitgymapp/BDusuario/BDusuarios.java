package com.example.fitgymapp.BDusuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

import com.example.fitgymapp.BDHelper;
import com.example.fitgymapp.Entidades.Entidad_datos_extras_usuario;

import java.util.ArrayList;

public class BDusuarios extends BDHelper {
    Context context;
    public BDusuarios(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public boolean AgregarMisDatosExtras(int usuID, String peso,String tel, String edad, String objetivo,String url_img)
    {
        SQLiteDatabase MyDB =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_datos_usuario", usuID);
        contentValues.put("peso", peso);
        contentValues.put("telefono", tel);
        contentValues.put("objetivo", objetivo);
        contentValues.put("edad", edad);
        contentValues.put("url_img", url_img);
        long result = MyDB.insert("tb_datos_usuario", null, contentValues);

        if(result ==-1) return false;
        else return true;
    }

    public Entidad_datos_extras_usuario VerMisDatosExtras(int id)
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        Entidad_datos_extras_usuario datos = null;
        Cursor cursorContatos ;

        cursorContatos = db.rawQuery("SELECT ID_tb_datos ,tb_login.nombre, peso, objetivo, url_img, edad, telefono FROM tb_datos_usuario INNER JOIN tb_login\n" +
                "ON tb_datos_usuario.ID_datos_usuario = tb_login.ID_usuario WHERE tb_datos_usuario.ID_datos_usuario = "+id+" LIMIT 1;", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {

            datos = new Entidad_datos_extras_usuario();
            datos.setID_datos(cursorContatos.getInt(0));
            datos.setNombreUsu(cursorContatos.getString(1));
            datos.setPeso(cursorContatos.getString(2));
            datos.setObjetivo(cursorContatos.getString(3));
            datos.setUrlImg_usuario(cursorContatos.getString(4));
            datos.setEdad(cursorContatos.getString(5));
            datos.setTelefono(cursorContatos.getString(6));

        }

        cursorContatos.close();

        return datos;
    }

    public Entidad_datos_extras_usuario VerMiImagenPerfil(int id)
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        Entidad_datos_extras_usuario datos = null;
        Cursor cursorContatos ;

        cursorContatos = db.rawQuery("SELECT url_img from tb_datos_usuario WHERE ID_datos_usuario = " +
                id+" LIMIT 1;", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {

            datos = new Entidad_datos_extras_usuario();
            datos.setUrlImg_usuario(cursorContatos.getString(0));
        }

        cursorContatos.close();

        return datos;
    }

    public Entidad_datos_extras_usuario VerMisNobre_Correo(int id)
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        Entidad_datos_extras_usuario datos = null;
        Cursor cursorContatos ;

        cursorContatos = db.rawQuery("SELECT nombre, correo FROM tb_login WHERE ID_usuario =" +
                id+" ;", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {

            datos = new Entidad_datos_extras_usuario();
            datos.setNombreUsu(cursorContatos.getString(0));
            datos.setCorreo(cursorContatos.getString(1));

        }

        cursorContatos.close();

        return datos;
    }


    public boolean EditarMisDatosExtras(int id_usu, String peso, String objetivo,String tel, String url, String edad)
    {
        boolean correcto =false;

        BDHelper dbHelper= new BDHelper(    context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.execSQL("UPDATE tb_datos_usuario SET peso = '" +
                    peso +"', objetivo = '" +
                    objetivo+"', edad = '" +
                    edad+"', telefono = '" +
                    tel+"', url_img = '" +
                    url+"' WHERE ID_datos_usuario = " +
                    id_usu+";");
            correcto =true;
        }
        catch (Exception e)
        {
            e.toString();
            correcto =false;
        } finally {
            db.close();
        }
        return correcto;

    }


    public Entidad_datos_extras_usuario ContactarUsuario(int id)
    {
        BDHelper dbHelper= new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        Entidad_datos_extras_usuario datos = null;
        Cursor cursorContatos ;

        cursorContatos = db.rawQuery("SELECT ID_tb_datos, ID_datos_usuario, telefono FROM tb_datos_usuario WHERE ID_datos_usuario = "+id+" LIMIT 1;", null);//se puede usar Where

        if(cursorContatos.moveToFirst())
        {

            datos = new Entidad_datos_extras_usuario();
            datos.setID_datos(cursorContatos.getInt(0));
            datos.setId_usario_tb_datos(cursorContatos.getString(1));
            datos.setTelefono(cursorContatos.getString(2));

        }

        cursorContatos.close();

        return datos;
    }


    public ArrayList<Entidad_datos_extras_usuario> ContactosTodosUsuario() {
        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Entidad_datos_extras_usuario> listaMembresias = new ArrayList<>();

        Entidad_datos_extras_usuario contactos = null;
        Cursor cursorContatos = null;

        cursorContatos = db.rawQuery("SELECT ID_tb_datos, tb_login.nombre, ID_datos_usuario, telefono FROM " +
                "tb_datos_usuario INNER JOIN tb_login ON " +
                "tb_datos_usuario.ID_datos_usuario = tb_login.ID_usuario;", null);//se puede usar Where

        if (cursorContatos.moveToFirst()) {
            do {
                contactos = new Entidad_datos_extras_usuario();
                contactos.setID_datos(cursorContatos.getInt(0));
                contactos.setNombreUsu(cursorContatos.getString(1));
                contactos.setId_usario_tb_datos(cursorContatos.getString(2));
                contactos.setTelefono(cursorContatos.getString(3));
                listaMembresias.add(contactos);
            } while (cursorContatos.moveToNext());
        }

        cursorContatos.close();

        return listaMembresias;
    }

    public int verificarSiPuedeComprarMembresia(int id_usuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT count(ID_datos_usuario) FROM tb_datos_usuario WHERE ID_datos_usuario ='" +
                id_usuario+"';";
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
