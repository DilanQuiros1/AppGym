package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.BDusuario.BDusuarios;
import com.example.fitgymapp.Entidades.Entidad_datos_extras_usuario;
import com.example.fitgymapp.Entidades.VariablesGlobales;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Editar_datos_extra_usuario extends AppCompatActivity {
    EditText pesoUsu,edadUsu, objetivoUsu, tel;
    Button btnEditarDatos;
    VariablesGlobales vg;
    Entidad_datos_extras_usuario datosExtrasUsu;
    private static final int PICK_IMAGE_REQUEST = 1; // Código de solicitud para seleccionar una imagen
    Boolean correcto;
    ImageView imgUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos_extra_usuario);
        pesoUsu=findViewById(R.id.PesoUsuEditar);
        edadUsu=findViewById(R.id.EdadUsuEditar);
        objetivoUsu=findViewById(R.id.ObjetivoUsuEditar);
        tel=findViewById(R.id.TelUsuEditar);
        btnEditarDatos=findViewById(R.id.btnDatosEditar);
        imgUsuario=findViewById(R.id.imageEditarUsuario);
        vg=new VariablesGlobales();

        int id_usuario=Integer.parseInt(vg.getID());

        BDusuarios bDusuarios=new BDusuarios(Editar_datos_extra_usuario.this);
        datosExtrasUsu=bDusuarios.VerMisDatosExtras(id_usuario);

        try {
            if (datosExtrasUsu!=null)
            {
                pesoUsu.setText(datosExtrasUsu.getPeso());
                objetivoUsu.setText(datosExtrasUsu.getObjetivo());
                edadUsu.setText(datosExtrasUsu.getEdad());
                tel.setText(datosExtrasUsu.getTelefono());

                File directory = getDir("Images_usuarios", Context.MODE_PRIVATE);
                File myPath = new File(directory, datosExtrasUsu.getUrlImg_usuario()+".jpg");
                Bitmap bitmap = BitmapFactory.decodeFile(myPath.getAbsolutePath());
                imgUsuario.setImageBitmap(bitmap);
            }

        }
        catch (Exception e)
        {
            Mensaje(e.toString());
        }

        imgUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
            }
        });

        btnEditarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String peso = pesoUsu.getText().toString();
                String obj = objetivoUsu.getText().toString();
                String edad = edadUsu.getText().toString();
                String telefono = tel.getText().toString();
                if(peso.equals("")||telefono.equals("")||obj.equals("")||edad.equals("")||vg.isAgrego_img_usuario()==false)
                {
                    Mensaje("Llena todos los campos");
                }
                else
                {
                    correcto = bDusuarios.EditarMisDatosExtras(id_usuario, peso, obj, telefono, vg.getID(), edad);

                    if(correcto)
                    {
                        saveImageToInternalStorage(vg.getBitmapImg_usuario(), vg.getID());
                        Mensaje("Datos modificados");

                    }
                    else
                    {
                        Mensaje("Error de editar");
                    }

                }

            }

        });

        BottomNavigationView view = findViewById(R.id.nav_main);
        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.miPerfil)
                {
                    Intent intento = new Intent(getApplicationContext(), Mi_perfil_usuario.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.membresias)
                {

                    Intent intento = new Intent(getApplicationContext(), ver_membresias_usuarios.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.entrenadores)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_entrenadoresUsuarios.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.gym_productos)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_productos_usuario.class);
                    startActivity(intento);
                }

            }
        });

    }
    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgUsuario.setImageBitmap(bitmap);
                vg.setBitmapImg_usuario(bitmap);
                vg.setAgrego_img_usuario(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void saveImageToInternalStorage(Bitmap bitmapImage, String nombreIMG) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("Images_usuarios", Context.MODE_PRIVATE); // Crea un directorio llamado "images"

        // Genera un nombre de archivo único para la imagen
        String imageName = nombreIMG+".jpg";//le mando como nombre el ID del usuario

        File myPath = new File(directory, imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //Mensaje("Mensaje "+imageName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}