package com.example.fitgymapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.Adaptadores.adapter_datosCada_membresia;
import com.example.fitgymapp.BDDatos_membresia.BDDatos_membresia;
import com.example.fitgymapp.Entidades.Entidad_usuarios_registrosCadamembresias;

import java.util.ArrayList;

public class VerRegistrosCadaMembresia extends AppCompatActivity {

    RecyclerView lista_usuariosCadaMembresia;
    ArrayList<Entidad_usuarios_registrosCadamembresias> listaArrayMembresiasCompradas;
    TextView viewCantidadUser, back, texo_membresia, texto_vencido;
    BDDatos_membresia bdDatos_membresia;
    ImageView btnVencidos;
    int id=0;
    String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registros_cada_membresia);
        lista_usuariosCadaMembresia=findViewById(R.id.ViewUser_cadaUsuarioMembresia);
        lista_usuariosCadaMembresia.setLayoutManager(new LinearLayoutManager(this));
        viewCantidadUser =findViewById(R.id.ViewCantidad_users);
        back=findViewById(R.id.Back);
        texo_membresia=findViewById(R.id.textoMembresia);
        texto_vencido=findViewById(R.id.texto_vencidos);
        btnVencidos=findViewById(R.id.btn_irRegistrosVencidos);

        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                id = Integer.parseInt(null);
            }
            else
            {
                id = extras.getInt("ID");
                tipo = extras.getString("tipo_membresia");
            }
        }
        else
        {
            id = (int) savedInstanceState.getSerializable("ID");
            tipo =(String) savedInstanceState.getSerializable("tipo_membresia");
        }

        try
        {

            texo_membresia.setText("Membresia "+tipo);
            texto_vencido.setText("Ver registros vencidos Mebresia "+tipo);
             bdDatos_membresia = new BDDatos_membresia(VerRegistrosCadaMembresia.this);
            listaArrayMembresiasCompradas=new ArrayList<>();

            adapter_datosCada_membresia adapter=new adapter_datosCada_membresia(bdDatos_membresia.MostrarUserCadaMembresias(id));
            lista_usuariosCadaMembresia.setAdapter(adapter);

           int cantidadUsers= bdDatos_membresia.cantidad_usuarios_cada_membresia(id);
           viewCantidadUser.setText(String.valueOf(cantidadUsers));

        }
        catch (Exception e)
        {
            Mensaje("Hubo un error");
        }

        btnVencidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), VerRegistrosVencidosAdmin.class);
                intento.putExtra("ID_vencidos", id);
                intento.putExtra("tipo_membresia",tipo);
                startActivity(intento);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Seleccionar_membresias_verRegistros.class);
                startActivity(intent);
            }
        });

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};
}