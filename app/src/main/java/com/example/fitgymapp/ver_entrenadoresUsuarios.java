package com.example.fitgymapp;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.fitgymapp.Adaptadores.adapter_entrenadoresVerUusarios;
import com.example.fitgymapp.BDentranadores.BDentrenador;
import com.example.fitgymapp.Entidades.Entidad_entrenador;

import java.util.ArrayList;

public class ver_entrenadoresUsuarios extends AppCompatActivity {

    RecyclerView verEntrenadores;
    ImageView irContactarEntrenador;
    ArrayList<Entidad_entrenador> listaEntrenadores;
    BottomNavigationView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_entrenadores_usuarios);
        irContactarEntrenador=findViewById(R.id.ircontactarEntrenador);
        verEntrenadores=findViewById(R.id.VerEntrenadores);
        verEntrenadores.setLayoutManager(new LinearLayoutManager(this));
        view = findViewById(R.id.nav_main);
        BDentrenador bDentrenador=new BDentrenador(ver_entrenadoresUsuarios.this);
        listaEntrenadores=new ArrayList<>();

        try
        {
            adapter_entrenadoresVerUusarios adapter=new adapter_entrenadoresVerUusarios(bDentrenador.MostrarEntrenadores());
            verEntrenadores.setAdapter(adapter);


        }
        catch (Exception e)
        {
            Log.i("MENSAJE ", e.toString());
        }

        irContactarEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), contactar_entrenador.class);
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
}