package com.example.fitgymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class VerProductoParaComprar extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave ="Yo digo";
    DatabaseReference myRef = database.getReference(miclave);
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String qr=null;
    String ca=null;
    TextView nom, precio, descripcion, cantidad, tel_usuario;
    ImageView url_img;
    TextView QRCODE;
    Button btn_agregarCarrito, btn_eliminarPro_de_carrito, escanear_pro, btn_comprar;
    VariablesGlobales vg;

    String clieID ="Ad7pJsB_173mUVqGevpwvRlt-gDwxunCsnJq5LCn90_tD_JJmSFf-cdgZDkntftswcTRLQ0_oQNl-AQx";
    private static final String TAG = "MyTag";
    PaymentButtonContainer paymentButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto_para_comprar);
        nom = findViewById(R.id.nombre_producto);
        precio = findViewById(R.id.precio_producto);
        descripcion = findViewById(R.id.descripcion_producto);
        url_img = findViewById(R.id.img_producto);
        QRCODE = findViewById(R.id.qrcode);
        tel_usuario = findViewById(R.id.numero_telefono_usuario);
        escanear_pro = findViewById(R.id.escanear);
        cantidad = findViewById(R.id.cantidad_producto);
        btn_agregarCarrito = findViewById(R.id.agregarALcarrito);

        paymentButtonContainer = findViewById(R.id.payment_button_container);

        btn_comprar = findViewById(R.id.comprar_producto);
        btn_eliminarPro_de_carrito = findViewById(R.id.Eliminar_de_carrito);
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
                ca = extras.getString("CA");
            }
        }
        else
        {
            qr = (String) savedInstanceState.getSerializable("QR");
            ca = (String) savedInstanceState.getSerializable("CA");
        }

        if(qr!=null)
        {
            LeeObjetoEnFirebase(qr);
        }

        if(ca!=null)//si no es nulo
        {
            btn_agregarCarrito.setVisibility(View.GONE);//no puede agregar al carrito
            btn_eliminarPro_de_carrito.setVisibility(View.VISIBLE);
        }


        btn_agregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String QR = QRCODE.getText().toString();
                String nombre = nom.getText().toString();
                String prec = precio.getText().toString();
                String img = vg.getHrf_imgProducto();
                String des = descripcion.getText().toString();
                String canti = cantidad.getText().toString();

                if(QR.equals("")||nombre.equals("")||prec.equals("")||des.equals("")||canti.equals("")||img.equals(""))
                {
                    MensajeOK("Llena todos los campos");
                }
                else
                {
                    verificaExistenciaEnFirebase("CarritoUsuario",QR, new RegistrarProductoAdmin.FirebaseCallback() {
                        @Override
                        public void onCallback(boolean existe) {

                            if (existe) {//si existe
                               Mensaje("Ya existe en tu carrito");
                            }
                            else {
                                AgregarProductoCarrito(QR, nombre, img, prec,  des, canti);
                            }
                        }
                    });

                }
            }
        });

        btn_eliminarPro_de_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String QR = QRCODE.getText().toString();
                if(QR.equals("QR 12345.."))
                {
                    Mensaje("Verifica producto");
                }
                else
                {
                    Elimiar_producto(QR);
                    Mensaje("Producto eliminado de tu carrito");
                }

            }
        });

        paymentButtonContainer.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        Log.d(TAG, "create: ");
                        ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.USD)
                                                        .value(precio.getText().toString())
                                                        .build()
                                        )
                                        .build()
                        );
                        OrderRequest order = new OrderRequest(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.d(TAG, String.format("CaptureOrderResult: %s", result));
                                //  Toast.makeText(activity_comprar_membresia_usuario.this, "Successful", Toast.LENGTH_SHORT).show();

                                String tel = tel_usuario.getText().toString();
                                String prec = precio.getText().toString();
                                String QR = QRCODE.getText().toString();
                                String nombre = nom.getText().toString();
                                if(tel.equals("")||prec.equals("")||QR.equals("")||nombre.equals(""))
                                {
                                    Mensaje("Revisa que completes todos los datos");
                                }
                                else
                                {
                                    enviarMensaje(tel, "Compraste el producto "+nombre+" QR: "+QR+" Precio: "+ prec);
                                    Elimiar_producto(QR);
                                }

                            }
                        });
                    }
                }
        );

        btn_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = tel_usuario.getText().toString();
                String prec = precio.getText().toString();
                String QR = QRCODE.getText().toString();
                String nombre = nom.getText().toString();
                if(tel.equals("")||prec.equals("")||QR.equals("")||nombre.equals(""))
                {
                    Mensaje("Revisa que completes todos los datos");
                }
                else
                {
                    enviarMensaje(tel, "Compraste el producto "+nombre+" QR: "+QR+" Precio: "+ prec);
                    Elimiar_producto(QR);
                }


            }
        });

        escanear_pro.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(VerProductoParaComprar.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(VerProductoParaComprar.this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                startQRScanner();
            }
        });

        BottomNavigationView view = findViewById(R.id.nav_productos_usuario);
        view.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.gym_main_page)
                {
                    Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.comprar_producto)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_productos_usuario.class);
                    startActivity(intento);
                } else if(item.getItemId()==R.id.mi_carrito)
                {
                    Intent intento = new Intent(getApplicationContext(), VerCarritoProductos.class);
                    startActivity(intento);
                }

            }
        });


    }


    public void Elimiar_producto(String qr)
    {

        myRef = database.getReference("CarritoUsuario");
        myRef.child(qr).removeValue();

    }

    private void startQRScanner()
    {
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

                verificaExistenciaEnFirebase("Productos",scanContent, new RegistrarProductoAdmin.FirebaseCallback() {
                    @Override
                    public void onCallback(boolean existe) {

                        if (existe) {//si existe
                            LeeObjetoEnFirebase(scanContent);
                        }
                        else {
                            Mensaje("Producto no registrado");
                        }
                    }
                });



            }
        }


    }

    public void LeeObjetoEnFirebase(String nombreobj) {
        // se supone que ya usted creo el objeto carro en su firebase
        myRef = database.getReference("Productos");
        myRef.child(nombreobj).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Entidad_producto producto = dataSnapshot.getValue(Entidad_producto.class);

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
                    cantidad.setText(producto.getCantidad());

                    if (!isDestroyed() && !isFinishing()) {
                        Glide.with(VerProductoParaComprar.this)
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

    private void AgregarProductoCarrito(String qr, String nom, String url_img, String precio, String descripcion, String cantidad) {
        myRef = database.getReference("CarritoUsuario");

        Entidad_productosCarrito carro = new Entidad_productosCarrito();
        carro.setQR_pro(qr);
        carro.setNombre_pro(nom);
        carro.setUrl_img_pro(url_img);
        carro.setPrecio_pro(precio);
        carro.setDescripcion_pro(descripcion);
        carro.setCantidad_pro(cantidad);

        myRef.child(qr).setValue(carro);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Mensaje("Producto Agregado al carrito");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void MensajeOK(String msg){
        View v1 = getWindow().getDecorView().getRootView();
        AlertDialog.Builder builder1 = new AlertDialog.Builder( v1.getContext());
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {} });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        ;};


    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    public interface FirebaseCallback {
        void onCallback(boolean existe);
    }

    public void verificaExistenciaEnFirebase(String data, String nombreobj, final RegistrarProductoAdmin.FirebaseCallback callback) {
        myRef = database.getReference(data);
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


    private void enviarMensaje(String numeroDestino, String mensaje) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numeroDestino, null, mensaje, null, null);
            Toast.makeText(this, "Compra realizada con exito", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al comprar", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Método para manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, puedes enviar mensajes SMS.
            } else {
                // Permiso denegado, muestra un mensaje de error o solicita permiso nuevamente.
                Toast.makeText(this, "Permiso de SMS denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

}