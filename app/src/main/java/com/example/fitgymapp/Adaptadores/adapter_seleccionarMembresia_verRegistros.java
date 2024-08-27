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

import com.example.fitgymapp.Entidades.Entidad_datos_membresia;
import com.example.fitgymapp.R;
import com.example.fitgymapp.VerRegistrosCadaMembresia;

import java.util.ArrayList;

public class adapter_seleccionarMembresia_verRegistros extends RecyclerView.Adapter<adapter_seleccionarMembresia_verRegistros.MembresiaViewHolder> {
    ArrayList<Entidad_datos_membresia> listaMembresias;

    public adapter_seleccionarMembresia_verRegistros(ArrayList<Entidad_datos_membresia> listaMembresias)
    {
        this.listaMembresias=listaMembresias;
    }



    @NonNull
    @Override
    public adapter_seleccionarMembresia_verRegistros.MembresiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_nombres_membresia_ver_datos, null, false);
        return new adapter_seleccionarMembresia_verRegistros.MembresiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_seleccionarMembresia_verRegistros.MembresiaViewHolder holder, int position) {
        holder.viewTipo.setText(listaMembresias.get(position).getTipo_membresia());
        holder.viewAreas.setText(listaMembresias.get(position).getAreas_membresias());
    }

    @Override
    public int getItemCount() {
        return listaMembresias.size();
    }


    public class MembresiaViewHolder extends RecyclerView.ViewHolder {
        TextView viewTipo, viewAreas;

        public MembresiaViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                viewTipo = itemView.findViewById(R.id.TipoMembresia);
                viewAreas = itemView.findViewById(R.id.AreaMembresia);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();

                        Intent intent = new Intent(context, VerRegistrosCadaMembresia.class);
                        intent.putExtra("ID", listaMembresias.get(getAdapterPosition()).getID_membresia());
                        intent.putExtra("tipo_membresia", listaMembresias.get(getAdapterPosition()).getTipo_membresia());
                        context.startActivity(intent);

                    }
                });


            } catch (Exception e) {
                Log.i("Error MSJ",e.toString());
            }
        }
    }


}
