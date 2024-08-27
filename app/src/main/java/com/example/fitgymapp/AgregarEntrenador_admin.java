package com.example.fitgymapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fitgymapp.BDentranadores.BDentrenador;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AgregarEntrenador_admin extends AppCompatActivity {

    BottomNavigationView view_admin;
    ImageView imagenEntrenador;
    private static final int PICK_IMAGE_REQUEST = 1; // Código de solicitud para seleccionar una imagen
    EditText id, nombre, edad, telefono;
    Button btnInicio, btn_salida, btn_guardar;
    private int horaInicio, HoraSalida, minutos;
    VariablesGlobales vg;
    BDentrenador bd;
    TextView entrada, salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_entrenador_admin);
        nombre = findViewById(R.id.NombreE);
        edad = findViewById(R.id.EdadE);
        telefono = findViewById(R.id.TEL_E);
        salida=findViewById(R.id.HoraSalida);
        entrada=findViewById(R.id.HoraEntrada);
        imagenEntrenador=findViewById(R.id.imageView);
        id=findViewById(R.id.ID_E);
        vg = new VariablesGlobales();
        bd = new BDentrenador(AgregarEntrenador_admin.this);

        view_admin=findViewById(R.id.nav_admin);
        view_admin.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.membresias_admin)
                {
                    Intent intento = new Intent(getApplicationContext(), Agregar_Membresia_Admin.class);
                    startActivity(intento);
                } else if(menuItem.getItemId()==R.id.home)
                {
                    Intent intento = new Intent(getApplicationContext(), AdministradorMain.class);
                    startActivity(intento);
                }
            }
        });


        imagenEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
            }
        });

        OnclickDelButton(R.id.btnHoraInicio);
        OnclickDelButton(R.id.btnHoraSalida);
        OnclickDelButton(R.id.btnAgregarE);


    }

    public void Mensaje(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void OnclickDelButton(int ref) {

        // Ejemplo  OnclickDelButton(R.id.MiButton);
        // 1 Doy referencia al Button
        View view = findViewById(ref);
        Button miButton = (Button) view;
        //  final String msg = miButton.getText().toString();
        // 2.  Programar el evento onclick
        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog;
                final Calendar c;
                switch (v.getId()) {

                    case R.id.btnHoraInicio:
                        c = Calendar.getInstance();
                        horaInicio = c.get(Calendar.HOUR_OF_DAY);
                        minutos = c.get(Calendar.MINUTE);

                        timePickerDialog = new TimePickerDialog(AgregarEntrenador_admin.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {

                                String amPm;
                                if (hourofDay < 12) {
                                    amPm = "AM";
                                } else {
                                    amPm = "PM";
                                    if (hourofDay > 12) {
                                        hourofDay -= 12; // Convierte la hora en formato de 12 horas si es mayor que 12.
                                    }
                                }

                                String selectedTime = hourofDay + ":" + minute + " " + amPm;
                                entrada.setText(selectedTime);
                                vg.setHoraIni(selectedTime);
                            }
                        }, horaInicio, minutos, false);
                        timePickerDialog.show();

                        break;

                    case R.id.btnHoraSalida:

                        c = Calendar.getInstance();
                        horaInicio = c.get(Calendar.HOUR_OF_DAY);
                        minutos = c.get(Calendar.MINUTE);

                        timePickerDialog = new TimePickerDialog(AgregarEntrenador_admin.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {

                                String amPm;
                                if (hourofDay < 12) {
                                    amPm = "AM";
                                } else {
                                    amPm = "PM";
                                    if (hourofDay > 12) {
                                        hourofDay -= 12; // Convierte la hora en formato de 12 horas si es mayor que 12.
                                    }
                                }

                                String selectedTime = hourofDay + ":" + minute + " " + amPm;
                                salida.setText(selectedTime);
                                vg.setHoraSa(selectedTime);
                            }
                        }, horaInicio, minutos, false);
                        timePickerDialog.show();

                        break;

                    case R.id.btnAgregarE:
                        Mensaje("Si agrego");
                        if (id.getText().toString().equals("")|| nombre.getText().toString().equals("") || edad.getText().toString().equals("") || telefono.getText().toString().equals("") || vg.getHoraIni().equals("") || vg.getHoraSa().equals("")||vg.isAgrego_img()==false) {
                            Mensaje("Llenar todos los campos");
                        } else {

                            try
                            {
                                boolean insert = bd.AgregarEntrenador(id.getText().toString(), nombre.getText().toString(), id.getText().toString() , edad.getText().toString(), telefono.getText().toString(), vg.getHoraIni(), vg.getHoraSa());
                                saveImageToInternalStorage(vg.getBitmapImg(), id.getText().toString());
                                if(insert)
                                 Mensaje("Se registro de forma correcta");
                            }
                            catch (Exception e)
                            {
                                Mensaje("Error al registrar"+ e.toString());
                                Log.i("Error", e.toString());
                            }



                        }


                        break;
                    default:
                        break;
                }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                // Convierte la URI de la imagen a un objeto Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                vg.setBitmapImg(bitmap);
                vg.setAgrego_img(true);
                // Guarda la imagen en el almacenamiento interno
                //saveImageToInternalStorage(bitmap, "prueba");
                //Mensaje("Bitmap "+String.valueOf(bitmap));
                // Muestra la imagen en el ImageView
                imagenEntrenador.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void saveImageToInternalStorage(Bitmap bitmapImage, String nombreIMG) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("Images_entrenador", Context.MODE_PRIVATE); // Crea un directorio llamado "images"

        // Genera un nombre de archivo único para la imagen
        String imageName = nombreIMG+".jpg";//le mando como nombre el ID del usuario

        File myPath = new File(directory, imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //Mensaje("Mensaje "+imageName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}