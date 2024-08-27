package com.example.fitgymapp;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.BDcompraMembresias.BDcompra_membresia;
import com.example.fitgymapp.BDusuario.BDusuarios;
import com.example.fitgymapp.Entidades.EntidadMembresiaActualUsuario;
import com.example.fitgymapp.Entidades.Entidad_datos_extras_usuario;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.io.File;

public class Mi_perfil_usuario extends AppCompatActivity {

    BottomNavigationView view;
    TextView msj, tipoMembresia, areas, vencimiento, NombreUsuario, CorreoUsuario;
    VariablesGlobales vg;
    LinearLayout linearViewMembresia;
    EntidadMembresiaActualUsuario membresiaActual;
    Entidad_datos_extras_usuario datosUsuario, imgUsu;
    ImageView btn_mis_compras, btn_mis_datos, imgusuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil_usuario);
        view=findViewById(R.id.nav_main_usu);
        btn_mis_compras = findViewById(R.id.btnVerMembresiasCompradas);
        vg = new VariablesGlobales();
        linearViewMembresia = findViewById(R.id.ViewMembresia);
        imgusuario = findViewById(R.id.Img_usu);
        msj = findViewById(R.id.text_mensaje);
        tipoMembresia = findViewById(R.id.tipo_membresiaActual);
        areas = findViewById(R.id.AreaMembresiaActual);
        vencimiento = findViewById(R.id.FechaVencimientoMembresiaActual);
        NombreUsuario = findViewById(R.id.nombre_Usuario);
        CorreoUsuario = findViewById(R.id.correo_usu);
        btn_mis_datos = findViewById(R.id.btnVerMisDatos);
        int id_usuario = Integer.parseInt(vg.getID());

        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.membresias)
                {

                    Intent intento = new Intent(getApplicationContext(), ver_membresias_usuarios.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.entrenadores)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_entrenadoresUsuarios.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                    startActivity(intento);
                }

            }
        });
        BDusuarios bDusuarios =new BDusuarios(Mi_perfil_usuario.this);
        datosUsuario = bDusuarios.VerMisNobre_Correo(id_usuario);
        imgUsu = bDusuarios.VerMiImagenPerfil(id_usuario);

        BDcompra_membresia bDcompraMembresia = new BDcompra_membresia(Mi_perfil_usuario.this);
        membresiaActual = bDcompraMembresia.VerMembresiaActualUsuario(id_usuario);

        NombreUsuario.setText(datosUsuario.getNombreUsu());
        CorreoUsuario.setText(datosUsuario.getCorreo());

        try {
            if (membresiaActual != null) {
                msj.setVisibility(View.INVISIBLE); // ya compro membresia
                vg.setCompra_membresia_realizada(true);

                tipoMembresia.setText(membresiaActual.getTipoMembresiaActual());
                areas.setText(membresiaActual.getAreasMembresiaActual());
                vencimiento.setText(membresiaActual.getVencimientoMembresiaActual());

                File directory = getDir("Images_usuarios", Context.MODE_PRIVATE);
                File myPath = new File(directory, imgUsu.getUrlImg_usuario()+".jpg");
                Bitmap bitmap = BitmapFactory.decodeFile(myPath.getAbsolutePath());
                imgusuario.setImageBitmap(bitmap);

            } else {
                vg.setCompra_membresia_realizada(false);
                linearViewMembresia.setVisibility(View.GONE);//no ha comprado membresia
                msj.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            //Mensaje(e.getMessage());
        }

        btn_mis_compras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), Mis_MembresiasCompradas_usuario.class);
                startActivity(intento);
            }
        });


                btn_mis_datos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intento = new Intent(getApplicationContext(), Mis_datos_extra_usuario.class);
                        startActivity(intento);
                    }
                    });

        }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

}
