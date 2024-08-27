package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.fitgymapp.Adaptadores.adapter_carrito_productos;
import com.example.fitgymapp.Adaptadores.adapter_productos;
import com.example.fitgymapp.Entidades.Entidad_producto;
import com.example.fitgymapp.Entidades.Entidad_productosCarrito;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.ECField;
import java.util.ArrayList;

public class VerCarritoProductos extends AppCompatActivity {

    RecyclerView recycle_view;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave ="Yo digo";
    DatabaseReference myRef = database.getReference(miclave);
    TextView total;
    int precio_total = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_carrito_productos);
        total = findViewById(R.id.precio_total);
        recycle_view=findViewById(R.id.vercarrito);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));

        try {
            DesplegarProductosCarrito();
        }
        catch (Exception e)
        {

        }

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
                }else if(item.getItemId()==R.id.comprar_producto)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_productos_usuario.class);
                    startActivity(intento);
                }

            }
        });

    }

    private void DesplegarProductosCarrito() {
        myRef = database.getReference("CarritoUsuario");
        ArrayList<Entidad_productosCarrito> listaCarrito = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String miscarros = "";
                String aux = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    aux = postSnapshot.getKey();
                    Entidad_productosCarrito producto = postSnapshot.getValue(Entidad_productosCarrito.class);
                    listaCarrito.add(new Entidad_productosCarrito( producto.getQR_pro(),producto.getNombre_pro(),producto.getPrecio_pro()));
                    precio_total=precio_total+Integer.parseInt(producto.getPrecio_pro());
                }

                adapter_carrito_productos adapter  =new adapter_carrito_productos(listaCarrito);
                recycle_view.setAdapter(adapter);
                total.setText(String.valueOf(precio_total));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<Entidad_productosCarrito> vercarrito() {


        ArrayList<Entidad_productosCarrito> listaProductos = new ArrayList<>();

        Entidad_productosCarrito productos = null;

        for (int i =0 ; i <=10; i++)
        {
            productos = new Entidad_productosCarrito();
            productos.setQR_pro("10101"+ i);
            productos.setNombre_pro("nombre1: "+i);
            productos.setPrecio_pro("1000 "+i);
            listaProductos.add(productos);
        }


        return listaProductos;
    }
}