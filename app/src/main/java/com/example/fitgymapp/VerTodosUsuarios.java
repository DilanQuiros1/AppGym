package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.Adaptadores.adapter_datosCada_membresia;
import com.example.fitgymapp.BDDatos_membresia.BDDatos_membresia;
import com.example.fitgymapp.Entidades.Entidad_usuarios_registrosCadamembresias;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class VerTodosUsuarios extends AppCompatActivity {

    RecyclerView listaTodosusuarios;
    ArrayList<Entidad_usuarios_registrosCadamembresias> listaArrayMembresiasCompradas;
    BDDatos_membresia bdDatos_membresia;
    EditText buscar;
    BottomNavigationView view_admin;
    TextView cantidad_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_todos_usuarios);
        listaTodosusuarios=findViewById(R.id.vertodosusuarios);
        buscar=findViewById(R.id.inputBuscar);
        cantidad_user = findViewById(R.id.ViewCantidad_users);
        listaTodosusuarios.setLayoutManager(new LinearLayoutManager(this));


        bdDatos_membresia = new BDDatos_membresia(VerTodosUsuarios.this);
        listaArrayMembresiasCompradas=new ArrayList<>();

     try {
         adapter_datosCada_membresia adapter=new adapter_datosCada_membresia(bdDatos_membresia.MostrarTodosUserActivos());
         listaTodosusuarios.setAdapter(adapter);

         int cantidadUsers= bdDatos_membresia.cantidad_todos_usuarios();
         cantidad_user.setText(String.valueOf(cantidadUsers));

     }
     catch (Exception e)
     {

     }

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton);
                RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
                if (rb1.isChecked()) {

                    adapter_datosCada_membresia adapter=new adapter_datosCada_membresia(bdDatos_membresia.MostrarTodosUserActivos());
                    listaTodosusuarios.setAdapter(adapter);

                }
                if (rb2.isChecked()) {
                    adapter_datosCada_membresia adapter=new adapter_datosCada_membresia(bdDatos_membresia.MostrarTodosUserVencidos());
                    listaTodosusuarios.setAdapter(adapter);

                }
            }
        });

        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No es necesario implementar este método, pero se debe dejar vacío.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Actualizar la variable con el texto ingresado
               String textoGuardado = buscar.getText().toString();
               // Mensaje(textoGuardado);

              try
              {
                  adapter_datosCada_membresia adapter=new adapter_datosCada_membresia(bdDatos_membresia.BuscarUserID(textoGuardado));
                  listaTodosusuarios.setAdapter(adapter);
                  if (textoGuardado.equals(""))
                  {
                      adapter=new adapter_datosCada_membresia(bdDatos_membresia.MostrarTodosUserActivos());
                      listaTodosusuarios.setAdapter(adapter);
                  }
              }
              catch (Exception e)
              {
                  Mensaje("Error");
              }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No es necesario implementar este método, pero se debe dejar vacío.
            }
        });

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
                }else if(menuItem.getItemId()==R.id.home)
                {
                    Intent intento = new Intent(getApplicationContext(), AdministradorMain.class);
                    startActivity(intento);
                }
            }
        });

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();}
}