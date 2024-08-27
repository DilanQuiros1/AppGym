package com.example.fitgymapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitgymapp.DBLogin.DBlogin;

public class register_usuario extends AppCompatActivity {
    EditText ID, username, correo, password;
    Button register;
    ImageView log;
    DBlogin DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_usuario);

        ID =(EditText) findViewById(R.id.ID_usuario);
        username =(EditText) findViewById(R.id.username);
        correo =(EditText) findViewById(R.id.correo);
        password =(EditText) findViewById(R.id.password);
        register =(Button) findViewById(R.id.btnRegister);
        log =(ImageView) findViewById(R.id.irLogin);
        //irsingin =(Button) findViewById(R.id.btnIrsignin);
        DB = new DBlogin(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String ID_usuario = ID.getText().toString();
                String nombre = username.getText().toString();
                String correoUsu = correo.getText().toString();
                String pass = password.getText().toString();


                try {

                    if(nombre.equals("")||pass.equals("")||correoUsu.equals("")||ID_usuario.equals(""))
                        Mensaje("Please enter all fields");
                    else
                    {

                            Boolean checkuser = DB.checkusername(nombre);
                            if(checkuser==false)
                            {
                                int id = Integer.parseInt(ID_usuario);
                                Boolean insert =DB.registertUser(id, nombre, correoUsu, pass);
                                if(insert==true)
                                {
                                    Mensaje("Register successfull");
                                    Intent intento = new Intent(getApplicationContext(), Login_Activity.class);
                                    startActivity(intento);
                                }
                                else
                                {
                                    Mensaje("error adding user");
                                }
                            }
                            else
                            {
                                Mensaje("alredy exists");
                            }

                    }

                }
                catch (Exception e)
                {
                    Mensaje(e.toString());
                }

            }
        });


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intento);
            }
        });


    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();}

}