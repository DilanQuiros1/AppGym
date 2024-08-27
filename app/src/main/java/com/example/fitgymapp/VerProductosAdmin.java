package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitgymapp.Adaptadores.adapter_carrito_productos;
import com.example.fitgymapp.Adaptadores.adapter_ver_productos_admin;
import com.example.fitgymapp.Entidades.Entidad_producto;
import com.example.fitgymapp.Entidades.Entidad_productosCarrito;
import com.example.fitgymapp.Entidades.VariablesGlobales;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerProductosAdmin extends AppCompatActivity {

    RecyclerView recycle_view;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave ="Yo digo";
    DatabaseReference myRef = database.getReference(miclave);
    TextView total;
    Button btn_ir_editar;
    int precio_total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_productos_admin);
        btn_ir_editar = findViewById(R.id.irEditarProducto);
        recycle_view=findViewById(R.id.verProductos);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));

        try {
            DesplegarProductosCarrito();
        }
        catch (Exception e)
        {

        }

        btn_ir_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intento = new Intent(getApplicationContext(), EditarEliminar_producto_admin.class);
                startActivity(intento);
            }
        });

        BottomNavigationView view = findViewById(R.id.nav_productos);
        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), AdministradorMain.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.agregar_producto)
                {
                    Intent intento = new Intent(getApplicationContext(), RegistrarProductoAdmin.class);
                    startActivity(intento);
                }

            }
        });

    }

    private void DesplegarProductosCarrito() {
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

                }

                adapter_ver_productos_admin adapter  =new adapter_ver_productos_admin(listaProductos);
                recycle_view.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}