package com.example.fitgymapp;

import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BDHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new BDHelper(this);
//        bar_main=findViewById(R.id.nav_main);
//
//        bar_main.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
//                if(menuItem.getItemId()==R.id.membresias_admin)
//                {
//
//                    Intent intento = new Intent(getApplicationContext(), Agregar_Membresia_Admin.class);
//                    startActivity(intento);
//                } else if(menuItem.getItemId()==R.id.entrenadores_admin)
//                {
//                    Mensaje("Entrenadores Admin");
//                }
//            }
//        });


        Button MiButton = (Button) findViewById(R.id.button);


        MiButton.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {

                SQLiteDatabase db = DB.getWritableDatabase();

                if(db!=null)
                    Mensaje("Bade de datos creada");
                else
                    Mensaje("No se creo");


            }

        });

    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();};
}