package com.example.fitgymapp.DBLogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

import com.example.fitgymapp.BDHelper;

public class DBlogin extends BDHelper {

    Context context;
    public DBlogin(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public boolean registertUser( int ID_usuario, String nombre_usuario, String correo, String password)
    {
        SQLiteDatabase MyDB =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_usuario", ID_usuario);
        contentValues.put("nombre", nombre_usuario);
        contentValues.put("correo", correo);
        contentValues.put("password", password);
        long result = MyDB.insert("tb_login", null, contentValues);

        if(result ==-1) return false;
        else return true;
    }

    public boolean checkusername(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from tb_login where ID_usuario = ?", new String[]{username});//para revisar que noexista otro usuario igual

        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkusernamepassword(String username, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from tb_login where ID_usuario = ? and password = ?", new String[]{username, password});

        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

}
