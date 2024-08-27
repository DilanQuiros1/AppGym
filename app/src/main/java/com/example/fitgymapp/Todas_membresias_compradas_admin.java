package com.example.fitgymapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.example.fitgymapp.Adaptadores.adapter_membresias_compradas_usuario;
import com.example.fitgymapp.BDcompraMembresias.BDcompra_membresia;
import com.example.fitgymapp.Entidades.EntidadMembresiaUsuarioCompradas;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.util.ArrayList;

public class Todas_membresias_compradas_admin extends AppCompatActivity {

    RecyclerView lista_membresiasCompraUsuario;
    ArrayList<EntidadMembresiaUsuarioCompradas> listaArrayMembresiasCompradas;
    VariablesGlobales vg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_membresias_compradas_admin);

        vg=new VariablesGlobales();
        lista_membresiasCompraUsuario=findViewById(R.id.ListaTodasMembresiasCompradas);
        lista_membresiasCompraUsuario.setLayoutManager(new LinearLayoutManager(this));

        try {
            BDcompra_membresia bDcompraMembresia = new BDcompra_membresia(Todas_membresias_compradas_admin.this);
            listaArrayMembresiasCompradas=new ArrayList<>();

            adapter_membresias_compradas_usuario adapterMembresia_comprada_usu = new adapter_membresias_compradas_usuario(bDcompraMembresia.AdminMostrarMembresiasCompraUsuarioTodas());
            lista_membresiasCompraUsuario.setAdapter(adapterMembresia_comprada_usu);

        }
        catch (Exception e)
        {
            Mensaje(e.toString());
        }

    }
    public void Mensaje(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}