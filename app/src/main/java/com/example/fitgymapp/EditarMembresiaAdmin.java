package com.example.fitgymapp;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitgymapp.BDMembresias.BDmembresias;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;

public class EditarMembresiaAdmin extends AppCompatActivity {

    EditText txtTipo, txtPrecio, txtAreas;
    Button btnGuarda, btnElimina;
    boolean correcto =false;
    Entidad_ListaMembresias membresias;
    BottomNavigationView view_admin;
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_membresia_admin);
        txtTipo = findViewById(R.id.txtTipo);
        txtPrecio=findViewById(R.id.txtPrecio);
        txtAreas=findViewById(R.id.txt_Areas);
        btnGuarda =findViewById(R.id.btnEditar);
        btnElimina =findViewById(R.id.btnEliminar);

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
            }
        }
        else
        {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        //Mensaje(String.valueOf(id));
        BDmembresias dbMembresias =new BDmembresias(EditarMembresiaAdmin.this);
        membresias = dbMembresias.verMembresia_editar(id);

        if(membresias != null)
        {
            txtTipo.setText(membresias.getTipo());
            txtAreas.setText(membresias.getPrecio());
            txtPrecio.setText(membresias.getAreas());
        }



        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtTipo.getText().toString().equals("")||txtAreas.getText().toString().equals("")||txtPrecio.getText().toString().equals(""))
                {
                    Mensaje("Llena todos los campos");

                }
                else
                {
                    correcto = dbMembresias.editarMembresia(id, txtTipo.getText().toString(), txtAreas.getText().toString(), txtPrecio.getText().toString());

                    if(correcto)
                    {
                        Mensaje("Registro modificado");
                        verRegistro();
                    }
                    else
                    {
                        Mensaje("Error de editar");
                    }

                }

            }
        });

        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean elimino = dbMembresias.EliminarMembresia(id);

                if(elimino)
                {
                    Mensaje("Se elimino de forma correcta");
                }
                else
                {
                    Mensaje("Error al aliminar");
                }
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
                }
            }
        });

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    private void verRegistro()
    {
        Intent intento = new Intent(getApplicationContext(), VerMembresiasAdmin.class);
        intento.putExtra("ID", id);
        startActivity(intento);
    }
}