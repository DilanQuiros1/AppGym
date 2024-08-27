package com.example.fitgymapp;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fitgymapp.Adaptadores.adapter_membresias_usuarios;
import com.example.fitgymapp.BDMembresias.BDmembresias;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;

import java.util.ArrayList;

public class ver_membresias_usuarios extends AppCompatActivity {
    BottomNavigationView view;
    RecyclerView lista_membresiasParausu;
    ArrayList<Entidad_ListaMembresias> listaArrayMembresiasParausu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_membresias_usuarios);

        view=findViewById(R.id.nav_main_compra);

        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.miPerfil)
                {
                    Intent intento = new Intent(getApplicationContext(), Mi_perfil_usuario.class);
                    startActivity(intento);

                } else if(item.getItemId()==R.id.entrenadores)
                {
                    Mensaje("add page coach");
                }else if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                    startActivity(intento);
                }

            }
        });


        lista_membresiasParausu=findViewById(R.id.ListaMembresias_para_usuarios);
        lista_membresiasParausu.setLayoutManager(new LinearLayoutManager(this));

        try {


            BDmembresias dbMembresias=new BDmembresias(ver_membresias_usuarios.this);
            listaArrayMembresiasParausu=new ArrayList<>();

            adapter_membresias_usuarios adapter = new adapter_membresias_usuarios(dbMembresias.mostrarMembresias1());
            lista_membresiasParausu.setAdapter(adapter);




        }
        catch (Exception e)
        {
            Mensaje(e.toString());
        }

    }
    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    };
}