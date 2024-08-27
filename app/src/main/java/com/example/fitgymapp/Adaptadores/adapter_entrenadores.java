package com.example.fitgymapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;

import com.example.fitgymapp.Eliminar_Editar_entrenador_admin;
import com.example.fitgymapp.Entidades.Entidad_entrenador;
import com.example.fitgymapp.R;

import java.util.ArrayList;

public class adapter_entrenadores extends RecyclerView.Adapter<adapter_entrenadores.EntrenadorViewHolder> {

    ArrayList<Entidad_entrenador> listaEntrenadores;

    public adapter_entrenadores(ArrayList<Entidad_entrenador> listaEntrenadores)
    {
        this.listaEntrenadores=listaEntrenadores;
    }

    @NonNull
    @Override
    public adapter_entrenadores.EntrenadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_ver_entrenadores, null, false);
        return new adapter_entrenadores.EntrenadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_entrenadores.EntrenadorViewHolder holder, int position) {
        holder.nom.setText(listaEntrenadores.get(position).getNombre_entrenador());

         File myPath = new File("/data/user/0/com.example.fitgymapp/app_Images_entrenador/", listaEntrenadores.get(position).getUrlImg_entrenador()+".jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(myPath.getAbsolutePath());
        holder.urlImg.setImageBitmap(bitmap);
        holder.tel.setText(listaEntrenadores.get(position).getTel_entrenador());
        holder.hora_Ini.setText(listaEntrenadores.get(position).getHoraEntrada_entrenador());
        holder.hora_Sa.setText(listaEntrenadores.get(position).getHoraSalida_entrenador());
    }


    @Override
    public int getItemCount() {
        return listaEntrenadores.size();
    }

    public class EntrenadorViewHolder extends RecyclerView.ViewHolder {
        TextView nom, tel, hora_Ini, hora_Sa;
        ImageView urlImg;

        public EntrenadorViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                nom = itemView.findViewById(R.id.NombreEntrenador);
                urlImg = itemView.findViewById(R.id.ImagenEntrenador);
                tel   = itemView.findViewById(R.id.TelEntrenador);
                 hora_Ini=itemView.findViewById(R.id.Hora_InicioEntrenador);
                 hora_Sa=itemView.findViewById(R.id.Hora_SalidaEntrenador);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();

                        Intent intent = new Intent(context, Eliminar_Editar_entrenador_admin.class);
                        int id = Integer.parseInt(listaEntrenadores.get(getAdapterPosition()).getId_entrenador());
                        intent.putExtra("ID_E", id);
                        context.startActivity(intent);

                    }
                });


            } catch (Exception e) {

            }
        }
    }

}
