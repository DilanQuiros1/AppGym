package com.example.fitgymapp;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitgymapp.DBLogin.DBlogin;
import com.example.fitgymapp.Entidades.VariablesGlobales;

public class Login_Activity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin, btn_register;
    DBlogin DB;
    VariablesGlobales vg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =(EditText) findViewById(R.id.username_lg);
        password =(EditText) findViewById(R.id.password_lg);
        btnlogin =(Button) findViewById(R.id.btnsignin);
        btn_register =(Button) findViewById(R.id.btnRegister);
        DB = new DBlogin(this);
        vg = new VariablesGlobales();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Mensaje("Please enter all fields");
                else
                {
                    Boolean checkuserpass=DB.checkusernamepassword(user,pass);
                    if(checkuserpass==true)
                    {

                        Intent intento = new Intent(getApplicationContext(), HomeMain.class);
                        startActivity(intento);
                        vg.setID(user);
                    }
                    else
                    {
                        Mensaje("Invalid credentials");
                    }
                }

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intento = new Intent(getApplicationContext(), register_usuario.class);
                startActivity(intento);
            }
        });

        CheckBox showPasswordCheckbox = findViewById(R.id.verpassword);
        final EditText editTextPassword = findViewById(R.id.password_lg);

        showPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Mostrar la contraseña
                    Mensaje("Mostrar");
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    // Ocultar la contraseña
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


    }
    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};
}