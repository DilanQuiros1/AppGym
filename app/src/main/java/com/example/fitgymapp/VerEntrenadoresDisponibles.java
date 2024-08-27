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
import android.widget.Toast;

import com.example.fitgymapp.Adaptadores.adapter_entrenadores;
import com.example.fitgymapp.BDentranadores.BDentrenador;
import com.example.fitgymapp.Entidades.Entidad_entrenador;

import java.util.ArrayList;

public class VerEntrenadoresDisponibles extends AppCompatActivity {

    RecyclerView verEntrenadores;
    BottomNavigationView view_admin;
    ArrayList<Entidad_entrenador> listaEntrenadores;
    ImageView ircontactar_entrenador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_entrenadores_disponibles);
        verEntrenadores=findViewById(R.id.VerEntrenadores);
        verEntrenadores.setLayoutManager(new LinearLayoutManager(this));
        ircontactar_entrenador = findViewById(R.id.contactar_entrenador);

        BDentrenador bDentrenador=new BDentrenador(VerEntrenadoresDisponibles.this);
        listaEntrenadores=new ArrayList<>();

       try
       {
           adapter_entrenadores adapter=new adapter_entrenadores(bDentrenador.MostrarEntrenadores());
           verEntrenadores.setAdapter(adapter);


       }
       catch (Exception e)
       {
           Log.i("MENSAJE ", e.toString());
       }


        view_admin=findViewById(R.id.nav_admin);
        view_admin.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.membresias_admin)
                {

                    Intent intento = new Intent(getApplicationContext(), Agregar_Membresia_Admin.class);
                    startActivity(intento);
                } else if(menuItem.getItemId()==R.id.home)
                {
                    Intent intento = new Intent(getApplicationContext(), AdministradorMain.class);
                    startActivity(intento);
                }
            }
        });

        ircontactar_entrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), contactar_entrenador.class);
                startActivity(intento);
            }
        });



    }

    public void Mensaje(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}