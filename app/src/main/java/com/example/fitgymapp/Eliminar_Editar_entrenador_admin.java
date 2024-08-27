package com.example.fitgymapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fitgymapp.BDentranadores.BDentrenador;
import com.example.fitgymapp.Entidades.Entidad_entrenador;
import com.example.fitgymapp.Entidades.VariablesGlobales;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Eliminar_Editar_entrenador_admin extends AppCompatActivity {
    ImageView imagenEntrenador;
    EditText ID,nom, edad, telefono;
    TextView horaEntra, horaSale;
    Button btn_horaEntrada, btnHoraSalida, btn_editar, btn_eliminar;
    VariablesGlobales vg;
    private int horaInicio, HoraSalida, minutos;
    BDentrenador bDentrenador;
    private static final int PICK_IMAGE_REQUEST = 1; // Código de solicitud para seleccionar una imagen
    int id=0;
    Entidad_entrenador entrenador;
    boolean correcto =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_editar_entrenador_admin);
        imagenEntrenador=findViewById(R.id.imageEditarEntrenador);
        ID=findViewById(R.id.ID_E_Opciones);
        nom = findViewById(R.id.NombreE);
        edad=findViewById(R.id.EdadE_Opciones);
        telefono=findViewById(R.id.TEL_E);
        horaEntra=findViewById(R.id.VerHoraEntradaActual);
        horaSale=findViewById(R.id.VerHoraSalidaActual);

        btn_editar=findViewById(R.id.btneditarE);
        btn_eliminar=findViewById(R.id.btneliminarE);

        btnHoraSalida=findViewById(R.id.btnHoraSalidaEditada);
        btn_horaEntrada=findViewById(R.id.btnHoraInicioEditar);

        vg = new VariablesGlobales();


        if(savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                id = Integer.parseInt(null);
            }
            else
            {
                id = extras.getInt("ID_E");
            }
        }
        else
        {
            id = (int) savedInstanceState.getSerializable("ID_E");
        }

         bDentrenador=new BDentrenador(Eliminar_Editar_entrenador_admin.this);
        entrenador = bDentrenador.verEntrandor_editar(id);




        if(entrenador!=null)
        {
            ID.setText(entrenador.getId_entrenador());
            nom.setText(entrenador.getNombre_entrenador());
            File directory = getDir("Images_entrenador", Context.MODE_PRIVATE);
            File myPath = new File(directory, entrenador.getUrlImg_entrenador()+".jpg");
            Bitmap bitmap = BitmapFactory.decodeFile(myPath.getAbsolutePath());
            imagenEntrenador.setImageBitmap(bitmap);

            edad.setText(entrenador.getEdad_entrenador());
            telefono.setText(entrenador.getTel_entrenador());
            horaEntra.setText(entrenador.getHoraEntrada_entrenador());
            horaSale.setText(entrenador.getHoraSalida_entrenador());

        }

        imagenEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
            }
        });


        OnclickDelButton(R.id.btneditarE);
        OnclickDelButton(R.id.btneliminarE);
        OnclickDelButton(R.id.btnHoraSalidaEditada);
        OnclickDelButton(R.id.btnHoraInicioEditar);


    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                vg.setBitmapImg(bitmap);
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

    public void OnclickDelButton(int ref) {

        // Ejemplo  OnclickDelButton(R.id.MiButton);
        // 1 Doy referencia al Button
        View view =findViewById(ref);
        Button miButton = (Button) view;
        //  final String msg = miButton.getText().toString();
        // 2.  Programar el evento onclick
        miButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog;
                final Calendar c;
                switch (v.getId()) {

                    case R.id.btneditarE:
                        String Id = ID.getText().toString();
                        String Nombre = nom.getText().toString();
                        String Edad = edad.getText().toString();
                        String Tel = telefono.getText().toString();
                        String Horaentra = horaEntra.getText().toString();
                        String Horasale = horaSale.getText().toString();

                        if(Id.equals("")||Nombre.equals("")||Edad.equals("")||Tel.equals("")||Horaentra.equals("")|| Horasale.equals(""))
                        {
                            Mensaje("Llena todos los campos");
                        }
                        else {
                            correcto = bDentrenador.editarEntrenador(Integer.parseInt(Id), Nombre, Id, Edad, Tel, Horaentra, Horasale);
                            if (correcto) {
                                saveImageToInternalStorage(vg.getBitmapImg(), Id);
                                Mensaje("Se Edito de forma correcta");
                            } else {

                            }
                        }

                        break;

                    case R.id.btneliminarE:
                         Id = ID.getText().toString();
                        correcto = bDentrenador.EliminarEntrenador(Integer.parseInt(Id));

                        if (correcto)
                            Mensaje("Se elimino de forma correcta");
                        else
                            Mensaje("No se eliminio");

                        break;

                    case R.id.btnHoraSalidaEditada:

                        c = Calendar.getInstance();
                        horaInicio = c.get(Calendar.HOUR_OF_DAY);
                        minutos = c.get(Calendar.MINUTE);

                        timePickerDialog = new TimePickerDialog(Eliminar_Editar_entrenador_admin.this, new TimePickerDialog.OnTimeSetListener() {
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
                                horaSale.setText(selectedTime);
                                vg.setHoraSa(selectedTime);

                            }
                        }, horaInicio, minutos, false);
                        timePickerDialog.show();

                        break;

                    case R.id.btnHoraInicioEditar:

                        c = Calendar.getInstance();
                        horaInicio = c.get(Calendar.HOUR_OF_DAY);
                        minutos = c.get(Calendar.MINUTE);

                        timePickerDialog = new TimePickerDialog(Eliminar_Editar_entrenador_admin.this, new TimePickerDialog.OnTimeSetListener() {
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
                                vg.setHoraIni(selectedTime);
                                horaEntra.setText(selectedTime);

                            }
                        }, horaInicio, minutos, false);
                        timePickerDialog.show();

                        break;
                    default:break; }// fin de casos
            }// fin del onclick
        });
    }// fin de OnclickDelButton

}