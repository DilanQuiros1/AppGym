package com.example.fitgymapp;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.example.fitgymapp.Adaptadores.adapter_seleccionarMembresia_verRegistros;
import com.example.fitgymapp.BDMembresias.BDmembresias;
import com.example.fitgymapp.Entidades.Entidad_datos_membresia;

import java.util.ArrayList;

public class Seleccionar_membresias_verRegistros extends AppCompatActivity {

    RecyclerView lista_membresias;
    BottomNavigationView view_admin;
    ArrayList<Entidad_datos_membresia> listaArrayMembresias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_membresias_ver_registros);
        lista_membresias=findViewById(R.id.Eligir_Membresias);

        lista_membresias.setLayoutManager(new LinearLayoutManager(this));

        try
        {
            BDmembresias dbMembresias=new BDmembresias(Seleccionar_membresias_verRegistros.this);
            listaArrayMembresias=new ArrayList<>();

            adapter_seleccionarMembresia_verRegistros adapter = new adapter_seleccionarMembresia_verRegistros(dbMembresias.mostrarMembresias_seleccionar());
            lista_membresias.setAdapter(adapter);
        }
        catch (Exception e)
        {

        }

        view_admin=findViewById(R.id.nav_admin);
        view_admin.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.membresias_admin)
                {

                    Intent intento = new Intent(getApplicationContext(), Agregar_Membresia_Admin.class);
                    startActivity(intento);
                } else if(menuItem.getItemId()==R.id.entrenadores_admin)
                {
                    Intent intento = new Intent(getApplicationContext(), VerEntrenadoresDisponibles.class);
                    startActivity(intento);
                }
            }
        });

    }
}