package com.example.fitgymapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitgymapp.Entidades.Entidad_usuarios_registrosCadamembresias;
import com.example.fitgymapp.R;
import com.example.fitgymapp.VerDatosCadaUsuario_Admin;

import java.util.ArrayList;

public class adapter_datosCada_membresia extends RecyclerView.Adapter<adapter_datosCada_membresia.MembresiaViewHolder> {

    ArrayList<Entidad_usuarios_registrosCadamembresias> listaMembresias;

    public adapter_datosCada_membresia(ArrayList<Entidad_usuarios_registrosCadamembresias> listaMembresias)
    {
        this.listaMembresias=listaMembresias;
    }

    @NonNull
    @Override
    public adapter_datosCada_membresia.MembresiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_users_registrados_cada_membrisa, null, false);
        return new adapter_datosCada_membresia.MembresiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_datosCada_membresia.MembresiaViewHolder holder, int position) {
        holder.ID.setText(String.valueOf(listaMembresias.get(position).getID_usuario()));
        holder.nombre.setText(listaMembresias.get(position).getNombre());
        holder.correo.setText(listaMembresias.get(position).getCorreo());
        holder.estado.setText(listaMembresias.get(position).getEstado());
    }

    @Override
    public int getItemCount() {
        return listaMembresias.size();
    }

    public class MembresiaViewHolder extends RecyclerView.ViewHolder {
        TextView  ID, nombre, correo, estado;

        public MembresiaViewHolder(@NonNull View itemView) {
            super(itemView);
            try {

                ID = itemView.findViewById(R.id.ID_usuario);
                nombre = itemView.findViewById(R.id.NombreUsu);
                correo = itemView.findViewById(R.id.CorreoUsu);
                estado = itemView.findViewById(R.id.EstadoUsu);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();

                        Intent intent = new Intent(context, VerDatosCadaUsuario_Admin.class);
                        intent.putExtra("ID", listaMembresias.get(getAdapterPosition()).getID_usuario());
                        context.startActivity(intent);

                    }
                });


            } catch (Exception e) {

            }
        }
    }


}
