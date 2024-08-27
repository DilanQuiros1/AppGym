package com.example.fitgymapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitgymapp.BDusuario.BDusuarios;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Agregar_datos_extra_usuario extends AppCompatActivity {

    EditText pesoAc,objetivoAc, edadAc, telAc;
    Button agregarDatos;
    ImageView imagenEntrenador;
    BDusuarios bDusuarios;
    private static final int PICK_IMAGE_REQUEST = 1; // Código de solicitud para seleccionar una imagen
    VariablesGlobales vg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_datos_extra_usuario);
        pesoAc=findViewById(R.id.inputPeso);
        objetivoAc=findViewById(R.id.inputObjetivo);
        edadAc=findViewById(R.id.inputEdad);
        imagenEntrenador=findViewById(R.id.imageView);
        telAc=findViewById(R.id.inputTel);
        agregarDatos=findViewById(R.id.btnAgregarDatos);


        bDusuarios=new BDusuarios(this);
        vg=new VariablesGlobales();
        int id_usuario=Integer.parseInt(vg.getID());

        imagenEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
            }
        });

        agregarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Peso=pesoAc.getText().toString();
                String Objetivo=objetivoAc.getText().toString();
                String Edad=edadAc.getText().toString();
                String telefono=telAc.getText().toString();

                if(Peso.equals("")||Objetivo.equals("")||Edad.equals("")||vg.isAgrego_img_usuario()==false)
                {
                    Mensaje("Please enter all fields");
                }
                else
                {
                    Boolean insert = bDusuarios.AgregarMisDatosExtras(id_usuario, Peso,telefono, Edad,  Objetivo, vg.getID());

                    if(insert==true)
                    {
                        saveImageToInternalStorage(vg.getBitmapImg_usuario(), vg.getID());
                        Mensaje("Se registro de forma correcta");
                        vg.setAgrego_img_usuario(false);
                    }
                    else
                    {
                        Mensaje("Error al registrar");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                // Convierte la URI de la imagen a un objeto Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                vg.setBitmapImg_usuario(bitmap);
                vg.setAgrego_img_usuario(true);
                // Guarda la imagen en el almacenamiento interno
                //saveImageToInternalStorage(bitmap, "prueba");
                //Mensaje("Bitmap "+String.valueOf(bitmap));
                // Muestra la imagen en el ImageView
                imagenEntrenador.setImageBitmap(bitmap);

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


    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

}