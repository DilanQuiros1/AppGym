package com.example.fitgymapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.Adaptadores.adapter_datosCada_membresia;
import com.example.fitgymapp.BDDatos_membresia.BDDatos_membresia;
import com.example.fitgymapp.Entidades.Entidad_usuarios_registrosCadamembresias;

import java.util.ArrayList;

public class VerRegistrosVencidosAdmin extends AppCompatActivity {

    RecyclerView lista_usuariosVencidosCadaMembresia;
    ArrayList<Entidad_usuarios_registrosCadamembresias> listaArrayMembresiasCompradas;
    TextView viewCantidadUserVencidos, msjVencidos, texo_membresia;
    BDDatos_membresia bdDatos_membresia;
    String tipo;
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registros_vencidos_admin);
        viewCantidadUserVencidos=findViewById(R.id.CantidadVencidos);
        msjVencidos=findViewById(R.id.MsjCantidadVencidos);
        texo_membresia=findViewById(R.id.textoMembresia);
        lista_usuariosVencidosCadaMembresia=findViewById(R.id.viewRegistrosVencidos);
        lista_usuariosVencidosCadaMembresia.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                id = Integer.parseInt(null);
            }
            else
            {
                id = extras.getInt("ID_vencidos");
                tipo = extras.getString("tipo_membresia");
            }
        }
        else
        {
            id = (int) savedInstanceState.getSerializable("ID_vencidos");
            tipo =(String) savedInstanceState.getSerializable("tipo_membresia");
        }


        bdDatos_membresia = new BDDatos_membresia(VerRegistrosVencidosAdmin.this);
        listaArrayMembresiasCompradas=new ArrayList<>();
        Mensaje("IDV "+String.valueOf(id));
        adapter_datosCada_membresia adapter=new adapter_datosCada_membresia(bdDatos_membresia.MostrarUserCadaMembresiasVencidos(id));
        lista_usuariosVencidosCadaMembresia.setAdapter(adapter);

                int cantidadUsers= bdDatos_membresia.cantidad_usuariosVencidos_cada_membresia(id);
            viewCantidadUserVencidos.setText(String.valueOf(cantidadUsers));

            if(cantidadUsers==0)
            {
                lista_usuariosVencidosCadaMembresia.setVisibility(View.GONE);
                msjVencidos.setVisibility(View.VISIBLE);
            }
            else
            {
                texo_membresia.setText("Membresia "+tipo);
                msjVencidos.setVisibility(View.GONE);
            }

    }
    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};
}