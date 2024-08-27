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

import com.example.fitgymapp.Adaptadores.adapter_membresias_compradas_usuario;
import com.example.fitgymapp.BDcompraMembresias.BDcompra_membresia;
import com.example.fitgymapp.Entidades.EntidadMembresiaUsuarioCompradas;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.util.ArrayList;

public class Mis_MembresiasCompradas_usuario extends AppCompatActivity {
    RecyclerView lista_membresiasCompraUsuario;
    ArrayList<EntidadMembresiaUsuarioCompradas> listaArrayMembresiasCompradas;
    VariablesGlobales vg;
    BottomNavigationView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_membresias_compradas_usuario);
        view = findViewById(R.id.nav_main);
        vg=new VariablesGlobales();
        lista_membresiasCompraUsuario=findViewById(R.id.ListaMembresiaCompraUsuario);
        lista_membresiasCompraUsuario.setLayoutManager(new LinearLayoutManager(this));

        try {
            BDcompra_membresia bDcompraMembresia = new BDcompra_membresia(Mis_MembresiasCompradas_usuario.this);
            listaArrayMembresiasCompradas=new ArrayList<>();

            adapter_membresias_compradas_usuario adapterMembresia_comprada_usu = new adapter_membresias_compradas_usuario(bDcompraMembresia.MostrarMembresiasCompraUsuario(Integer.parseInt(vg.getID())));
            lista_membresiasCompraUsuario.setAdapter(adapterMembresia_comprada_usu);

        }
        catch (Exception e)
        {
            Mensaje(e.toString());
        }

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

    public void Mensaje(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}