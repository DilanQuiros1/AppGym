package com.example.fitgymapp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitgymapp.EditarEliminar_producto_admin;
import com.example.fitgymapp.Entidades.Entidad_producto;
import com.example.fitgymapp.Entidades.Entidad_productosCarrito;
import com.example.fitgymapp.R;
import com.example.fitgymapp.VerProductoParaComprar;
import com.example.fitgymapp.VerProductosAdmin;

import java.util.List;

public class adapter_ver_productos_admin extends RecyclerView.Adapter<adapter_ver_productos_admin.ViewProducto>{

    List<Entidad_producto> listaProductos;

    public adapter_ver_productos_admin( List<Entidad_producto> listaProductos)
    {

        this.listaProductos=listaProductos;
    }

    @NonNull
    @Override
    public adapter_ver_productos_admin.ViewProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ver_carrito_productos, null, false);
        return new adapter_ver_productos_admin.ViewProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_ver_productos_admin.ViewProducto holder, int position) {
        holder.qr.setText(listaProductos.get(position).getID_QRCODE());
        holder.nombre.setText(listaProductos.get(position).getNombre());
        holder.precio.setText(listaProductos.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewProducto extends RecyclerView.ViewHolder{
        TextView qr, nombre, precio;

        public ViewProducto(@NonNull View itemView) {
            super(itemView);
            qr = itemView.findViewById(R.id.QRPro);
            nombre = itemView.findViewById(R.id.NombrePro);
            precio = itemView.findViewById(R.id.PrecioPro);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();

                    Intent intent = new Intent(context, EditarEliminar_producto_admin.class);
                    intent.putExtra("QR", listaProductos.get(getAdapterPosition()).getID_QRCODE());
                    context.startActivity(intent);

                }
            });

        }
    }

}
