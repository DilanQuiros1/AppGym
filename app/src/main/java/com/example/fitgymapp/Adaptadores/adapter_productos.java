package com.example.fitgymapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitgymapp.Entidades.Entidad_producto;
import com.example.fitgymapp.Entidades.Entidad_usuarios_registrosCadamembresias;
import com.example.fitgymapp.R;
import com.example.fitgymapp.VerDatosCadaUsuario_Admin;
import com.example.fitgymapp.VerProductoParaComprar;

import java.util.ArrayList;

public class adapter_productos extends RecyclerView.Adapter<adapter_productos.ProductoViewHolder>{

    ArrayList<Entidad_producto> listaProductos;

    public adapter_productos(ArrayList<Entidad_producto> listaMembresias)
    {
        this.listaProductos=listaMembresias;
    }

    @NonNull
    @Override
    public adapter_productos.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos, null, false);
        return new adapter_productos.ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_productos.ProductoViewHolder holder, int position) {
        holder.QR.setText(listaProductos.get(position).getID_QRCODE());
        holder.nombre.setText(listaProductos.get(position).getNombre());
        holder.precio.setText(listaProductos.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView QR, nombre, precio;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            try {

                QR = itemView.findViewById(R.id.Qr_pruducto);
                nombre = itemView.findViewById(R.id.Nombre_producto);
                precio = itemView.findViewById(R.id.Precio_producto);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();

                        Intent intent = new Intent(context, VerProductoParaComprar.class);
                        intent.putExtra("QR", listaProductos.get(getAdapterPosition()).getID_QRCODE());
                        context.startActivity(intent);

                    }
                });


            } catch (Exception e) {

            }
        }
    }

}
