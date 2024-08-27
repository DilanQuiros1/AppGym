package com.example.fitgymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAdmin extends AppCompatActivity {

    EditText username, password;
    Button btnlogin, btn_home;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://proyectoprogra-d52fb-default-rtdb.firebaseio.com/");//https://pruebafirebase-bc526-default-rtdb.firebaseio.com/
    String miclave = "Yo digo";
    DatabaseReference myRef = database.getReference(miclave);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        username =(EditText) findViewById(R.id.username_lg);
        password =(EditText) findViewById(R.id.password_lg);
        btnlogin =(Button) findViewById(R.id.btnsignin);
        btn_home =(Button) findViewById(R.id.btn_irhome);



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String usu = username.getText().toString();
               String clave = password.getText().toString();


                   verificarID(usu, clave);

            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                startActivity(intento);
            }
        });
    }


    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};

    void verificarID(String id, String clave){
        myRef = database.getReference("IDAdmin");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                if(value.equals(id))
                {
                    myRef = database.getReference("PasswordAdmin");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = (String) dataSnapshot.getValue();
                            if(value.equals(clave))
                            {
                                Intent intento = new Intent(getApplicationContext(), AdministradorMain.class);
                                startActivity(intento);
                            }
                            else
                            {
                                Mensaje("Crendenciales Incorrectas");
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}

                    });
                }
                else
                {
                    Mensaje("Crendenciales Incorrectas");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

    }

    void verificarPassword(){
        myRef = database.getReference("PasswordAdmin");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                if(value!=null)
                {
                   // verTel.setText(value);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

    }

}