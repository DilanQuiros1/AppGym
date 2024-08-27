package com.example.fitgymapp;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitgymapp.BDMembresias.BDmembresias;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.util.ArrayList;

public class Agregar_Membresia_Admin extends AppCompatActivity {

    BottomNavigationView view_admin;

    EditText tipo_membersia, precio, areas, descripcion;
    Button btn_agregar, verMembresias, membresiasCompradas;
    BDmembresias DB;
    static String varlo_tipoMembresia;
    Spinner s1;
    ArrayList<Entidad_ListaMembresias> listaArrayMembresiasParausu;
    VariablesGlobales vg;
   String valortipomembresia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_membresia_admin);

        tipo_membersia=findViewById(R.id.tipoMembresiaNueva);
        precio = findViewById(R.id.precio);
        areas = findViewById(R.id.areas);
        descripcion = findViewById(R.id.descripcion);
        btn_agregar = findViewById(R.id.btnAgregarMembresia);
        verMembresias = findViewById(R.id.btnVerMembresia);
        s1 = (Spinner) findViewById(R.id.sp);
        membresiasCompradas = findViewById(R.id.btnVerMembresiasCompradas);
        DB = new BDmembresias(this);
        vg=new VariablesGlobales();

        CargarSpinner();

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String precio_membresia = precio.getText().toString();
                String area = areas.getText().toString();
                String descrip = descripcion.getText().toString();


                try
                {

                    if(vg.getSpinnerTipo().equals("")&&tipo_membersia.equals("")||precio_membresia.equals("")||area.equals("")||descrip.equals(""))
                        Mensaje("Please enter all fields");
                    else
                    {
                        if(tipo_membersia.isEnabled())
                        {
                            valortipomembresia=tipo_membersia.getText().toString();
                        }
                        else
                        {
                            valortipomembresia = vg.getSpinnerTipo();
                        }

                        Boolean insert =DB.AgregarMembresia(valortipomembresia, precio_membresia, area, descrip);

                        if(insert==true)
                        {
                            Mensaje("Added successfull");

                        }
                        else
                        {
                            Mensaje("Error en insertar");
                        }
                        Mensaje(getVarlo_tipoMembresia()+" "+ precio_membresia);


                    }

                }
                catch (Exception e)
                {
                    Mensaje(e.toString());
                }

            }
        });

        verMembresias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), VerMembresiasAdmin.class);
                startActivity(intento);
            }
        });

        membresiasCompradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), Todas_membresias_compradas_admin.class);
                startActivity(intento);
            }
        });

        view_admin=findViewById(R.id.nav_admin);
        view_admin.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.home)
                {
                    Intent intento = new Intent(getApplicationContext(), AdministradorMain.class);
                    startActivity(intento);

                } else if(menuItem.getItemId()==R.id.membresias_admin)
                {
                    Intent intento = new Intent(getApplicationContext(), VerMembresiasAdmin.class);
                    startActivity(intento);
                }else if(menuItem.getItemId()==R.id.entrenadores_admin)
                {
                    Intent intento = new Intent(getApplicationContext(), VerEntrenadoresDisponibles.class);
                    startActivity(intento);
                }
            }
        });

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    public static String getVarlo_tipoMembresia() {
        return varlo_tipoMembresia;
    }

    public static void setVarlo_tipoMembresia(String varlo_tipoMembresia) {
        Agregar_Membresia_Admin.varlo_tipoMembresia = varlo_tipoMembresia;
    }

    private void CargarSpinner() {
        //Spinner s1;

        BDmembresias dbMembresias=new BDmembresias(Agregar_Membresia_Admin.this);
        listaArrayMembresiasParausu=new ArrayList<>();

        ArrayList<Entidad_ListaMembresias> listaMembresias = dbMembresias.ObtenerNombresMembresias();

        final String[] presidentes = new String[listaMembresias.size()+1];

        for (int i = 0; i < listaMembresias.size(); i++) {
            presidentes[0]="Seleccione";
            presidentes[i+1] = listaMembresias.get(i).getTipo();//revisar
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, presidentes);


        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                    if(presidentes[position].equals("Seleccione"))
                    {
                        vg.setSpinnerTipo("");
                        tipo_membersia.setEnabled(true);
                    }
                    else
                    {
                        vg.setSpinnerTipo(presidentes[position]);
                        tipo_membersia.setEnabled(false);
                        tipo_membersia.setText(presidentes[position]);
                    }

                  ;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        s1.setAdapter(adapter);

    }// fin de CargarSpinner

}
