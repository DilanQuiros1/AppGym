package com.example.fitgymapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.fitgymapp.Entidades.Entidad_producto;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdministradorMain extends AppCompatActivity {

    BottomNavigationView view_admin;
    ArrayList<Entidad_ListaMembresias> listaArrayMembresiasParausu;
    Button btndatosMe, btn_ir_agregarEntrenador, vertodos, productos, ver_proAdmin, irmain;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave = "Yo digo";
    DatabaseReference myRef = database.getReference(miclave);
    TextView agregartelefono, vertel, usuario_admin, password_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_main);
        btndatosMe=findViewById(R.id.VerdatosMembresia);
        btn_ir_agregarEntrenador=findViewById(R.id.AgregarEntrenador);
        vertodos=findViewById(R.id.vertodos);
        productos=findViewById(R.id.agregar_producto);
        ver_proAdmin=findViewById(R.id.ver_productos_admin);
        usuario_admin=findViewById(R.id.usuario_gym);
        password_admin=findViewById(R.id.passwrod_gym);
        agregartelefono = findViewById(R.id.agregar_telefono_gym);
        vertel = findViewById(R.id.ver_telefono_gym);
        irmain = findViewById(R.id.ir_homemain);
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

        ver_telefono();

        agregartelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MensajeConNumero("Ingrese el nuevo numero de telefono del Gym","Numero Gym");
            }
        });

        btn_ir_agregarEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), AgregarEntrenador_admin.class);
                startActivity(intento);
            }
        });

        vertodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), VerTodosUsuarios.class);
                startActivity(intento);
            }
        });


        btndatosMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), Seleccionar_membresias_verRegistros.class);
                startActivity(intento);
            }
        });

        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), RegistrarProductoAdmin.class);
                startActivity(intento);
            }
        });


        ver_proAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), VerProductosAdmin.class);
                startActivity(intento);
            }
        });

        irmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                startActivity(intento);
            }
        });

        usuario_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MensajeConNumero("Ingrese el nuevo ID para administradores","IDAdmin");
            }
        });

        password_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MensajePasswordAdmin("Ingresa el nuevo Password para administradores");

            }
        });

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    };

    public void MensajeConNumero(String msj, String database) {
        View v1 = getWindow().getDecorView().getRootView();
        AlertDialog.Builder builder = new AlertDialog.Builder(v1.getContext());
        builder.setMessage(msj);

        // Agregar un EditText al diálogo
        final EditText input = new EditText(v1.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Obtener el número ingresado por el usuario
                String userInput = input.getText().toString();

                EscribirEnFireBaseClaveValor(database,userInput);

            }
        });

        // Botón de Cancelar
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Cerrar el diálogo sin hacer nada
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void MensajePasswordAdmin(String msj) {
        View v1 = getWindow().getDecorView().getRootView();
        AlertDialog.Builder builder = new AlertDialog.Builder(v1.getContext());
        builder.setMessage(msj);

        // Agregar un EditText al diálogo
        final EditText input = new EditText(v1.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Obtener el número ingresado por el usuario
                String userInput = input.getText().toString();

                EscribirEnFireBaseClaveValor("PasswordAdmin",userInput);

            }
        });

        // Botón de Cancelar
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Cerrar el diálogo sin hacer nada
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    void EscribirEnFireBaseClaveValor(String aux1, String aux2){
        myRef = database.getReference(aux1);
        myRef.setValue(aux2);
        // Read from the database
        myRef = database.getReference(aux1);
        final String aux3 = aux1;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Mensaje(aux3+": " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Mensaje("Failed to read value."+ error.toException());
            }
        });
    }

    void ver_telefono(){
        myRef = database.getReference("Numero Gym");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
               if(value!=null)
               {
                   vertel.setText(value);
               }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

    }

}