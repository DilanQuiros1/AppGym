package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import org.checkerframework.checker.units.qual.A;

public class EditarEliminar_producto_admin extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave ="Yo digo";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    DatabaseReference myRef = database.getReference(miclave);
    String qr=null;
    private StorageReference mstorage;
    TextView nom, precio, descripcion, cantidad;
    ImageView url_img;
    TextView QRCODE, seAgregoMasCantidad;
    private ProgressDialog mProgressDialog;
    private static final int GALLERY_INTENT=1;
    Button btn_eliminarPro, btn_editarPro;
    VariablesGlobales vg;
    String nueva_cantidad;
    int cantidad_agregar=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_eliminar_producto_admin);

        nom = findViewById(R.id.nombre_producto);
        precio = findViewById(R.id.precio_producto);
        descripcion = findViewById(R.id.descripcion_producto);
        url_img = findViewById(R.id.img_producto);
        QRCODE = findViewById(R.id.qrcode);
        seAgregoMasCantidad = findViewById(R.id.textViewnueva_cantidad);
        cantidad = findViewById(R.id.cantidad_producto);
        btn_editarPro = findViewById(R.id.editar_producto);
        btn_eliminarPro = findViewById(R.id.eliminar_producto);
        mstorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);
        vg=new VariablesGlobales();

        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                //qr = Integer.parseInt(null);
            }
            else
            {
                qr = extras.getString("QR");
                nueva_cantidad = extras.getString("CANTI");
            }
        }
        else
        {
            qr = (String) savedInstanceState.getSerializable("QR");
            nueva_cantidad = (String) savedInstanceState.getSerializable("CANTI");
        }
       if(nueva_cantidad!=null)
       {
           cantidad_agregar = Integer.parseInt(nueva_cantidad);
           seAgregoMasCantidad.setVisibility(View.VISIBLE);
           seAgregoMasCantidad.setText("Preciona Editar para agregar " +
                   nueva_cantidad+" productos");
       }
        if(qr!=null)
        {
            LeeObjetoEnFirebase(qr,cantidad_agregar );
        }

        url_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        Button scanButton = findViewById(R.id.escanear);

        scanButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(EditarEliminar_producto_admin.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditarEliminar_producto_admin.this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                startQRScanner();
            }
        });


        btn_editarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String QR = QRCODE.getText().toString();
                String nombre = nom.getText().toString();
                String prec = precio.getText().toString();
                String des = descripcion.getText().toString();
                String canti = cantidad.getText().toString();

                if(QR.equals("")||nombre.equals("")||prec.equals("")||des.equals("")||canti.equals(""))
                {
                    Mensaje("Llena todos los campos");
                }
                else
                {
                    boolean modifico_img = vg.isAgrego_img_usuario();
                    Uri uri = vg.getImg();
                    if(modifico_img==false)
                    {
                        Mensaje("Se edito de forma correcta");
                        String misma_imagen = vg.getHrf_imgProducto();
                        EditarProducto(QR, nombre,misma_imagen, prec,  des, canti);
                    }
                    else
                    { //edita imagen

                        Mensaje("Se Edito de forma correcta");
                        StorageReference filepath = mstorage.child("productos").child(uri.getLastPathSegment());
                        filepath.putFile(uri).addOnSuccessListener(taskSnapshot -> {


                            //obtenemos la URL de la imagen
                            filepath.getDownloadUrl().addOnSuccessListener(uri1 -> {
                                String downloadUrl = uri1.toString();
                                String url = downloadUrl;
                                EditarProducto(QR, nombre,url, prec,  des, canti);

                                Intent intento = new Intent(getApplicationContext(), VerProductosAdmin.class);
                                startActivity(intento);

                            }).addOnFailureListener(exception -> {
                                Mensaje("Error al obtener la URL de descarga: " + exception.getMessage());
                            });
                        }).addOnFailureListener(exception -> {
                            Mensaje("Error al subir la imagen: " + exception.getMessage());
                        });
                    }



                }

            }
        });

        btn_eliminarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String QR = QRCODE.getText().toString();
                if(QR.equals(""))
                {
                    Mensaje("El QR esta vacio, no se puede aliminar");
                }
                else
                {
                    Elimiar_producto(QR);
                    qr = null;
                    Intent intento = new Intent(getApplicationContext(), VerProductosAdmin.class);
                    startActivity(intento);
                    Mensaje("Se elimino el producto");
                }
            }
        });

        BottomNavigationView view = findViewById(R.id.nav_productos);
        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.Mis_productos)
                {
                    Intent intento = new Intent(getApplicationContext(), VerProductosAdmin.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.agregar_producto)
                {
                    Intent intento = new Intent(getApplicationContext(), RegistrarProductoAdmin.class);
                    startActivity(intento);
                }

            }
        });

    }

    public void Elimiar_producto(String qr)
    {
        myRef = database.getReference("Productos");
        myRef.child(qr).removeValue();
        myRef = database.getReference("CarritoUsuario");
        myRef.child(qr).removeValue();

    }

    public void LeeObjetoEnFirebase(String nombreobj,int cantidad_a_agregar) {
        // se supone que ya usted creo el objeto carro en su firebase
        myRef = database.getReference("Productos");
        myRef.child(nombreobj).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Entidad_producto producto = dataSnapshot.getValue(Entidad_producto.class);
                //MensajeOK(producto.getNombre()+" tiene un "+producto.getPrecio() );
                if(producto == null || producto.getID_QRCODE()==null||producto.getNombre()==null||producto.getPrecio()==null||
                        producto.getUrl_img()==null||producto.getDescripcion()==null||producto.getCantidad()==null){
                    Mensaje("Producto no encontrado");
                }
                else
                {

                    nom.setText(producto.getNombre());
                    precio.setText(producto.getPrecio());
                    descripcion.setText(producto.getDescripcion());
                    QRCODE.setText(producto.getID_QRCODE());
                    int valor = (Integer.parseInt(producto.getCantidad())+ cantidad_a_agregar);
                    cantidad.setText(String.valueOf(valor));

                    if (!isDestroyed() && !isFinishing()) {
                        Glide.with(EditarEliminar_producto_admin.this)
                                .load(producto.getUrl_img())
                                .into(url_img);
                        vg.setHrf_imgProducto(producto.getUrl_img());
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void EditarProducto(String qr, String nom,String url, String precio, String descripcion, String cantidad) {
        // se supone que ya usted creo el objeto carro en su firebase
        myRef = database.getReference("Productos");
        Entidad_producto  producto = new Entidad_producto(qr, nom,url, precio, descripcion, cantidad);
        myRef.child(qr).setValue(producto);

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};


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
                QRCODE.setText(scanContent);
                String valor = cantidad.getText().toString();

                    LeeObjetoEnFirebase(scanContent, 0);


            }
        }

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
        {

            Uri uri = data.getData();
            vg.setImg(uri);

            try {
                // Convierte la URI de la imagen a un objeto Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                vg.setAgrego_img(true);
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


}