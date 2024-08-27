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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitgymapp.BDusuario.BDusuarios;
import com.example.fitgymapp.Entidades.Entidad_datos_extras_usuario;

import java.util.ArrayList;

public class ContactarUsuario_Admin extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_PHONE_CALL = 1;

    EditText numTelefono, msj;
    int id=0;
    Entidad_datos_extras_usuario usuario;
    Spinner spContacto;
    ArrayList<Entidad_datos_extras_usuario> listaArrayContactoParausu;
    ImageView mssj, llamar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar_usuario_admin);
        numTelefono=findViewById(R.id.IngresarTelefono);
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
                id = Integer.parseInt(null);
            }
            else
            {
                id = extras.getInt("ID_Contacto");
            }
        }
        else
        {
            id = (int) savedInstanceState.getSerializable("ID_Contacto");
        }

        String tel = String.valueOf(id);
        if (tel!=null)
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
                if (ContextCompat.checkSelfPermission(ContactarUsuario_Admin.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Si no está concedido, solicitar el permiso al usuario
                    ActivityCompat.requestPermissions(ContactarUsuario_Admin.this,
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

    }

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

        BDusuarios bDusuarios=new BDusuarios(ContactarUsuario_Admin.this);
        listaArrayContactoParausu=new ArrayList<>();

        ArrayList<Entidad_datos_extras_usuario> listaContactos = bDusuarios.ContactosTodosUsuario();


        final String[] contactos = new String[listaContactos.size()+1];
        final String[] contactosTelefono = new String[listaContactos.size()+1];

        for (int i = 0; i < listaContactos.size(); i++) {
            contactos[0]="Seleccione";
            contactos[i+1] = "ID: "+ listaContactos.get(i).getId_usario_tb_datos()+" | "+listaContactos.get(i).getNombreUsu()+" | "+listaContactos.get(i).getTelefono();
            contactosTelefono[i+1]=listaContactos.get(i).getTelefono();

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


    public void Mensaje(String msg){Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

}