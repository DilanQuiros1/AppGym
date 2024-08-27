package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.fitgymapp.Adaptadores.adapter_membresias_admin;
import com.example.fitgymapp.BDMembresias.BDmembresias;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class VerMembresiasAdmin extends AppCompatActivity {

    RecyclerView lista_membresias;
    ArrayList<Entidad_ListaMembresias> listaArrayMembresias;
    BottomNavigationView view_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_membresias_admin);

        lista_membresias=findViewById(R.id.ListaMembresias);
        lista_membresias.setLayoutManager(new LinearLayoutManager(this));

        try {


            BDmembresias dbMembresias=new BDmembresias(VerMembresiasAdmin.this);
            listaArrayMembresias=new ArrayList<>();


            adapter_membresias_admin adapter = new adapter_membresias_admin(dbMembresias.mostrarMembresias1());
            lista_membresias.setAdapter(adapter);


        }
        catch (Exception e)
        {
            Mensaje(e.toString());
        }

        view_admin=findViewById(R.id.nav_admin);
        view_admin.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.home)
                {
                    Intent intento = new Intent(getApplicationContext(), AdministradorMain.class);
                    startActivity(intento);

                } else if(menuItem.getItemId()==R.id.entrenadores_admin)
                {
                    Intent intento = new Intent(getApplicationContext(), VerEntrenadoresDisponibles.class);
                    startActivity(intento);
                }
            }
        });

    }
    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    };
}