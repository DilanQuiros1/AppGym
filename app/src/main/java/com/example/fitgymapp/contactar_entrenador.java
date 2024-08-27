package com.example.fitgymapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.BDcompraMembresias.BDcompra_membresia;
import com.example.fitgymapp.BDentranadores.BDentrenador;
import com.example.fitgymapp.Entidades.Entidad_datos_extras_usuario;
import com.example.fitgymapp.Entidades.Entidad_entrenador;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class contactar_entrenador extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_PHONE_CALL = 1;
    EditText msj, numTelefono;
    String tel="";
    TextView txt;
    Entidad_datos_extras_usuario usuario;
    Spinner spContacto;
    ArrayList<Entidad_datos_extras_usuario> listaArrayContactoParausu;
    ImageView mssj, llamar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar_entrenador);

        numTelefono=findViewById(R.id.IngresarTel);
        txt=findViewById(R.id.textoCon);
        msj=findViewById(R.id.IngresarMensage);
        spContacto=findViewById(R.id.spContactos);

        mssj=findViewById(R.id.enviarMsjContacto);
        llamar=findViewById(R.id.llamarContacto);

        CargarSpinner();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
        }

        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {

            }
            else
            {
                tel = extras.getString("Tel_E");
            }
        }
        else
        {
            tel = (String) savedInstanceState.getSerializable("Tel_E");
        }

           if(tel!=null)
           {
               numTelefono.setText(tel);
           }

        mssj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numTelefono.getText().toString().equals("")&&msj.getText().toString().equals(""))
                {
                    Mensaje("Ingresa datos en ambos textos");
                }
                else
                {
                    enviarMensaje(numTelefono.getText().toString(), msj.getText().toString());
                }

            }
        });


        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Verificar si el permiso ya está concedido
                if (ContextCompat.checkSelfPermission(contactar_entrenador.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Si no está concedido, solicitar el permiso al usuario
                    ActivityCompat.requestPermissions(contactar_entrenador.this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    // Si ya está concedido, puedes realizar la llamada telefónica aquí
                    if(numTelefono.getText().toString().equals(""))
                    {
                        Mensaje("Dijita un numero de telefono");
                    }
                    else
                    {
                        hacerLlamadaTelefonica(numTelefono.getText().toString());

                    }
                }


            }
        });

        BottomNavigationView view = findViewById(R.id.nav_main);
        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.miPerfil)
                {
                    Intent intento = new Intent(getApplicationContext(), Mi_perfil_usuario.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.membresias)
                {

                    Intent intento = new Intent(getApplicationContext(), ver_membresias_usuarios.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.entrenadores)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_entrenadoresUsuarios.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.gym_productos)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_productos_usuario.class);
                    startActivity(intento);
                }

            }
        });



    }

    private void enviarMensaje(String numeroDestino, String mensaje) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numeroDestino, null, mensaje, null, null);
            Toast.makeText(this, "Mensaje enviado con éxito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permiso concedido, puedes enviar mensajes SMS.
//            } else {
//                // Permiso denegado, muestra un mensaje de error o solicita permiso nuevamente.
//                Toast.makeText(this, "Permiso de SMS denegado", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    // Método para manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Verificar el código de solicitud
        if (requestCode == REQUEST_PHONE_CALL) {
            // Verificar si el permiso fue concedido
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, puedes realizar la llamada telefónica
               // hacerLlamadaTelefonica("72297859");
            } else {
                // Permiso denegado, muestra un mensaje al usuario
                Toast.makeText(this, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, puedes enviar mensajes SMS.
            } else {
                // Permiso denegado, muestra un mensaje de error o solicita permiso nuevamente.
                Toast.makeText(this, "Permiso de SMS denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void CargarSpinner() {

        BDentrenador bDusuarios=new BDentrenador(contactar_entrenador.this);
        listaArrayContactoParausu=new ArrayList<>();

        ArrayList<Entidad_entrenador> listaContactos = bDusuarios.ContactosTodosEntrenador();


        final String[] contactos = new String[listaContactos.size()+1];
        final String[] contactosTelefono = new String[listaContactos.size()+1];

        for (int i = 0; i < listaContactos.size(); i++) {
            contactos[0]="Seleccione";
            contactos[i+1] = "ID: "+ listaContactos.get(i).getId_entrenador()+" | "+listaContactos.get(i).getNombre_entrenador()+" | "+listaContactos.get(i).getTel_entrenador();
            contactosTelefono[i+1]=listaContactos.get(i).getTel_entrenador();

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, contactos);


        spContacto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                    numTelefono.setText(contactosTelefono[position]);

                ;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spContacto.setAdapter(adapter);

    }// fin de CargarSpinner



    private void hacerLlamadaTelefonica(String num) {
        // Número de teléfono al que deseas llamar
        String numeroTelefono = "123456789"; // Reemplaza con el número al que quieres llamar

        // Crear un Intent para realizar la llamada
        Intent intentLlamada = new Intent(Intent.ACTION_CALL);
        intentLlamada.setData(Uri.parse("tel:" + num));

        try {
            startActivity(intentLlamada);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void MarcaryLlamar(String numero) {
        Intent i = new
                Intent(android.content.Intent.ACTION_CALL,
                Uri.parse("tel:" + numero));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(i);
    };


    public void Mensaje(String msg){Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

}