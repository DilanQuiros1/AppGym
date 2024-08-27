package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitgymapp.Entidades.Entidad_producto;
import com.example.fitgymapp.Entidades.Entidad_productosCarrito;
import com.example.fitgymapp.Entidades.VariablesGlobales;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class RegistrarProductoAdmin extends AppCompatActivity {
    TextView qr;
    EditText nombre, precio, descripcion, cantidad;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    boolean existe_producto;
    private StorageReference mstarege;
    private static final int GALLERY_INTENT = 1;
    private ProgressDialog mProgressDialog;
    String userInput;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave = "Yo digo";
    DatabaseReference myRef = database.getReference(miclave);
    VariablesGlobales vg;
    ImageView url_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto_admin);
        nombre = findViewById(R.id.nombre_producto);
        precio = findViewById(R.id.precio_producto);
        descripcion = findViewById(R.id.descripcion_producto);
        cantidad = findViewById(R.id.cantidad_producto);
        qr = findViewById(R.id.qrcode);
        url_img = findViewById(R.id.img_producto);
        Button guardar = findViewById(R.id.agregar_producto);
        Button scanButton = findViewById(R.id.escanear);
        DetectarModificacionesEnLaBase();
        mstarege = FirebaseStorage.getInstance().getReference();
        vg = new VariablesGlobales();
        mProgressDialog = new ProgressDialog(this);

        scanButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(RegistrarProductoAdmin.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(RegistrarProductoAdmin.this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                startQRScanner();
            }
        });

        url_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String QR = (String) qr.getText();
                String nom = nombre.getText().toString();
                String precio_pro = precio.getText().toString();
                String descripcion1 = descripcion.getText().toString();
                String canti = cantidad.getText().toString();

                verificaExistenciaEnFirebase(QR, new FirebaseCallback() {
                    @Override
                    public void onCallback(boolean existe) {

                        if (existe) {//si existe
                            MensajeConNumero(QR);
                        } else {

                            if (QR.equals("QR 12345..") || nom.equals("") || precio_pro.equals("") || descripcion1.equals("") || canti.equals("")) {
                                Mensaje("Llena todos los campos");
                            } else {

                                insertar(QR, nom, precio_pro, descripcion1, canti);

                            }

                        }
                    }
                });

                BottomNavigationView view1 = findViewById(R.id.nav_productos1);
                view1.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
                    @Override
                    public void onNavigationItemReselected(@NonNull MenuItem item) {
                        if(item.getItemId()==R.id.gym_main_page)
                        {
                            Intent intento = new Intent(getApplicationContext(), AdministradorMain.class);
                            startActivity(intento);
                        } else if(item.getItemId()==R.id.Mis_productos)
                        {
                            Intent intento = new Intent(getApplicationContext(), VerProductosAdmin.class);
                            startActivity(intento);
                        }

                    }
                });



            }
        });
    }

    private void startQRScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Escaneando código QR");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String scanContent = result.getContents();
                qr.setText(scanContent);

            }
        }

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            vg.setImg(uri);

            try {
                // Convierte la URI de la imagen a un objeto Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //vg.setAgrego_img(true);
                url_img.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startQRScanner();
            }
        }
    }



    void DetectarModificacionesEnLaBase() {
        //myRef.setValue("abcd");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Mensaje("Failed to read value." + error.toException());
            }
        });
    }


    public interface FirebaseCallback {
        void onCallback(boolean existe);
    }

    public void verificaExistenciaEnFirebase(String nombreobj, final FirebaseCallback callback) {
        myRef = database.getReference("Productos");
        myRef.child(nombreobj).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean existe = dataSnapshot.exists(); // Verifica si existe el dato en Firebase
                callback.onCallback(existe); // Llama a la devolución de llamada con el resultado
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCallback(false); // En caso de error, se asume que no existe
            }
        });
    }

    public void insertar(String qr, String nom, String precio, String descripcion, String cantidad)
    {

        Uri uri = vg.getImg();
        if (uri == null) {
            Mensaje("Agrega una imagen");

        } else {
            mProgressDialog.setTitle("Subiendo articulo");
            mProgressDialog.setMessage("Subiendo articulo a firebase");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            StorageReference filepath = mstarege.child("productos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                mProgressDialog.dismiss();

                //obtenemos la URL de la imagen
                filepath.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    String downloadUrl = uri1.toString();
                    //Log.i("DESCARGAAAAA", downloadUrl);//este se guarda en la base de datos
                    String url = downloadUrl;
                    Mensaje("Se registro de forma correcta");

                    myRef = database.getReference("Productos");
                    Entidad_producto carro = new Entidad_producto();
                    carro.setID_QRCODE(qr);
                    carro.setNombre(nom);
                    carro.setUrl_img(url);
                    carro.setPrecio(precio);
                    carro.setDescripcion(descripcion);
                    carro.setCantidad(cantidad);

                    myRef.child(qr).setValue(carro);
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }).addOnFailureListener(exception -> {
                    Mensaje("Error al obtener la URL de descarga: " + exception.getMessage());
                });
            }).addOnFailureListener(exception -> {
                mProgressDialog.dismiss();
                Mensaje("Error al subir la imagen: " + exception.getMessage());
            });
        }

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};




    public void MensajeConNumero(String qr) {
        View v1 = getWindow().getDecorView().getRootView();
        AlertDialog.Builder builder = new AlertDialog.Builder(v1.getContext());
        builder.setMessage("Producto ya existe, " +
                "Cuantos Articulos quieres agregar:");

        // Agregar un EditText al diálogo
        final EditText input = new EditText(v1.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Obtener el número ingresado por el usuario
                String userInput = input.getText().toString();
               // userInputListener.onInputReceived(userInput); // Pasar el valor ingresado al listener
                Intent intento = new Intent(getApplicationContext(), EditarEliminar_producto_admin.class);
                intento.putExtra("CANTI", userInput);
                intento.putExtra("QR", qr);
                startActivity(intento);
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




}