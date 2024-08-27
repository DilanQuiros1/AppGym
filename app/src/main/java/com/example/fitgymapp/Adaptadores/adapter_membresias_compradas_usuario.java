package com.example.fitgymapp.Adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitgymapp.Entidades.EntidadMembresiaUsuarioCompradas;
import com.example.fitgymapp.R;

import java.util.ArrayList;

public class adapter_membresias_compradas_usuario extends RecyclerView.Adapter<adapter_membresias_compradas_usuario.MembresiaActualViewHolder_usu> {

    ArrayList<EntidadMembresiaUsuarioCompradas> Membresia_compradas;

    public adapter_membresias_compradas_usuario(ArrayList<EntidadMembresiaUsuarioCompradas> Membresia_compradas)
    {
        this.Membresia_compradas=Membresia_compradas;
    }

    @NonNull
    @Override
    public adapter_membresias_compradas_usuario.MembresiaActualViewHolder_usu onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_membresias_compradas_usuario, null, false);
        return new adapter_membresias_compradas_usuario.MembresiaActualViewHolder_usu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_membresias_compradas_usuario.MembresiaActualViewHolder_usu holder, int position) {
        holder.viewTipo.setText(Membresia_compradas.get(position).getTipo_membresia());
        holder.viewAreas.setText(Membresia_compradas.get(position).getAreas());
        holder.fecha_inicio.setText(Membresia_compradas.get(position).getFecha_inicio());
        holder.estado.setText(Membresia_compradas.get(position).getEstado());
        holder.fecha_vence.setText(Membresia_compradas.get(position).getFecha_vencimiento());
        holder.id_usu.setText(Membresia_compradas.get(position).getID_UsuarioCompra());
    }

    @Override
    public int getItemCount() {
        return Membresia_compradas.size();
    }

    public class MembresiaActualViewHolder_usu extends RecyclerView.ViewHolder {
        TextView viewTipo, viewAreas, estado, fecha_inicio, fecha_vence, id_usu;

        public MembresiaActualViewHolder_usu(@NonNull View itemView) {
            super(itemView);
            try {
                viewTipo = itemView.findViewById(R.id.tipoMembresiaCompra);
                viewAreas = itemView.findViewById(R.id.AreasDisponibles);
                fecha_vence = itemView.findViewById(R.id.FechaVencimiento);
                estado = itemView.findViewById(R.id.EstadoMembresia);
                fecha_inicio = itemView.findViewById(R.id.FechaInicio);
                id_usu = itemView.findViewById(R.id.ID_usuarioCompra);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();

//                        Intent intent = new Intent(context, ComprarMembresia.class);
//                        intent.putExtra("ID", listaMembresias.get(getAdapterPosition()).getId());
//                        context.startActivity(intent);

                    }
                });


            } catch (Exception e) {

            }
        }
    }

}
