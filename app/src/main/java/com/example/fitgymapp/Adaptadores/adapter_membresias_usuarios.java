package com.example.fitgymapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;
import com.example.fitgymapp.R;
import com.example.fitgymapp.comprar_membresia_usuario;

import java.util.ArrayList;

public class adapter_membresias_usuarios extends RecyclerView.Adapter<adapter_membresias_usuarios.MembresiaViewHolder_usu> {
   ArrayList<Entidad_ListaMembresias> listaMembresias;

    public adapter_membresias_usuarios(ArrayList<Entidad_ListaMembresias> listaMembresias)
    {
        this.listaMembresias=listaMembresias;
    }

    @NonNull
    @Override
    public adapter_membresias_usuarios.MembresiaViewHolder_usu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_ver_membresias_usuarios, null, false);
        return new adapter_membresias_usuarios.MembresiaViewHolder_usu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_membresias_usuarios.MembresiaViewHolder_usu holder, int position) {
        holder.viewTipo.setText(listaMembresias.get(position).getTipo());
        holder.viewAreas.setText(listaMembresias.get(position).getAreas());
        holder.precio_membresia.setText(listaMembresias.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return listaMembresias.size();
    }

    public class MembresiaViewHolder_usu extends RecyclerView.ViewHolder {
        TextView viewTipo, precio_membresia, viewAreas, desc1;

        public MembresiaViewHolder_usu(@NonNull View itemView) {
            super(itemView);
            try {
                viewTipo = itemView.findViewById(R.id.tipo_membresia_usu1);
                viewAreas = itemView.findViewById(R.id.areas1_para_usu);
                precio_membresia = itemView.findViewById(R.id.precio_usu);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();

                        Intent intent = new Intent(context, comprar_membresia_usuario.class);
                        intent.putExtra("ID", listaMembresias.get(getAdapterPosition()).getId());
                        context.startActivity(intent);

                    }
                });


            } catch (Exception e) {
                Log.i("Error MSJ",e.toString());
            }
        }
    }
}
