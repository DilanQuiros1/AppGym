package com.example.fitgymapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fitgymapp.BDMembresias.BDmembresias;
import com.example.fitgymapp.BDcompraMembresias.BDcompra_membresia;
import com.example.fitgymapp.Entidades.VariablesGlobales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeMain extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    BottomNavigationView view;
    ImageButton btn;
    GoogleMap mMap;
    VariablesGlobales vg;
    BDmembresias DB;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave = "Yo digo";
    DatabaseReference myRef = database.getReference(miclave);
    TextView agregartelefono, vertel;

    private static final int REQUEST_PHONE_CALL = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 123;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 113;
    TextView contactenos, hubicacion, verTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        btn=findViewById(R.id.buttonIrAdmin);
        view = findViewById(R.id.nav_main);
        contactenos=findViewById(R.id.Contactenos);
        hubicacion=findViewById(R.id.ir_hubicacion);
        verTel=findViewById(R.id.vertelefono);
        DB = new BDmembresias(this);
        vg=new VariablesGlobales();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ver_telefono();

        if (ContextCompat.checkSelfPermission(HomeMain.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Si el permiso no está concedido, solicitarlo al usuario
            ActivityCompat.requestPermissions(HomeMain.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            // El permiso ya está concedido, proceder con la lógica que necesita el permiso
            // Por ejemplo, iniciar la obtención de la ubicación
        }


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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), LoginAdmin.class);
                startActivity(intento);
//                File directory = getDir("Images_entrenador", Context.MODE_PRIVATE);
//                Log.i("PATH", String.valueOf(directory));
            }
        });

        hubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        contactenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(HomeMain.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Si no está concedido, solicitar el permiso al usuario
                    ActivityCompat.requestPermissions(HomeMain.this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    // Si ya está concedido, puedes realizar la llamada telefónica aquí
                    String tel = verTel.getText().toString();
                    if(tel==null||tel.equals(""))
                    {
                        Mensaje("Aun no existe un numero telefonico");
                    }
                    else
                    {
                        hacerLlamadaTelefonica(tel);
                    }


                }

            }
        });
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

        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido

            } else {
                // Permiso denegado
            }
        }


    }






    @Override
    protected void onStart(){
        super.onStart();
            BDcompra_membresia bd=new BDcompra_membresia(HomeMain.this);
           String fechaAc=fecha_actual();
           bd.actualizarEstadoDeCompras(fechaAc);//para que inicie y edite los usuarios si estan venciodos o no

          int cantidad=DB.cantidad_membresia();
          if(cantidad==0)
          {//agregue una membresia
              DB.AgregarMembresia("Premium", "100", "Funcional", "Incluye Todo");
          }
           // Mensaje("No se actualizo "+ actualizar);

    };

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};


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


    public String fecha_actual()
    {
        String nuevaFechaFormateada="";
        Calendar calendar = Calendar.getInstance();
        Date fechaInicio = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        nuevaFechaFormateada = dateFormat.format(fechaInicio);

        return nuevaFechaFormateada;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng Inicio = new LatLng(9.386368,-83.706797);
        mMap.addMarker(new MarkerOptions().position(Inicio).title("FIT GYM"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(Inicio));

    }



    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        boolean isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isProviderEnabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            Mensaje("provider");
        }

        // Verifica los permisos
        if (ContextCompat.checkSelfPermission(HomeMain.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

          //la hubicacion
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            if (lastKnownLocation != null) {
                // Usa la ubicación actual como inicio
                LatLng inicio = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                // Coordenadas del destino fijo
                LatLng destinoFijo = new LatLng(9.3887372, -83.7090262);

                // Crear un Uri para la ruta en Google Maps
                String url = "http://maps.google.com/maps?saddr=" + inicio.latitude + "," + inicio.longitude
                        + "&daddr=" + destinoFijo.latitude + "," + destinoFijo.longitude;


                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            } else {

            }
        } else {
            // Solicitar permisos si aún no se han concedido
            ActivityCompat.requestPermissions(HomeMain.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
                ActivityCompat.requestPermissions(HomeMain.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
        }

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    void ver_telefono(){
        myRef = database.getReference("Numero Gym");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                if(value!=null)
                {
                    verTel.setText(value);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

    }

}