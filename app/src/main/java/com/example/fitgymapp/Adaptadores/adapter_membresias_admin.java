package com.example.fitgymapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitgymapp.EditarMembresiaAdmin;
import com.example.fitgymapp.Entidades.Entidad_ListaMembresias;
import com.example.fitgymapp.R;

import java.util.ArrayList;

public class adapter_membresias_admin extends RecyclerView.Adapter<adapter_membresias_admin.MembresiaViewHolder1>{

    ArrayList<Entidad_ListaMembresias> listaMembresias;

    public adapter_membresias_admin(ArrayList<Entidad_ListaMembresias> listaMembresias)
    {
        this.listaMembresias=listaMembresias;
    }

    @NonNull
    @Override
    public adapter_membresias_admin.MembresiaViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_ver_membresias_admin, null, false);
        return new adapter_membresias_admin.MembresiaViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_membresias_admin.MembresiaViewHolder1 holder, int position) {
        holder.viewTipo.setText(listaMembresias.get(position).getTipo());
        holder.viewAreas.setText(listaMembresias.get(position).getAreas());
        holder.precio_membresia.setText(listaMembresias.get(position).getPrecio());
        // holder.desc1.setText(listaMembresias.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {return listaMembresias.size();}

    public class MembresiaViewHolder1 extends RecyclerView.ViewHolder {
        TextView viewTipo, precio_membresia, viewAreas, desc1;

        public MembresiaViewHolder1(@NonNull View itemView) {
            super(itemView);
            try {
                viewTipo = itemView.findViewById(R.id.tipo);
                viewAreas = itemView.findViewById(R.id.areas1);
                precio_membresia = itemView.findViewById(R.id.precio1);
                // desc1 =itemView.findViewById(R.id.des1);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();

                        Intent intent = new Intent(context, EditarMembresiaAdmin.class);
                        intent.putExtra("ID", listaMembresias.get(getAdapterPosition()).getId());
                        context.startActivity(intent);

                    }
                });


            } catch (Exception e) {

            }
        }
    }

}
