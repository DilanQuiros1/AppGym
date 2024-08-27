package com.example.fitgymapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.BDusuario.BDusuarios;
import com.example.fitgymapp.Entidades.Entidad_datos_extras_usuario;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.io.File;

public class Mis_datos_extra_usuario extends AppCompatActivity {

    TextView nombreUsu, pesoUsu,edadUsu, objetivoUsu, irEditar, telefono;
    LinearLayout CardMisDatos;
    Entidad_datos_extras_usuario datosExtrasUsu;
    VariablesGlobales vg;
    ImageView imagenEntrenador;
    TextView texto_agregar_datos;
    BottomNavigationView view;
    Button btnAgregarDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos_extra_usuario);
        imagenEntrenador=findViewById(R.id.imageUsuario);
        nombreUsu=findViewById(R.id.NombreUsu);
        pesoUsu=findViewById(R.id.PesoUsu);
        edadUsu=findViewById(R.id.EdadUsu);
        objetivoUsu=findViewById(R.id.ObjetivoUsu);
        btnAgregarDatos=findViewById(R.id.btn_agregar_datos);
        CardMisDatos=findViewById(R.id.card_datos);
        texto_agregar_datos=findViewById(R.id.texto_agregarDatos);
        irEditar=findViewById(R.id.textoAccionEditar);
        telefono=findViewById(R.id.TelefonoUsu);
        view = findViewById(R.id.nav_main);
        vg=new VariablesGlobales();
        int id_usuario=Integer.parseInt(vg.getID());

        BDusuarios bDusuarios=new BDusuarios(Mis_datos_extra_usuario.this);
        datosExtrasUsu=bDusuarios.VerMisDatosExtras(id_usuario);

        try {
            if (datosExtrasUsu!=null)
            {
                nombreUsu.setText(datosExtrasUsu.getNombreUsu());
                pesoUsu.setText(datosExtrasUsu.getPeso());
                objetivoUsu.setText(datosExtrasUsu.getObjetivo());
                edadUsu.setText(datosExtrasUsu.getEdad());
                telefono.setText(datosExtrasUsu.getTelefono());

                File directory = getDir("Images_usuarios", Context.MODE_PRIVATE);
                File myPath = new File(directory, datosExtrasUsu.getUrlImg_usuario()+".jpg");
                Bitmap bitmap = BitmapFactory.decodeFile(myPath.getAbsolutePath());
                imagenEntrenador.setImageBitmap(bitmap);
            }
            else
            {
                CardMisDatos.setVisibility(View.GONE);
                btnAgregarDatos.setVisibility(View.VISIBLE);
                texto_agregar_datos.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e)
        {
            Mensaje(e.toString());
        }

        btnAgregarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), Agregar_datos_extra_usuario.class);
                startActivity(intento);
            }
        });

        irEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intento = new Intent(getApplicationContext(), Editar_datos_extra_usuario.class);
                startActivity(intento);
            }
        });


        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.membresias)
                {

                    Intent intento = new Intent(getApplicationContext(), ver_membresias_usuarios.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.entrenadores)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_entrenadoresUsuarios.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.miPerfil)
                {
                    Intent intento = new Intent(getApplicationContext(), Mi_perfil_usuario.class);
                    startActivity(intento);
                }

            }
        });

    }


    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

}