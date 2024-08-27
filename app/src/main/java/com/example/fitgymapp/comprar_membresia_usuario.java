package com.example.fitgymapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fitgymapp.BDusuario.BDusuarios;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitgymapp.BDcompraMembresias.BDcompra_membresia;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;
import com.example.fitgymapp.Entidades.VariablesGlobales;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class comprar_membresia_usuario extends AppCompatActivity {

    TextView txtTipo_compra, txtPrecio_compra, txtAreas_compra, txtDescripcion_compra, verificarsipuedecomprar;
    Button btnGuarda_compra;
    boolean correctoCompra =false;
    BottomNavigationView view;
    Entidad_ListaMembresias membresias_compra;
    int id =0;
    VariablesGlobales vg;
    String clieID ="Ad7pJsB_173mUVqGevpwvRlt-gDwxunCsnJq5LCn90_tD_JJmSFf-cdgZDkntftswcTRLQ0_oQNl-AQx";
    private static final String TAG = "MyTag";
    PaymentButtonContainer paymentButtonContainer;
    BDusuarios bd;
    int PAYPAL_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar_membresia_usuario);
        view = findViewById(R.id.nav_main);

        paymentButtonContainer = findViewById(R.id.payment_button_container);
        bd=new BDusuarios(comprar_membresia_usuario.this);
        btnGuarda_compra=findViewById(R.id.btnComprarMembresia);

        txtTipo_compra = findViewById(R.id.txtTipo_compra);
        txtPrecio_compra = findViewById(R.id.txtPrecio_compra);
        txtAreas_compra = findViewById(R.id.txt_Areas_compra);
        txtDescripcion_compra = findViewById(R.id.txt_Descripcion_compra);
        verificarsipuedecomprar = findViewById(R.id.verificar_compra);
        vg= new VariablesGlobales();

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

        BDcompra_membresia bDcompraMembresia = new BDcompra_membresia(comprar_membresia_usuario.this);
        membresias_compra = bDcompraMembresia.verMembresia_Comprar(id);

        try {

            if(membresias_compra!=null)
            {
                txtTipo_compra.setText(membresias_compra.getTipo());
                txtPrecio_compra.setText(membresias_compra.getPrecio());
                txtAreas_compra.setText(membresias_compra.getAreas());
                txtDescripcion_compra.setText(membresias_compra.getDescripcion());

                txtTipo_compra.setInputType(InputType.TYPE_NULL);
                txtPrecio_compra.setInputType(InputType.TYPE_NULL);
                txtAreas_compra.setInputType(InputType.TYPE_NULL);
                txtDescripcion_compra.setInputType(InputType.TYPE_NULL);

                int permiso_compra = bd.verificarSiPuedeComprarMembresia(Integer.parseInt(vg.getID()));

                boolean verificar = vg.getCompra_membresia_realizada();
                if(verificar==true||permiso_compra<1) {
                    //MensajeOK("Ya tienes una membresia activa");
                    verificarsipuedecomprar.setText("Ya tienes una membresia activa");
                    paymentButtonContainer.setVisibility(View.GONE);
                    btnGuarda_compra.setEnabled(false);
                    MensajeOK("Agrega datos a tu usuario");
                }
                else {

                }

            }
        }
        catch (Exception e)
        {
            Mensaje(e.toString());
        }

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
                                                        .value(txtPrecio_compra.getText().toString())
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

                                String date_vencimiento = fecha_vencimiento();
                                String date_inicio = fecha_actual();

                                Boolean insert_compra_membresia = bDcompraMembresia.ComprarMembresia(Integer.parseInt(vg.getID()), id, date_inicio, date_vencimiento);

                                if (insert_compra_membresia == true) {
                                    MensajeOK("Pago y compra realizada exitosamente");
                                } else {
                                    Mensaje("error");
                                }
                            }
                        });
                    }
                }
        );

        btnGuarda_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int permiso_compra = bd.verificarSiPuedeComprarMembresia(Integer.parseInt(vg.getID()));
                if(permiso_compra<1)
                {
                    MensajeOK("Tienes que agregar datos a tu perfil");
                }
                else
                {

                        if(txtTipo_compra.getText().toString().equals("")||txtAreas_compra.getText().toString().equals("")||txtPrecio_compra.getText().toString().equals("")||txtDescripcion_compra.getText().toString().equals(""))
                        {
                            Mensaje("Llena todos los campos");
                        }
                        else
                        {
                            boolean verificar = vg.getCompra_membresia_realizada();
                            if(verificar==true) {
                                MensajeOK("Ya tienes una membresia activa");
                            }
                            else {

                                try {
                                    String date_vencimiento = fecha_vencimiento();
                                    String date_inicio = fecha_actual();

                                    Boolean insert_compra_membresia = bDcompraMembresia.ComprarMembresia(Integer.parseInt(vg.getID()), id, date_inicio, date_vencimiento);

                                    if (insert_compra_membresia == true) {
                                        Mensaje("Compra successfull");
                                    } else {
                                        Mensaje("error");
                                    }

                                } catch (Exception e) {
                                    Mensaje(e.toString());
                                }

                            }

                    }
                }
            }
        });



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
                }else if(item.getItemId()==R.id.miPerfil)
                {
                    Intent intento = new Intent(getApplicationContext(), Mi_perfil_usuario.class);
                    startActivity(intento);
                }else if(item.getItemId()==R.id.gym_productos)
                {
                    Intent intento = new Intent(getApplicationContext(), ver_productos_usuario.class);
                    startActivity(intento);
                }

            }
        });

    }

    public String fecha_vencimiento()
    {
        String nuevaFechaFormateada="";
        Calendar calendar = Calendar.getInstance();
        Date fecha_vencimiento = calendar.getTime();

        {
            try {
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                fecha_vencimiento = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                nuevaFechaFormateada = dateFormat.format(fecha_vencimiento);
            } catch (Exception e) {
                Mensaje(e.toString());
            }
        }
        return nuevaFechaFormateada;
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



}