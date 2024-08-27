package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.fitgymapp.Adaptadores.adapter_datosCada_membresia;
import com.example.fitgymapp.Adaptadores.adapter_productos;
import com.example.fitgymapp.BDMembresias.BDmembresias;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;
import com.example.fitgymapp.Entidades.Entidad_datos_membresia;
import com.example.fitgymapp.Entidades.Entidad_producto;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ver_productos_usuario extends AppCompatActivity {


    RecyclerView lista_productos;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave ="Yo digo";
    DatabaseReference myRef = database.getReference(miclave);
    EditText buscar_producto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_productos_usuario);
        buscar_producto = findViewById(R.id.inputBuscar);
        lista_productos = findViewById(R.id.Lista_productos);
        lista_productos.setLayoutManager(new LinearLayoutManager(this));


        try {
            DesplegarProductos();
        }
        catch (Exception e)
        {

        }

        buscar_producto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No es necesario implementar este método, pero se debe dejar vacío.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Actualizar la variable con el texto ingresado
                String textoGuardado = buscar_producto.getText().toString();
                // Mensaje(textoGuardado);

                try
                {

                    DesplegarProductosBuscados(textoGuardado);

                }
                catch (Exception e)
                {
                    //Mensaje("Error");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No es necesario implementar este método, pero se debe dejar vacío.
            }
        });

        BottomNavigationView view = findViewById(R.id.nav_productos_usuario);
        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.escanear_para_compra)
                {
                    Intent intento = new Intent(getApplicationContext(), VerProductoParaComprar.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.mi_carrito)
                {
                    Intent intento = new Intent(getApplicationContext(), VerCarritoProductos.class);
                    startActivity(intento);
                }
            }
        });

    }


    private void DesplegarProductos() {
        myRef = database.getReference("Productos");
        ArrayList<Entidad_producto> listaProductos = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String miscarros = "";
                String aux = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    aux = postSnapshot.getKey();
                    Entidad_producto producto = postSnapshot.getValue(Entidad_producto.class);

                    listaProductos.add(new Entidad_producto( producto.getID_QRCODE(),producto.getNombre(),producto.getPrecio()));
                    //miscarros = miscarros + producto.getID_QRCODE() + " tiene un " + producto.getNombre() + "\n";
                }

                adapter_productos adapter_productos = new adapter_productos(listaProductos);
                lista_productos.setAdapter(adapter_productos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void DesplegarProductosBuscados(String letraInicial) {
        myRef = database.getReference("Productos");
        Query query = myRef.orderByKey().startAt(letraInicial).endAt(letraInicial + "\uf8ff");
        ArrayList<Entidad_producto> listaProductos = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String miscarros = "";
                String aux = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    aux = postSnapshot.getKey();
                    Entidad_producto producto = postSnapshot.getValue(Entidad_producto.class);

                    if (producto != null) {
                        if (producto.getNombre().startsWith(letraInicial)) {
                            listaProductos.add(new Entidad_producto( producto.getID_QRCODE(),producto.getNombre(),producto.getPrecio()));
                        }
                    }

                }

                adapter_productos adapter_productos = new adapter_productos(listaProductos);
                lista_productos.setAdapter(adapter_productos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void LeeObjetoEnFirebase(String letraInicial) {
        myRef = database.getReference("Carros");

        Query query = myRef.orderByKey().startAt(letraInicial).endAt(letraInicial + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Entidad_producto carro = snapshot.getValue(Entidad_producto.class);
                    if (carro != null) {
                        if (carro.getNombre().startsWith(letraInicial)) {
                            MensajeOK(carro.getNombre() + " tiene un " + carro.getID_QRCODE());
                            MensajeOK(snapshot.toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejo de errores en caso de cancelación
            }
        });
    }


    public ArrayList<Entidad_producto> verproductos() {//haciendo pruebas


        ArrayList<Entidad_producto> listaProductos = new ArrayList<>();

        Entidad_producto productos = null;

        for (int i =0 ; i <=10; i++)
        {
            productos = new Entidad_producto();
            productos.setID_QRCODE("1010"+ i);
            productos.setNombre("nombre: "+i);
            productos.setPrecio("100"+i);
            listaProductos.add(productos);
        }


        return listaProductos;
    }

        public void MensajeOK(String msg){
            View v1 = getWindow().getDecorView().getRootView();
            AlertDialog.Builder builder1 = new AlertDialog.Builder( v1.getContext());
            builder1.setMessage(msg);
            builder1.setCancelable(true);
            builder1.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {} });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            ;};

}