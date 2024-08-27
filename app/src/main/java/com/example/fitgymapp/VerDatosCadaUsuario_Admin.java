package com.example.fitgymapp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.BDusuario.BDusuarios;
import com.example.fitgymapp.Entidades.Entidad_datos_extras_usuario;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.io.File;

public class VerDatosCadaUsuario_Admin extends AppCompatActivity {

    TextView nombreUsu, pesoUsu,edadUsu, objetivoUsu, telefono;
    RelativeLayout CardMisDatos;
    Entidad_datos_extras_usuario datosExtrasUsu;
    VariablesGlobales vg;
    ImageView imgUsuario;
    TextView texto_agregar_datos;
    Button btnContactar;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_cada_usuario_admin);

        nombreUsu=findViewById(R.id.NombreUsu);
        pesoUsu=findViewById(R.id.PesoUsu);
        edadUsu=findViewById(R.id.EdadUsu);
        objetivoUsu=findViewById(R.id.ObjetivoUsu);
        btnContactar=findViewById(R.id.btn_contactar);
        CardMisDatos=findViewById(R.id.card_datos);
        texto_agregar_datos=findViewById(R.id.texto_opciones);
        telefono=findViewById(R.id.TelefonoUsu);
        imgUsuario=findViewById(R.id.imageUsuario);

        vg=new VariablesGlobales();

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

        BDusuarios bDusuarios=new BDusuarios(VerDatosCadaUsuario_Admin.this);
        datosExtrasUsu=bDusuarios.VerMisDatosExtras(id);

        try {
            if (datosExtrasUsu!=null)
            {
                nombreUsu.setText(datosExtrasUsu.getNombreUsu());
                pesoUsu.setText(datosExtrasUsu.getPeso());
                objetivoUsu.setText(datosExtrasUsu.getObjetivo());
                edadUsu.setText(datosExtrasUsu.getEdad());
                telefono.setText(datosExtrasUsu.getTelefono());

                File directory = getDir("Images_usuarios", Context.MODE_PRIVATE);
                File myPath = new File(directory, datosExtrasUsu.getUrlImg_usuario()+".jpg");
                Bitmap bitmap = BitmapFactory.decodeFile(myPath.getAbsolutePath());
                imgUsuario.setImageBitmap(bitmap);

            }
            else
            {
                CardMisDatos.setVisibility(View.GONE);
                btnContactar.setVisibility(View.GONE);
                texto_agregar_datos.setText("El usuario aun no agrego datos");
            }

        }
        catch (Exception e)
        {
           // Mensaje(e.toString());
        }

        btnContactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactarUsuario_Admin.class);
                intent.putExtra("ID_Contacto", Integer.parseInt((String) telefono.getText()) );
                startActivity(intent);

            }
        });



    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};
}